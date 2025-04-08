package net.vercte.luncheon.foundation.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;
import net.vercte.luncheon.content.misc.LuncheonDamageTypes;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class GeneratedEntriesProvider extends RegistriesDatapackGenerator {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, LuncheonDamageTypes::bootstrap);

    public GeneratedEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries.thenApply((r) -> constructRegistries(r, BUILDER)));
    }
    private static HolderLookup.Provider constructRegistries(HolderLookup.Provider original, RegistrySetBuilder datapackEntriesBuilder) {
        HashSet<? extends ResourceKey<? extends Registry<?>>> builderKeys = new HashSet(datapackEntriesBuilder.entries.stream().map(RegistrySetBuilder.RegistryStub::key).toList());
        getDataPackRegistriesWithDimensions().filter((data) -> !builderKeys.contains(data.key())).forEach((data) -> datapackEntriesBuilder.add(data.key(), (context) -> {
        }));
        return datapackEntriesBuilder.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), original);
    }

    public static Stream<RegistryDataLoader.RegistryData<?>> getDataPackRegistriesWithDimensions() {
        return Stream.concat(RegistryDataLoader.WORLDGEN_REGISTRIES.stream(), RegistryDataLoader.DIMENSION_REGISTRIES.stream());
    }

    @Override
    public @NotNull String getName() {
        return "Luncheon's Generated Registry Entries";
    }
}
