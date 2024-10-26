package net.vercte.luncheon.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.vercte.luncheon.content.registry.LuncheonBlocks;
import org.lwjgl.system.NonnullDefault;
import vectorwing.farmersdelight.common.block.BuddingBushBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

@NonnullDefault
public class BuddingFireberryBlock extends BuddingBushBlock implements BonemealableBlock {
    public BuddingFireberryBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return LuncheonBlocks.BUDDING_FIREBERRY_CROP.getDefaultState();
    }

    @Override
    public boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlocks.RICH_SOIL_FARMLAND.get()) || state.is(Blocks.FARMLAND);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(BuddingBushBlock.AGE) == 4) {
            level.setBlock(currentPos, LuncheonBlocks.FIREBERRY_CROP.get().defaultBlockState(), 3);
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canGrowPastMaxAge() {
        return true;
    }

    @Override
    public void growPastMaxAge(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlockAndUpdate(pos, LuncheonBlocks.FIREBERRY_CROP.get().defaultBlockState());
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    protected int getBonemealAgeIncrease(Level level) {
        return Mth.nextInt(level.random, 1, 4);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int maxAge = getMaxAge();
        int ageGrowth = Math.min(getAge(state) + getBonemealAgeIncrease(level), 7);
        if (ageGrowth <= maxAge) {
            level.setBlockAndUpdate(pos, state.setValue(AGE, ageGrowth));
        } else {
            int remainingGrowth = ageGrowth - maxAge - 1;
            level.setBlockAndUpdate(pos, LuncheonBlocks.FIREBERRY_CROP.get().defaultBlockState().setValue(FireberryVineBlock.VINE_AGE, remainingGrowth));
        }
    }
}
