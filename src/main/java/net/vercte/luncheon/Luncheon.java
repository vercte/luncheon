package net.vercte.luncheon;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vercte.luncheon.content.registry.*;
import net.vercte.luncheon.content.registry.custom.LuncheonRegistrate;
import net.vercte.luncheon.foundation.utility.data.LuncheonDatagen;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Luncheon.MODID)
public class Luncheon {
    public static final String MODID = "luncheon";
    public static final NonNullSupplier<LuncheonRegistrate> REGISTRATE = NonNullSupplier.lazy(() -> LuncheonRegistrate.create(MODID));
    public static final Logger LOGGER = LogUtils.getLogger();

    public Luncheon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        LuncheonItems.register();
        LuncheonBlocks.register();
        LuncheonBlockEntityTypes.register();
        LuncheonMobEffects.register();

        modEventBus.addListener(LuncheonClient::clientInit);
        modEventBus.addListener(EventPriority.LOWEST, LuncheonDatagen::gatherData);

        REGISTRATE.get().registerEventListeners(modEventBus);
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }

    public static LuncheonRegistrate registrate() {
        return REGISTRATE.get();
    }

}
