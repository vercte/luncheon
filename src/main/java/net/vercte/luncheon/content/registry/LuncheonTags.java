package net.vercte.luncheon.content.registry;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.vercte.luncheon.Luncheon;

import java.util.Collections;

import static net.vercte.luncheon.content.registry.LuncheonTags.Namespace.MOD;

public class LuncheonTags {
    public static <T> TagKey<T> optionalTag(IForgeRegistry<T> registry,
                                            ResourceLocation id) {
        return registry.tags()
                .createOptionalTagKey(id, Collections.emptySet());
    }


    public enum Namespace {

        MOD(Luncheon.MODID, false, true),
        CREATE(Create.ID, false, false),
        FORGE("forge"),
        ;

        public final String id;
        public final boolean optionalDefault;
        public final boolean alwaysDatagenDefault;

        Namespace(String id) {
            this(id, true, false);
        }

        Namespace(String id, boolean optionalDefault, boolean alwaysDatagenDefault) {
            this.id = id;
            this.optionalDefault = optionalDefault;
            this.alwaysDatagenDefault = alwaysDatagenDefault;
        }
    }

    public enum ItemTags {
        ICE_CREAM_BLOCKS_PLAIN(MOD, "ice_cream_blocks/plain"),
        ICE_CREAM_BLOCKS_CHOCOLATE(MOD, "ice_cream_blocks/chocolate");

        public final TagKey<Item> tag;
        public final boolean alwaysDatagen;

        ItemTags() {
            this(MOD);
        }

        ItemTags(Namespace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        ItemTags(Namespace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        ItemTags(Namespace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        ItemTags(Namespace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
            if (optional) {
                tag = optionalTag(ForgeRegistries.ITEMS, id);
            } else {
                tag = net.minecraft.tags.ItemTags.create(id);
            }
            this.alwaysDatagen = alwaysDatagen;
        }

        public static void init() {}
    }

    public static void init() {
        ItemTags.init();
    }
}
