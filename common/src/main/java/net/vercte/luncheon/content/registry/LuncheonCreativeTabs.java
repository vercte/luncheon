package net.vercte.luncheon.content.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.registry.custom.LuncheonDisplayItemsGenerator;

import java.util.function.Supplier;

public class LuncheonCreativeTabs {
    public static final Supplier<CreativeModeTab> BASE = makeTab("base", () -> createBuilder()
            .title(Component.translatable("itemGroup.luncheon.base"))
            .icon(LuncheonItems.ICE_CUBE::asStack)
            .displayItems(new LuncheonDisplayItemsGenerator())
            .build());

    @ExpectPlatform
    public static Supplier<CreativeModeTab> makeTab(String id, Supplier<CreativeModeTab> sup) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static CreativeModeTab.Builder createBuilder() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void useModTab(ResourceKey<CreativeModeTab> key) {
        throw new AssertionError();
    }

    public static ResourceKey<CreativeModeTab> makeKey(String id) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, Luncheon.asResource(id));
    }
}
