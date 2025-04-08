package net.vercte.luncheon.forge.content.regisitry;

import com.simibubi.create.Create;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.registry.LuncheonCreativeTabs;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LuncheonCreativeTabsImpl {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Luncheon.ID);
    private static final Map<ResourceKey<CreativeModeTab>, RegistryObject<CreativeModeTab>> TABS = new HashMap<>();

    public static Supplier<CreativeModeTab> makeTab(String id, Supplier<CreativeModeTab> sup) {
        RegistryObject<CreativeModeTab> tab = CREATIVE_TABS.register(id, sup);
        TABS.put(LuncheonCreativeTabs.makeKey(id), tab);
        return tab;
    }

    public static CreativeModeTab.Builder createBuilder() {
        return CreativeModeTab.builder().withTabsBefore(Create.asResource("palettes"));
    }

    public static void registerForge(IEventBus modBus) {
        CREATIVE_TABS.register(modBus);
    }

    public static void useModTab(ResourceKey<CreativeModeTab> key) {
        Luncheon.REGISTRATE.get().setCreativeTab(TABS.get(key));
    }
}
