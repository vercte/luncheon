package net.vercte.luncheon.content.processing.recipe;

import com.simibubi.create.content.kinetics.deployer.DeployerRecipeSearchEvent;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.ByteTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.content.registry.LuncheonItems;
import net.vercte.luncheon.content.registry.LuncheonRecipeTypes;
import net.vercte.luncheon.content.registry.LuncheonTags;
import org.lwjgl.system.NonnullDefault;

import java.util.Optional;

@NonnullDefault
@Mod.EventBusSubscriber
public class GlassShardApplicationRecipe extends ProcessingRecipe<RecipeWrapper> {
    public GlassShardApplicationRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(LuncheonRecipeTypes.GLASS_SHARD_APPLICATION, params);
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv, RegistryAccess registryAccess) {
        ItemStack copied = inv.getItem(0).copy();
        copied.getOrCreateTag().putBoolean("luncheon.spiked", true);
        return copied;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        return inv.getItem(0).isEdible() &&
              !inv.getItem(0).is(LuncheonTags.ItemTags.GLASS_SHARD_INCOMPATIBLE.tag) &&
              !inv.getItem(0).getOrCreateTag().getBoolean("luncheon.spiked") &&
               getRequiredHeldItem().test(inv.getItem(1));
    }

    @Override
    protected int getMaxInputCount() {
        return 2;
    }

    @Override
    protected int getMaxOutputCount() {
        return 4;
    }

    public Ingredient getRequiredHeldItem() {
        return Ingredient.of(LuncheonItems.GLASS_SHARDS);
    }

    @SubscribeEvent
    public static void addDeployerRecipe(DeployerRecipeSearchEvent event) {
        event.addRecipe(() -> checkRecipe(LuncheonRecipeTypes.GLASS_SHARD_APPLICATION, event.getInventory(), event.getBlockEntity().getLevel()), 50);
    }

    private static Optional<? extends Recipe<? extends Container>> checkRecipe(LuncheonRecipeTypes type, RecipeWrapper inv, Level level) {
        return type.find(inv, level);
    }
}
