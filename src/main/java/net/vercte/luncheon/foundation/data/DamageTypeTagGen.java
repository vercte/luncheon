package net.vercte.luncheon.foundation.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.registry.LuncheonDamageTypes;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class DamageTypeTagGen extends TagsProvider<DamageType> {
    public DamageTypeTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, lookupProvider, Luncheon.ID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(DamageTypeTags.BYPASSES_ARMOR)
                .add(LuncheonDamageTypes.GLASS_SHARDS)
                .add(LuncheonDamageTypes.GLASS_SPIKED);
    }

    @Override @Nonnull
    public String getName() {
        return "Luncheon's Damage Type Tags";
    }
}
