package net.vercte.luncheon;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.vercte.luncheon.content.block.ice_cream.IceCreamTypes;
import net.vercte.luncheon.content.registry.*;
import net.vercte.luncheon.content.registry.custom.LuncheonDisplayItemsGenerator;
import net.vercte.luncheon.content.registry.custom.LuncheonRegistrate;
import org.slf4j.Logger;

public class Luncheon {
    public static final String ID = "luncheon";
    public static final NonNullSupplier<LuncheonRegistrate> REGISTRATE = NonNullSupplier.lazy(() -> LuncheonRegistrate.create(ID));
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void initialize() {
        LuncheonItems.register();
        LuncheonBlocks.register();
        LuncheonFluids.register();
        LuncheonBlockEntityTypes.register();
        LuncheonTags.init();
        IceCreamTypes.register(REGISTRATE.get());
        CREATIVE_TABS.register(modEventBus);
    }

    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ID);

    public static final RegistryObject<CreativeModeTab> BASE_CREATIVE_TAB = CREATIVE_TABS.register("base",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.luncheon.base"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(LuncheonItems.ICE_CUBE::asStack)
                    .displayItems(new LuncheonDisplayItemsGenerator(Luncheon.BASE_CREATIVE_TAB))
                    .build());

    public static ResourceLocation at(String namespace, String path) { return new ResourceLocation(namespace, path); }
    public static ResourceLocation asVanillaResource(String path) {
        return new ResourceLocation("minecraft", path);
    }
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(ID, path);
    }
    public static LuncheonRegistrate registrate() {
        return REGISTRATE.get();
    }
}
