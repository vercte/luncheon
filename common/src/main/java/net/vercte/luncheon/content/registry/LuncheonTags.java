package net.vercte.luncheon.content.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.platform.Services;

public class LuncheonTags {
    public enum ItemTags {
        ICE_CREAM_CONES("ice_cream/cones"),
        ICE_CREAM_BLOCKS_PLAIN("ice_cream/plain"),
        ICE_CREAM_BLOCKS_CHOCOLATE("ice_cream/chocolate"),
        ICE_CREAM_BLOCKS_BERRY("ice_cream/berry"),
        BUILDING_BLOCKS("building"),
        WAFER_BLOCKS("wafer_blocks"),
        GLASS_SHARD_INCOMPATIBLE("glass_shard_incompatible"),

        // COMMON TAGS
        GLASS("glass_blocks", "glass"),
        GLASS_COLORLESS("colorless_glass", "glass/colorless");

        public final TagKey<Item> tag;

        // luncheon tag
        ItemTags(String luncheonID) {
            tag = TagKey.create(BuiltInRegistries.ITEM.key(), Luncheon.asResource(luncheonID));
        }

        // common tag (same path)
        ItemTags(String commonID, boolean common) {
            if(common) {
                tag = TagKey.create(BuiltInRegistries.ITEM.key(), this.makeCommonTag(commonID, commonID));
                return;
            }
            tag = TagKey.create(BuiltInRegistries.ITEM.key(), Luncheon.asResource(commonID));
        }

        // common tag (different paths)
        ItemTags(String fabricID, String forgeID) {
            tag = TagKey.create(BuiltInRegistries.ITEM.key(), this.makeCommonTag(fabricID, forgeID));
        }

        private ResourceLocation makeCommonTag(String fabricID, String forgeID) {
            String platform = Services.PLATFORM.getPlatformName();
            if(platform.equals("Forge")) {
                return Luncheon.at("forge", forgeID);
            } else {
                return Luncheon.at("c", fabricID);
            }
        }

        public static void init() {}
    }

    public enum BlockTags {
        GLASS_COLORLESS("colorless_glass", "glass/colorless");

        public final TagKey<Block> tag;

        // luncheon tag
        BlockTags(String luncheonID) {
            tag = TagKey.create(BuiltInRegistries.BLOCK.key(), Luncheon.asResource(luncheonID));
        }

        // common tag (same path)
        BlockTags(String commonID, boolean common) {
            if(common) {
                tag = TagKey.create(BuiltInRegistries.BLOCK.key(), this.makeCommonTag(commonID, commonID));
                return;
            }
            tag = TagKey.create(BuiltInRegistries.BLOCK.key(), Luncheon.asResource(commonID));
        }

        // common tag (different paths)
        BlockTags(String fabricID, String forgeID) {
            tag = TagKey.create(BuiltInRegistries.BLOCK.key(), this.makeCommonTag(fabricID, forgeID));
        }

        private ResourceLocation makeCommonTag(String fabricID, String forgeID) {
            String platform = Services.PLATFORM.getPlatformName();
            if(platform.equals("Forge")) {
                return Luncheon.at("forge", forgeID);
            } else {
                return Luncheon.at("c", fabricID);
            }
        }

        public static void init() {}
    }

    public static void init() {
        ItemTags.init();
        BlockTags.init();
    }
}
