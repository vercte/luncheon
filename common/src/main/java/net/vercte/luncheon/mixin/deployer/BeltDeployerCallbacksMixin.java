package net.vercte.luncheon.mixin.deployer;

import com.simibubi.create.content.kinetics.deployer.BeltDeployerCallbacks;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.vercte.luncheon.content.processing.duck.SundaeCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BeltDeployerCallbacks.class)
public abstract class BeltDeployerCallbacksMixin {
    @Shadow
    private static void awardAdvancements(DeployerBlockEntity blockEntity, ItemStack created) {}

    @Redirect(method = "activate", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/deployer/BeltDeployerCallbacks;awardAdvancements(Lcom/simibubi/create/content/kinetics/deployer/DeployerBlockEntity;Lnet/minecraft/world/item/ItemStack;)V"), remap = false)
    private static void award(DeployerBlockEntity blockEntity, ItemStack created) {
        SundaeCounter sundaeCounter = (SundaeCounter)blockEntity;
        sundaeCounter.luncheon$onItemProcessed(blockEntity, created);
        awardAdvancements(blockEntity, created);
    }
}
