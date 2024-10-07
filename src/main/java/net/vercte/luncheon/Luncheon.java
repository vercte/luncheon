package net.vercte.luncheon;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vercte.luncheon.content.registry.LuncheonItems;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Luncheon.MODID)
public class Luncheon {
    public static final String MODID = "luncheon";
    public static final NonNullSupplier<CreateRegistrate> REGISTRATE = NonNullSupplier.lazy(() -> CreateRegistrate.create(MODID));
    public static final Logger LOGGER = LogUtils.getLogger();

    public Luncheon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        LuncheonItems.register();
        REGISTRATE.get().registerEventListeners(modEventBus);
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE.get();
    }
}
