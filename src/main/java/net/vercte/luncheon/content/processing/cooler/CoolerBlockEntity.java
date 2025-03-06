package net.vercte.luncheon.content.processing.cooler;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.simibubi.create.foundation.item.TooltipHelper;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.lang.FontHelper;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.foundation.utility.LuncheonLang;

import java.util.List;

import static net.minecraft.ChatFormatting.GOLD;

public class CoolerBlockEntity extends KineticBlockEntity {
    private int drainSpeed = 40;
    private int satisfiedTicks;

    private static final int SYNC_RATE = 8;
    private int syncCooldown;
    private boolean queuedSync;

    private boolean active = false;
    public SmartFluidTank tankInventory;
    protected LazyOptional<IFluidHandler> fluidCapability;

    protected LerpedFloat fluidLevel;

    public CoolerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);

        satisfiedTicks = 0;

        tankInventory = new CoolerFluidTank(this::onFluidStackChanged);
        fluidCapability = LazyOptional.of(() -> tankInventory);
    }

    protected void onFluidStackChanged(FluidStack stack) {
        if (!hasLevel())
            return;

        if(!level.isClientSide) {
            setChanged();
            sendData();
        }

        if (isVirtual()) {
            if (fluidLevel == null) fluidLevel = LerpedFloat.linear().startWithValue(getFillState());
            fluidLevel.chase(getFillState(), 0.5f, LerpedFloat.Chaser.EXP);
        }
    }

    @Override
    public void sendData() {
        if (syncCooldown > 0) {
            queuedSync = true;
            return;
        }
        super.sendData();
        queuedSync = false;
        syncCooldown = SYNC_RATE;
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        satisfiedTicks = compound.getInt("satisfiedTicks");
        active = compound.getBoolean("active");
        tankInventory.readFromNBT(compound);

        float fillState = getFillState();
        if(fluidLevel == null) fluidLevel = LerpedFloat.linear().startWithValue(fillState);
        fluidLevel.chase(fillState, 0.5f, LerpedFloat.Chaser.EXP);

        super.read(compound, clientPacket);
    }

    @Override
    public boolean addToTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        if(super.addToTooltip(tooltip, isPlayerSneaking)) return true;

        if(isSpeedRequirementFulfilled() && getSpeed() != 0 && !active) {
            LuncheonLang.translate("tooltip.waterRequirement")
                    .style(GOLD)
                    .forGoggles(tooltip);
            MutableComponent hint =
                    LuncheonLang.translateDirect("gui.contraptions.no_water", I18n.get(getBlockState().getBlock()
                            .getDescriptionId()));
            List<Component> cutString = TooltipHelper.cutTextComponent(hint, FontHelper.Palette.GRAY_AND_WHITE);
            for (Component component : cutString)
                Lang.builder(Luncheon.ID)
                        .add(component
                                .copy())
                        .forGoggles(tooltip);
            return true;
        }
        return false;
    }

    @Override
    protected void write(CompoundTag compoundTag, boolean clientPacket) {
        compoundTag.putInt("satisfiedTicks", satisfiedTicks);
        compoundTag.putBoolean("active", active);
        tankInventory.writeToNBT(compoundTag);

        super.write(compoundTag, clientPacket);
    }

    @Override
    public void tick() {
        super.tick();

        if (fluidLevel != null) fluidLevel.tickChaser();

        if (syncCooldown > 0) {
            syncCooldown--;
            if (syncCooldown == 0 && queuedSync)
                sendData();
        }

        if(level.isClientSide && !isVirtual()) {
            spawnParticles();
            return;
        }

        active = false;
        if(isSpeedRequirementFulfilled()) {
            if(satisfiedTicks > 0) satisfiedTicks--;
            if(satisfiedTicks == 0) {
                FluidStack drained = tankInventory.drain(drainSpeed, IFluidHandler.FluidAction.SIMULATE);
                if(drained.getAmount() == drainSpeed) {
                    tankInventory.drain(drainSpeed, IFluidHandler.FluidAction.EXECUTE);
                    satisfiedTicks = 5;
                }
            }

            active = satisfiedTicks > 0;
        }

        setBlockState();
    }

    protected void setBlockState() {
        setBlockCooling(getCoolingLevel());
    }

    protected void setBlockCooling(CoolerBlock.CoolingLevel coolingLevel) {
        CoolerBlock.CoolingLevel inBlockState = getCoolingLevelFromBlock();
        if (inBlockState == coolingLevel)
            return;
        level.setBlockAndUpdate(worldPosition, getBlockState().setValue(CoolerBlock.COOL_LEVEL, coolingLevel));
        notifyUpdate();
    }

    protected CoolerBlock.CoolingLevel getCoolingLevel() {
        if(active) return CoolerBlock.CoolingLevel.COOLED;
        return CoolerBlock.CoolingLevel.NONE;
    }

    public CoolerBlock.CoolingLevel getCoolingLevelFromBlock() {
        return CoolerBlock.getCoolingLevelOf(getBlockState());
    }

    public float getBladeRotationSpeed() {
        if(!active) return Mth.clamp(speed / 2, -8, 8);
        return speed;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (isFluidHandlerCap(cap)
                && (side == null || CoolerBlock.hasPipeTowards(level, worldPosition, getBlockState(), side)))
            return this.fluidCapability.cast();
        return super.getCapability(cap, side);
    }

    public float getFillState() {
        return (float) tankInventory.getFluidAmount() / tankInventory.getCapacity();
    }

    public LerpedFloat getFluidLevel() {
        return fluidLevel;
    }

    public void spawnParticles() {
        if(level == null) return;
        if(!isSpeedRequirementFulfilled()) return;

        RandomSource r = level.getRandom();

        Vec3 c = VecHelper.getCenterOf(worldPosition).add(0, -2 / 16f, 0);
        Vec3 v = c.add(VecHelper.offsetRandomly(Vec3.ZERO, r, .25f)
                .multiply(1, 0, 1));

        if(r.nextInt(6) != 0) return;

        if(getCoolingLevel() == CoolerBlock.CoolingLevel.NONE) level.addParticle(ParticleTypes.SMOKE, v.x, v.y, v.z, 0, 0, 0);
        if(getCoolingLevel() == CoolerBlock.CoolingLevel.COOLED) level.addParticle(ParticleTypes.SNOWFLAKE, v.x, v.y + 4/16f, v.z, 0, 0, 0);
    }
}
