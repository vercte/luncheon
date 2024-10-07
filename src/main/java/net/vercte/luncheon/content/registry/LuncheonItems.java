package net.vercte.luncheon.content.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.item.BaguetteItem;
import net.vercte.luncheon.content.item.LuncheonFoodProperties;

public class LuncheonItems {
    private static final CreateRegistrate REGISTRATE = Luncheon.registrate();

    public static final ItemEntry<Item> GLASS_JAR = REGISTRATE.item("glass_jar", Item::new).register();

    // region Foods
    public static final ItemEntry<BaguetteItem> BAGUETTE = REGISTRATE.item("baguette", BaguetteItem::new)
            .properties(p -> p.food(LuncheonFoodProperties.BAGUETTE))
            .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "item.luncheon.baguette"))
            .register();

    public static final ItemEntry<Item> ICE_CUBE = REGISTRATE.item("ice_cube", Item::new).register();
    // endregion

    public static void register() {}
}
