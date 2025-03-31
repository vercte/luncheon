package net.vercte.luncheon.foundation.utility;

import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.lang.LangBuilder;
import net.createmod.catnip.lang.LangNumberFormat;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.vercte.luncheon.Luncheon;

import static net.createmod.catnip.lang.LangBuilder.resolveBuilders;

public class LuncheonLang extends Lang {
    public static LangBuilder builder() {
        return new LangBuilder(Luncheon.ID);
    }

    public static MutableComponent translateDirect(String key, Object... args) {
        return Component.translatable(Luncheon.ID + "." + key, resolveBuilders(args));
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
