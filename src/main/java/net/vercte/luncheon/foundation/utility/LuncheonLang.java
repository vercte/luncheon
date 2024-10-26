package net.vercte.luncheon.foundation.utility;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;
import com.simibubi.create.foundation.utility.LangNumberFormat;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.vercte.luncheon.Luncheon;

public class LuncheonLang extends Lang {
    public static LangBuilder builder() {
        return new LangBuilder(Luncheon.MODID);
    }

    public static MutableComponent translateDirect(String key, Object... args) {
        return Components.translatable(Luncheon.MODID + "." + key, resolveBuilders(args));
    }

    public static LangBuilder blockName(BlockState state) {
        return builder().add(state.getBlock()
                .getName());
    }

    public static LangBuilder itemName(ItemStack stack) {
        return builder().add(stack.getHoverName()
                .copy());
    }

    public static LangBuilder fluidName(FluidStack stack) {
        return builder().add(stack.getDisplayName()
                .copy());
    }

    public static LangBuilder number(double d) {
        return builder().text(LangNumberFormat.format(d));
    }

    public static LangBuilder translate(String langKey, Object... args) {
        return builder().translate(langKey, args);
    }
}
