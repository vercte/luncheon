package net.vercte.luncheon.content.processing.cooler;

import com.simibubi.create.api.recipe.HeatCondition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.vercte.luncheon.registry.LuncheonBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CooledCondition implements HeatCondition {
    public int getColor() { return 0x4455b8; }

    @Override
    public boolean test(Level level, BlockPos testPos) {
        BlockState cooler = level.getBlockState(testPos.below());
        if(!cooler.is(LuncheonBlocks.MECHANICAL_COOLER.get())) return false;
        return cooler.getValue(MechanicalCoolerBlock.ACTIVE);
    }


    @NotNull
    public List<ItemStack> getItemHints() {
        return List.of(LuncheonBlocks.MECHANICAL_COOLER.asStack());
    }

    public String getTranslationKey() {
        return "luncheon.recipe.heat_requirement.cooled";
    }
}
