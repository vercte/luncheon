package net.vercte.luncheon;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

public class LuncheonItems {
    private static final CreateRegistrate REGISTRATE = Luncheon.registrate();

    public static final ItemEntry<Item> ICE_CUBE = ingredient("ice_cube", "Ice Cube");

    private static ItemEntry<Item> ingredient(String id, String lang) {
        return REGISTRATE.item(id, Item::new).lang(lang).register();
    }

    public static void register() {}
}
