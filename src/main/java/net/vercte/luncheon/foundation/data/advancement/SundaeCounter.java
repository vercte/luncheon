package net.vercte.luncheon.foundation.data.advancement;

import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import net.minecraft.world.item.ItemStack;

public interface SundaeCounter {
    void luncheon$onItemProcessed(DeployerBlockEntity entity, ItemStack stack);
}
