package net.vercte.luncheon;

import com.mojang.logging.LogUtils;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import net.vercte.luncheon.content.mechanical_cooler.CooledCondition;
import org.slf4j.Logger;

@Mod(Luncheon.ID)
public class Luncheon {
    public static final String ID = "luncheon";
    public static final Logger LOGGER = LogUtils.getLogger();

    private static final NonNullSupplier<CreateRegistrate> REGISTRATE = NonNullSupplier.lazy(() -> CreateRegistrate.create(ID));

    public Luncheon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get()
                .getModEventBus();

        LuncheonBlocks.initalize();
        LuncheonBlockEntities.initalize();
        LuncheonItems.register();

        modEventBus.addListener(Luncheon::onRegister);

        REGISTRATE.get().registerEventListeners(modEventBus);
    }

    private static void onRegister(final RegisterEvent event) {
        Registry.register(CreateBuiltInRegistries.HEAT_CONDITION, Luncheon.asResource("cooled"), new CooledCondition());
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(ID, path);
    }

    public static CreateRegistrate registrate() { return REGISTRATE.get(); }
}
