package net.vercte.luncheon.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.ParametersAreNonnullByDefault;

@Mixin(Cow.class)
@ParametersAreNonnullByDefault
public abstract class CowMixin extends Animal {
    @Unique
    private int luncheon$milkTime = 0;

    protected CowMixin(EntityType<? extends Animal> animal, Level level) {
        super(animal, level);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if(!(this.level().isClientSide()) && !this.isBaby() && this.isAlive() && this.luncheon$milkTime > 0) {
            this.luncheon$milkTime -= 1;
        }
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(Items.BUCKET) && !this.isBaby() && !this.level().isClientSide() && luncheon$milkTime <= 0) {
            luncheon$milkTime = 1200;
        } else {
            cir.setReturnValue(super.mobInteract(player, hand));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("MilkTime", this.luncheon$milkTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("MilkTime")) {
            this.luncheon$milkTime = nbt.getInt("MilkTime");
        }
    }
}
