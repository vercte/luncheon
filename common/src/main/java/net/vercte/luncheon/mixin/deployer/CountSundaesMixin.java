package net.vercte.luncheon.mixin.deployer;

import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.misc.LuncheonCriteriaTriggers;
import net.vercte.luncheon.content.processing.duck.SundaeCounter;
import net.vercte.luncheon.content.registry.LuncheonItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(DeployerBlockEntity.class)
public abstract class CountSundaesMixin implements SundaeCounter {
    @Shadow public BeltProcessingBehaviour processingBehaviour;
    @Unique private int luncheon$sundaesMade = 0;

    @Unique
    public void luncheon$onItemProcessed(DeployerBlockEntity entity, ItemStack stack) {
        if(stack.is(LuncheonItems.NEAPOLITAN_SUNDAE.asItem())) luncheon$sundaesMade++;
        if(luncheon$sundaesMade > 31) {
            AdvancementBehaviourAccessor adv = (AdvancementBehaviourAccessor)BlockEntityBehaviour.get(entity, AdvancementBehaviour.TYPE);
            UUID id = adv.getPlayerId();
            if(id != null) {
                Player player = entity.getLevel().getPlayerByUUID(id);
                if(player instanceof ServerPlayer sp) LuncheonCriteriaTriggers.AUTOMATION.trigger(sp, stack);
            }
            luncheon$sundaesMade = 0;
        }
    }

    @Inject(method = "write", at = @At("HEAD"), remap = false)
    public void write(CompoundTag tag, boolean clientPacket, CallbackInfo ci) {
        tag.putInt("luncheon$sundaesMade", luncheon$sundaesMade);
    }

    @Inject(method = "read", at = @At("HEAD"), remap = false)
    public void read(CompoundTag tag, boolean clientPacket, CallbackInfo ci) {
        luncheon$sundaesMade = tag.getInt("luncheon$sundaesMade");
    }
}
