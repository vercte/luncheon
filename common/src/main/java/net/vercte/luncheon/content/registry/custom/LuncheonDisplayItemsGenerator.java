package net.vercte.luncheon.content.registry.custom;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.vercte.luncheon.content.registry.LuncheonBlocks;
import net.vercte.luncheon.content.registry.LuncheonItems;
import org.lwjgl.system.NonnullDefault;

import java.util.List;

@NonnullDefault
public class LuncheonDisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {
    private static final List<ItemProviderEntry<?>> tabItems = List.of(
            LuncheonBlocks.MECHANICAL_COOLER, LuncheonItems.ICE_CUBE
    );

    @Override
    public void accept(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        outputAll(output);
    }

    private void outputAll(CreativeModeTab.Output output) {
        List<ItemStack> items = tabItems.stream().map(ItemProviderEntry::asStack).toList();
        output.acceptAll(items);
    }
}
