package net.vercte.luncheon.mixin;

import com.simibubi.create.content.processing.basin.BasinBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.vercte.luncheon.content.kinetics.stirrer.MechanicalStirrerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BasinBlock.class)
public class BasinBlockMixin {
    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    public void survivesUnderStirrer(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockEntity blockEntity = world.getBlockEntity(pos.above());
        if (blockEntity instanceof MechanicalStirrerBlockEntity)
            cir.setReturnValue(true);
    }
}
