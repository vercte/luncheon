package net.vercte.luncheon.foundation.data;

import com.google.common.collect.Sets;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.registry.LuncheonBlocks;
import net.vercte.luncheon.content.registry.LuncheonItems;
import net.vercte.luncheon.content.registry.LuncheonTags;
import net.vercte.luncheon.foundation.data.advancement.LuncheonAdvancement;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LuncheonAdvancements implements DataProvider {
    public static final List<LuncheonAdvancement> ENTRIES = new ArrayList<>();

    public static final LuncheonAdvancement BASE = null,
        ROOT = create("root", LuncheonItems.ICE_CUBE)
                .name("A Wonderful Luncheon").description("I hope you're prepared!")
                .free().silent().build(),

        MECHANICAL_COOLER = create("mechanical_cooler", LuncheonBlocks.MECHANICAL_COOLER)
                .name("They freeze now?").description("Obtain a Mechanical Cooler")
                .after(ROOT).onIconCollected().build(),

        ICE_CUBE = create("ice_cube", LuncheonItems.ICE_CUBE)
                .name("Crispy Crunchy Watery").description("Eat an ice cube... Delicious!")
                .after(MECHANICAL_COOLER).onIconConsumed().secret().build(),

        ICE_CREAM = create("ice_cream", LuncheonItems.PLAIN_ICE_CREAM)
                .name("You Scream").description("Eat your first Ice Cream of any kind")
                .after(MECHANICAL_COOLER).onItemConsumed(LuncheonTags.ItemTags.ICE_CREAM_CONES.tag).build(),

        NEAPOLITAN_SUNDAE = create("neapolitan_sundae", LuncheonItems.NEAPOLITAN_SUNDAE)
                .name("Brain Freeze").description("Eat your first Neapolitan Sundae")
                .after(ICE_CREAM).onIconConsumed().goal().build(),

        WORLDS_COLDEST = create("worlds_coldest", LuncheonItems.NEAPOLITAN_SUNDAE)
                .name("World's Coldest").description("Fully automate the Neapolitan Sundae")
                .after(NEAPOLITAN_SUNDAE).onItemAutomated(LuncheonItems.NEAPOLITAN_SUNDAE).challenge().build();

    private static LuncheonAdvancement.Builder create(String id, ItemLike icon) {
        return new LuncheonAdvancement.Builder(id, icon);
    }

    private final PackOutput output;
    public LuncheonAdvancements(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
       PackOutput.PathProvider pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "advancements");
        List<CompletableFuture<?>> futures = new ArrayList<>();

        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            ResourceLocation id = advancement.getId();
            if (!set.add(id))
                throw new IllegalStateException("Duplicate advancement " + id);
            Path path = pathProvider.json(id);
            futures.add(DataProvider.saveStable(cache, advancement.deconstruct()
                    .serializeToJson(), path));
        };

        for (LuncheonAdvancement advancement : ENTRIES)
            advancement.save(consumer);

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    public static void provideLang(BiConsumer<String, String> consumer) {
        for(LuncheonAdvancement advancement : ENTRIES)
            advancement.provideLang(consumer);
    }

    public static ResourceLocation getBackground() {
        return Luncheon.asResource("textures/gui/advancements.png");
    }

    @Override
    public String getName() {
        return "Luncheon's Advancements";
    }

    public static void init() {}
}
