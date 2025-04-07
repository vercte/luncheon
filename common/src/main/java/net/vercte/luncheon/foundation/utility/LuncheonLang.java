package net.vercte.luncheon.foundation.utility;

import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.vercte.luncheon.Luncheon;

import static net.createmod.catnip.lang.LangBuilder.resolveBuilders;

public class LuncheonLang extends Lang {
    public static LangBuilder builder() {
        return new LangBuilder(Luncheon.ID);
    }

    public static MutableComponent translateDirect(String key, Object... args) {
        return Component.translatable(Luncheon.ID + "." + key, resolveBuilders(args));
    }

    public static LangBuilder translate(String langKey, Object... args) {
        return builder().translate(langKey, args);
    }
}
