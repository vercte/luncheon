package net.vercte.luncheon.content.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.vercte.luncheon.content.misc.SpicyEffect;
import net.vercte.luncheon.content.registry.LuncheonBlocks;
import net.vercte.luncheon.content.registry.LuncheonItems;
import net.vercte.luncheon.content.registry.LuncheonMobEffects;
import org.lwjgl.system.NonnullDefault;
import vectorwing.farmersdelight.common.block.PieBlock;

@NonnullDefault
public class BlazeCakeBlock extends PieBlock {
    public BlazeCakeBlock(BlockBehaviour.Properties properties) {
        super(properties, LuncheonItems.BLAZE_CAKE_SLICE::get);
    }

    @Override
    protected InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player playerIn) {
        if (playerIn.canEat(false)) {
            playerIn.setSecondsOnFire(4);
            playerIn.addEffect(new MobEffectInstance(LuncheonMobEffects.SPICY.get(), 8 * 20));
        }

        return super.consumeBite(level, pos, state, playerIn);
    }

    public static LootTable.Builder buildLootTable() {
        LootItemCondition.Builder survivesExplosion = ExplosionCondition.survivesExplosion();
        BlazeCakeBlock block = LuncheonBlocks.BLAZE_CAKE.get();
        LootTable.Builder builder = LootTable.lootTable();
        LootPool.Builder poolBuilder = LootPool.lootPool();

        poolBuilder.add(LootItem.lootTableItem(AllItems.BLAZE_CAKE)
                .when(survivesExplosion)
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(BlazeCakeBlock.BITES, 0))));

        builder.withPool(poolBuilder.setRolls(ConstantValue.exactly(1)));
        return builder;
    }
}
