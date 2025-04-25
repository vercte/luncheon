package net.vercte.luncheon.content.processing.cooler;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.vercte.luncheon.registry.LuncheonBlockEntityTypes;
import net.vercte.luncheon.foundation.utility.LuncheonLang;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
@SuppressWarnings("deprecation")
public class CoolerBlock extends KineticBlock implements IBE<CoolerBlockEntity> {
    public static final EnumProperty<CoolingLevel> COOL_LEVEL = EnumProperty.create("cool_level", CoolingLevel.class);

    public CoolerBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(COOL_LEVEL, CoolingLevel.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(COOL_LEVEL);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(state, world, pos, oldState, moving);
        if (world.isClientSide)
            return;
        BlockEntity blockEntity = world.getBlockEntity(pos.above());
        if (!(blockEntity instanceof BasinBlockEntity basin))
            return;
        basin.notifyChangeOfContents();
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.MEDIUM;
    }

    public static boolean hasPipeTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
            return face.getAxis() != Direction.Axis.Y;
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
    public Class<CoolerBlockEntity> getBlockEntityClass() {
        return CoolerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CoolerBlockEntity> getBlockEntityType() {
        return LuncheonBlockEntityTypes.MECHANICAL_COOLER.get();
    }

    public static int getLight(BlockState state) {
        CoolingLevel level = state.getValue(COOL_LEVEL);
        if(level == CoolingLevel.COOLED) return 8;
        return 2;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return AllShapes.HEATER_BLOCK_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter reader, BlockPos pos,
                                        CollisionContext context) {
        if (context == CollisionContext.empty())
            return AllShapes.HEATER_BLOCK_SPECIAL_COLLISION_SHAPE;
        return getShape(state, reader, pos, context);
    }

    public static CoolingLevel getCoolingLevelOf(BlockState blockState) {
        return blockState.getValue(COOL_LEVEL);
    }

    public enum CoolingLevel implements StringRepresentable {
        NONE, COOLED;

        public static CoolingLevel byIndex(int index) {
            return values()[index];
        }

        @Override
        public String getSerializedName() {
            return LuncheonLang.asId(name());
        }
    }
}
