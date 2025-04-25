package net.vercte.luncheon.content.processing.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.deployer.DeployerRecipeSearchEvent;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.registry.LuncheonItems;
import net.vercte.luncheon.registry.LuncheonTags;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@ParametersAreNonnullByDefault
@Mod.EventBusSubscriber
public class GlassShardApplicationRecipe implements Recipe<RecipeWrapper> {
    private final RecipeWrapper inventory;
    public GlassShardApplicationRecipe(RecipeWrapper inventory) {
        this.inventory = inventory;
    }

    public static Optional<GlassShardApplicationRecipe> fromInventory(RecipeWrapper inventory) {
        return Optional.of(new GlassShardApplicationRecipe(inventory));
    }

    @SubscribeEvent
    public static void addDeployerRecipe(DeployerRecipeSearchEvent event) {
        Optional<GlassShardApplicationRecipe> recipe = fromInventory(event.getInventory());
        if(recipe.isEmpty()) return;
        if(recipe.get().matches(event.getInventory(), event.getBlockEntity().getLevel()))
            event.addRecipe(() -> recipe, 50);
    }

    @Override
    public boolean matches(RecipeWrapper inv, @Nonnull Level level) {
        return inv.getItem(0).isEdible() &&
                !inv.getItem(0).is(LuncheonTags.ItemTags.GLASS_SHARD_INCOMPATIBLE.tag) &&
                !inv.getItem(0).getOrCreateTag().getBoolean("luncheon.spiked") &&
                getRequiredHeldItem().test(inv.getItem(1));
    }

    public Ingredient getRequiredHeldItem() {
        return Ingredient.of(LuncheonItems.GLASS_SHARDS);
    }

    @Override @Nonnull
    public ItemStack assemble(RecipeWrapper inv, RegistryAccess access) {
        ItemStack copied = inv.getItem(0).copy();
        copied.setCount(1);
        copied.getOrCreateTag().putBoolean("luncheon.spiked", true);
        return copied;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override @Nonnull
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        ItemStack copied = inventory.getItem(0).copy();
        copied.setCount(1);
        copied.getOrCreateTag().putBoolean("luncheon.spiked", true);
        return copied;
    }

    @Override @Nonnull
    public ResourceLocation getId() {
        return Luncheon.asResource("glass_shard_spiking");
    }

    @Override @Nonnull
    public RecipeSerializer<?> getSerializer() {
        return AllRecipeTypes.DEPLOYING.getSerializer();
    }

    @Override @Nonnull
    public RecipeType<?> getType() {
        return AllRecipeTypes.DEPLOYING.getType();
    }
}
