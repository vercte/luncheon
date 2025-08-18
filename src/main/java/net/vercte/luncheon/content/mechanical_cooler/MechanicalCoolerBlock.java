package net.vercte.luncheon.content.mechanical_cooler;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.vercte.luncheon.LuncheonBlockEntities;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class MechanicalCoolerBlock extends KineticBlock implements IBE<MechanicalCoolerBlockEntity> {
    public static EnumProperty<CoolingCondition> COOLING = EnumProperty.create("cooling", CoolingCondition.class);

    public MechanicalCoolerBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(COOLING, CoolingCondition.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(COOLING);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return Block.box(1, 0, 1, 15, 14, 15);
    }

    @Override
    public Class<MechanicalCoolerBlockEntity> getBlockEntityClass() { return MechanicalCoolerBlockEntity.class; }

    @Override
    public BlockEntityType<? extends MechanicalCoolerBlockEntity> getBlockEntityType() {
        return LuncheonBlockEntities.MECHANICAL_COOLER.get();
    }
}
