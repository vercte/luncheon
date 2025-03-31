package net.vercte.luncheon.content.processing.duck;

import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import net.minecraft.world.item.ItemStack;

public interface SundaeCounter {
    void luncheon$onItemProcessed(DeployerBlockEntity entity, ItemStack stack);
}
