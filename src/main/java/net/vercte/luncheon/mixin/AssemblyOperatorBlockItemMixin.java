package net.vercte.luncheon.mixin;

import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.vercte.luncheon.content.kinetics.stirrer.MechanicalStirrerBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AssemblyOperatorBlockItem.class)
public class AssemblyOperatorBlockItemMixin {
    @Inject(method = "adjustContext", at = @At("HEAD"), cancellable = true, remap = false)
    public void doNotAdjustIfStirrer(BlockPlaceContext context, BlockPos placedOnPos, CallbackInfoReturnable<BlockPlaceContext> cir) {
        AssemblyOperatorBlockItem blockItem = (AssemblyOperatorBlockItem)(Object)this;
        if(blockItem.getBlock() instanceof MechanicalStirrerBlock) {
            cir.setReturnValue(context);
        }
    }
}
