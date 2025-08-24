package net.vercte.luncheon.foundation.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.foundation.data.recipe.LuncheonRecipeProvider;
import net.vercte.luncheon.foundation.data.recipe.LuncheonSequencedAssemblyGen;
import net.vercte.luncheon.foundation.data.recipe.LuncheonStandardRecipeGen;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class LuncheonDatagen {
    public static void gatherData(GatherDataEvent event) {
        addExtraRegistrateData();

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if(event.includeServer()) {
            GeneratedEntriesProvider generatedEntriesProvider = new GeneratedEntriesProvider(output, lookupProvider);
            lookupProvider = generatedEntriesProvider.getRegistryProvider();
            generator.addProvider(true, generatedEntriesProvider);

            generator.addProvider(true, new LuncheonStandardRecipeGen(output));
            generator.addProvider(true, new DamageTypeTagGen(output, lookupProvider, existingFileHelper));
            generator.addProvider(true, new LuncheonAdvancements(output));
            generator.addProvider(true, new LuncheonSequencedAssemblyGen(output));

            LuncheonRecipeProvider.registerAllProcessing(generator, output);
        }
    }

    private static void addExtraRegistrateData() {
        Luncheon.REGISTRATE.get().addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;

            LuncheonAdvancements.provideLang(langConsumer);
            provideDefaultLang("interface", langConsumer);
        });
    }

    @SuppressWarnings("SameParameterValue")
    private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
        String path = "assets/luncheon/lang/default/" + fileName + ".json";
        JsonElement jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null) {
            throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            consumer.accept(key, value);
        }
    }
}
