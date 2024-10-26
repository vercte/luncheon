package net.vercte.luncheon.content.kinetics.stirrer;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.infrastructure.config.AllConfigs;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.vercte.luncheon.content.registry.LuncheonRecipeTypes;
import net.vercte.luncheon.foundation.utility.LuncheonLang;

import java.util.List;
import java.util.Optional;

import static net.minecraft.ChatFormatting.GOLD;

public class MechanicalStirrerBlockEntity extends BasinOperatingBlockEntity {
    private static final Object StirringRecipesKey = new Object();

    public boolean running;

    public MechanicalStirrerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return new AABB(worldPosition).expandTowards(0,-1,0);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        running = compound.getBoolean("Running");
        super.read(compound, clientPacket);
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        compound.putBoolean("Running", running);
        super.write(compound, clientPacket);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void onBasinRemoved() {
        if (!running)
            return;
        running = false;
    }

    @Override
    protected <C extends Container> boolean matchStaticFilters(Recipe<C> r) {
        return r.getType() == LuncheonRecipeTypes.STIRRING.getType();
    }

    @Override
    public boolean isSpeedRequirementFulfilled() {
        BlockState state = getBlockState();
        if (!(getBlockState().getBlock() instanceof IRotate))
            return true;
        MechanicalStirrerBlock def = (MechanicalStirrerBlock) state.getBlock();
        IRotate.SpeedLevel minimumRequiredSpeedLevel = def.getMinimumRequiredSpeedLevel();
        IRotate.SpeedLevel maximumRequiredSpeedLevel = def.getMaximumRequiredSpeedLevel();
        return (Math.abs(getSpeed()) >= minimumRequiredSpeedLevel.getSpeedValue())
            && (Math.abs(getSpeed()) <= maximumRequiredSpeedLevel.getSpeedValue());
    }

    private boolean isTooFast() {
        BlockState state = getBlockState();
        if (!(getBlockState().getBlock() instanceof IRotate))
            return true;
        MechanicalStirrerBlock def = (MechanicalStirrerBlock) state.getBlock();
        IRotate.SpeedLevel maximumRequiredSpeedLevel = def.getMaximumRequiredSpeedLevel();
        return (Math.abs(getSpeed()) > maximumRequiredSpeedLevel.getSpeedValue());
    }

    @Override
    public boolean addToTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        boolean wrongSpeed = !isSpeedRequirementFulfilled() && getSpeed() != 0;

        if (overStressed && AllConfigs.client().enableOverstressedTooltip.get()) {
            Lang.translate("gui.stressometer.overstressed")
                    .style(GOLD)
                    .forGoggles(tooltip);
            Component hint = Lang.translateDirect("gui.contraptions.network_overstressed");
            List<Component> cutString = TooltipHelper.cutTextComponent(hint, TooltipHelper.Palette.GRAY_AND_WHITE);
            for (Component component : cutString)
                Lang.builder()
                        .add(component
                                .copy())
                        .forGoggles(tooltip);
            return true;
        }

        if (wrongSpeed) {
            Lang.translate("tooltip.speedRequirement")
                    .style(GOLD)
                    .forGoggles(tooltip);

            MutableComponent hint;
            if(isTooFast()) {
                hint = LuncheonLang.translateDirect("gui.contraptions.too_fast", I18n.get(getBlockState().getBlock()
                        .getDescriptionId()));
            } else {
                hint = Lang.translateDirect("gui.contraptions.not_fast_enough", I18n.get(getBlockState().getBlock()
                        .getDescriptionId()));
            }
            List<Component> cutString = TooltipHelper.cutTextComponent(hint, TooltipHelper.Palette.GRAY_AND_WHITE);
            for (Component component : cutString)
                Lang.builder()
                        .add(component
                                .copy())
                        .forGoggles(tooltip);
            return true;
        }

        return false;
    }

    @Override
    protected Object getRecipeCacheKey() {
        return StirringRecipesKey;
    }

    @Override
    protected Optional<BasinBlockEntity> getBasin() {
        if (level == null)
            return Optional.empty();
        BlockEntity basinBE = level.getBlockEntity(worldPosition.below());
        if (!(basinBE instanceof BasinBlockEntity))
            return Optional.empty();
        return Optional.of((BasinBlockEntity) basinBE);
    }
}
