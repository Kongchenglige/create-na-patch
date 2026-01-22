package org.antarcticgardens.newage.content.heat.heatpump;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.antarcticgardens.newage.NewAgeBlockEntityTypes;
import org.antarcticgardens.newage.content.heat.HeatBlockEntity;
import org.jetbrains.annotations.Nullable;

import static org.antarcticgardens.newage.content.heat.heatpipe.HeatPipeBlock.massPipe;
import static org.antarcticgardens.newage.content.heat.heatpipe.HeatPipeBlock.updateState;

public class HeatPumpBlock extends Block implements EntityBlock, IWrenchable {
    public HeatPumpBlock(Properties properties) {
        super(properties);
    }

    public static BooleanProperty UP = BlockStateProperties.UP;
    public static BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static BooleanProperty EAST = BlockStateProperties.EAST;
    public static BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static BooleanProperty WEST = BlockStateProperties.WEST;

    public static DirectionProperty FACING = BlockStateProperties.FACING;

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction dir = context.getClickedFace();
        if (!context.isSecondaryUseActive())
            dir = dir.getOpposite();

        state = state.setValue(FACING, dir);

        return updateState(state, world, pos);
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
        Direction facing = state.getValue(FACING);
        if (state.getValue(UP) || facing == Direction.UP) {
            shape = Shapes.join(shape,
                    Shapes.box(0.25, 0.75, 0.25, 0.75, 1.0, 0.75),
                    BooleanOp.OR);
        }
        if (state.getValue(DOWN) || facing == Direction.DOWN) {
            shape = Shapes.join(shape,
                    Shapes.box(0.25, 0.0, 0.25, 0.75, 0.25, 0.75),
                    BooleanOp.OR);
        }
        if (state.getValue(NORTH) || facing == Direction.NORTH) {
            shape = Shapes.join(shape,
                    Shapes.box(0.25, 0.25, 0.0, 0.75, 0.75, 0.25),
                    BooleanOp.OR);
        }
        if (state.getValue(EAST) || facing == Direction.EAST) {
            shape = Shapes.join(shape,
                    Shapes.box(0.75, 0.25, 0.25, 1.0, 0.75, 0.75),
                    BooleanOp.OR);
        }
        if (state.getValue(SOUTH) || facing == Direction.SOUTH) {
            shape = Shapes.join(shape,
                    Shapes.box(0.25, 0.25, 0.75, 0.75, 0.75, 1.0),
                    BooleanOp.OR);
        }
        if (state.getValue(WEST) || facing == Direction.WEST) {
            shape = Shapes.join(shape,
                    Shapes.box(0, 0.25, 0.25, 0.25, 0.75, 0.75),
                    BooleanOp.OR);
        }

        return shape;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return NewAgeBlockEntityTypes.HEAT_PUMP.create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        final int on = massPipe;
        massPipe++;
        if (massPipe >= 20) {
            massPipe = 0;
        }
        return (world, blockPos, blockState, sel) -> {
            if ((world.getGameTime() + on) % 20 != 0 || !(sel instanceof HeatPumpBlockEntity self) || self.getLevel() == null) return;
            self.lastPump = 0;
            Direction facing = state.getValue(FACING);
            BlockEntity entity = world.getBlockEntity(blockPos.relative(facing));
            if (entity instanceof HeatBlockEntity hbe && hbe.canAdd(facing)) {
                float ht = self.heat;
                hbe.addHeat(ht);
                self.lastPump += ht;
                self.heat -= ht;
                if (self.heat > 0) {
                    self.heat = Math.max(0, self.heat-4);
                }
                self.setChanged();
            }

            HeatBlockEntity.handleOverheat(self);
            HeatBlockEntity.trySync(self);
        };
    }
}
