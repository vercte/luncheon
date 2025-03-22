package net.vercte.luncheon.foundation.data;

import com.google.common.collect.Sets;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.vercte.luncheon.content.registry.LuncheonBlocks;
import net.vercte.luncheon.content.registry.LuncheonItems;
import net.vercte.luncheon.content.registry.LuncheonTags;
import net.vercte.luncheon.foundation.data.advancement.LuncheonAdvancement;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static net.vercte.luncheon.foundation.data.advancement.LuncheonAdvancement.TaskType.*;

@NonnullDefault
@SuppressWarnings("unused")
public class LuncheonAdvancements implements DataProvider {
    public static final List<LuncheonAdvancement> ENTRIES = new ArrayList<>();

    public static @Nullable final LuncheonAdvancement START = null,

    ROOT = create("root", b -> b.icon(LuncheonItems.ICE_CUBE)
            .title("A Wonderful Luncheon")
            .description("I hope you're prepared!")
            .awardedForFree()
            .special(SILENT)),

    MECHANICAL_COOLER = create("mechanical_cooler", b -> b.icon(LuncheonBlocks.MECHANICAL_COOLER)
            .title("They freeze now?")
            .description("Meet the Blaze Burner's cooler, mechanical cousin")
            .after(ROOT).whenIconCollected()),

    ICE_CUBE = create("ice_cube", b -> b.icon(LuncheonItems.ICE_CUBE)
            .title("Crispy and Crunchy")
            .description("Eat an ice cube... Delicious!")
            .after(MECHANICAL_COOLER).special(SECRET)),

    ICE_CREAM = create("ice_cream", b -> b.icon(LuncheonItems.PLAIN_ICE_CREAM)
            .title("Ice Cream...")
            .description("You scream...")
            .after(MECHANICAL_COOLER)
            .whenItemCollected(LuncheonTags.ItemTags.ICE_CREAM_CONES.tag)),

    NEAPOLITAN_SUNDAE = create("neapolitan_sundae", b -> b.icon(LuncheonItems.NEAPOLITAN_SUNDAE)
            .title("Brain Freeze")
            .description("Eat your first Neapolitan Sundae") // TODO: FIX THIS SHIT
            .after(ICE_CREAM).special(EXPERT)),

    WORLDS_COLDEST = create("worlds_coldest", b -> b.icon(LuncheonItems.NEAPOLITAN_SUNDAE)
            .title("World's Coldest")
            .description("Make 64 sundaes with the same deployer, successfully automating the Neapolitan Sundae")
            .after(NEAPOLITAN_SUNDAE).special(CHALLENGE));


    private final PackOutput output;
    public LuncheonAdvancements(PackOutput output) {
        this.output = output;
    }

    private static LuncheonAdvancement create(String id, UnaryOperator<LuncheonAdvancement.Builder> b) {
        return new LuncheonAdvancement(id, b);
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
        for (LuncheonAdvancement advancement : ENTRIES)
            advancement.provideLang(consumer);
    }

    @Override
    public String getName() {
        return "Luncheon's Advancements";
    }
}
