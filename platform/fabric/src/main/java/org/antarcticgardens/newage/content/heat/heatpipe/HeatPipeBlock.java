package org.antarcticgardens.newage.content.heat.heatpipe;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.fluids.tank.BoilerHeaters;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.antarcticgardens.newage.NewAgeBlockEntityTypes;
import org.antarcticgardens.newage.config.NewAgeConfig;
import org.antarcticgardens.newage.content.heat.HeatBlockEntity;
import org.jetbrains.annotations.Nullable;

public class HeatPipeBlock extends Block implements EntityBlock, IWrenchable {
    public HeatPipeBlock(Properties properties) {
        super(properties);
    }

    public static BooleanProperty UP = BlockStateProperties.UP;
    public static BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static BooleanProperty EAST = BlockStateProperties.EAST;
    public static BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static BooleanProperty WEST = BlockStateProperties.WEST;

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }


    private static BlockState checkHeatBlock(BlockState state, LevelAccessor world, BlockPos pos, Direction dir, BooleanProperty property) {
        return state.setValue(property,
                world.getBlockEntity(pos.relative(dir)) instanceof HeatBlockEntity hbe
                        && hbe.canConnect(dir));

    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return updateState(state, world, pos);
    }

    public static BlockState updateState(BlockState state, LevelAccessor world, BlockPos pos) {
        state = checkHeatBlock(state, world, pos, Direction.DOWN, DOWN);
        state = checkHeatBlock(state, world, pos, Direction.UP, UP);
        state = checkHeatBlock(state, world, pos, Direction.NORTH, NORTH);
        state = checkHeatBlock(state, world, pos, Direction.EAST, EAST);
        state = checkHeatBlock(state, world, pos, Direction.SOUTH, SOUTH);
        state = checkHeatBlock(state, world, pos, Direction.WEST, WEST);
        return state;
    }



    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return updateState(state, world, pos);
    }


    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        level.scheduleTick(pos, this, 1);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        level.scheduleTick(pos, this, 1);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.box(0.25, 0.25, 0.25, 0.75, 0.75, 0.75);
        if (state.getValue(UP)) {
            shape = Shapes.join(shape,
                    Shapes.box(0.25, 0.75, 0.25, 0.75, 1.0, 0.75),
                    BooleanOp.OR);
        }
        if (state.getValue(DOWN)) {
            shape = Shapes.join(shape,
                    Shapes.box(0.25, 0.0, 0.25, 0.75, 0.25, 0.75),
                    BooleanOp.OR);
        }
        if (state.getValue(NORTH)) {
            shape = Shapes.join(shape,
                    Shapes.box(0.25, 0.25, 0.0, 0.75, 0.75, 0.25),
                    BooleanOp.OR);
        }
        if (state.getValue(EAST)) {
            shape = Shapes.join(shape,
                    Shapes.box(0.75, 0.25, 0.25, 1.0, 0.75, 0.75),
                    BooleanOp.OR);
        }
        if (state.getValue(SOUTH)) {
            shape = Shapes.join(shape,
                    Shapes.box(0.25, 0.25, 0.75, 0.75, 0.75, 1.0),
                    BooleanOp.OR);
        }
        if (state.getValue(WEST)) {
            shape = Shapes.join(shape,
                    Shapes.box(0, 0.25, 0.25, 0.25, 0.75, 0.75),
                    BooleanOp.OR);
        }

        return shape;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return NewAgeBlockEntityTypes.HEAT_PIPE.create(pos, state);
    }


    public static int massPipe = 0;
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        final int on = massPipe;
        massPipe++;
        if (massPipe >= 20) {
            massPipe = 0;
        }
        return (world, blockPos, blockState, self) -> {
            if ((world.getGameTime() + on) % 20 != 0 || !(self instanceof HeatPipeBlockEntity selfC)) return;

            BlockPos heatPos = blockPos.below();

            float heat = BoilerHeaters.getActiveHeat(level, blockPos, level.getBlockState(heatPos));

            selfC.generating = 0;

            double loss = NewAgeConfig.getCommon().passivePipeHeatLoss.get();

            selfC.heat = (float) Math.max(0, selfC.heat - loss);

            if (heat >= 0) {
                selfC.generating = ((1+heat*3) * 6);
                selfC.generating = (float) Math.max(Math.min(selfC.generating, ((2+Math.pow(2, heat)) * 300) - selfC.heat), 0);
                selfC.heat += selfC.generating;
            }

            selfC.generating -= (float) loss;

            HeatBlockEntity.transferAround(selfC);
            HeatBlockEntity.handleOverheat(selfC);
            HeatBlockEntity.trySync(selfC);
        };
    }
}
