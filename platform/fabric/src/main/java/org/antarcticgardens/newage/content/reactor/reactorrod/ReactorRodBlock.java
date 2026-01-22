package org.antarcticgardens.newage.content.reactor.reactorrod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.antarcticgardens.newage.NewAgeBlockEntityTypes;
import org.antarcticgardens.newage.NewAgeBlocks;
import org.antarcticgardens.newage.content.reactor.ReactorBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReactorRodBlock extends ReactorBlock implements EntityBlock {
    public ReactorRodBlock(Properties properties) {
        super(properties
                .isViewBlocking((blockState, blockGetter, blockPos) -> false)
                .lightLevel(state -> state.getValue(ACTIVE) ? 12 : 0));
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y));
    }

    protected static final VoxelShape Y_AXIS_AABB = Block.box(4.5, 0.0, 4.5, 11.5, 16.0, 11.5);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(4.5, 4.5, 0.0, 11.5, 11.5, 16.0);
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0, 4.5, 4.5, 16.0, 11.5, 11.5);

    public static final BooleanProperty ACTIVE = BlockStateProperties.LIT;
    public static final EnumProperty<Direction.Axis> AXIS = EnumProperty.create("axis", Direction.Axis.class);

    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }



    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(ACTIVE, false).setValue(AXIS, context.getClickedFace().getAxis());
    }

    public VoxelShape getShape(BlockState arg, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
        return switch ((arg.getValue(AXIS))) {
            case X -> X_AXIS_AABB;
            case Z -> Z_AXIS_AABB;
            case Y -> Y_AXIS_AABB;
        };
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        if (state.getValue(ACTIVE)) {
            level.setBlock(pos, NewAgeBlocks.CORIUM.getDefaultState(), 3);
            if (level instanceof ServerLevel lvl) {
                lvl.explode(null, pos.getX(), pos.getY(), pos.getZ(), 2.0f, true, Level.ExplosionInteraction.TNT);
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return state.getValue(ACTIVE) ? new ArrayList<>() : super.getDrops(state, params);
    }


    @Override
    public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
        if (state.getValue(ACTIVE))
            return InteractionResult.PASS;
        else {
            return super.onSneakWrenched(state, context);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE).add(AXIS);
    }

    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return NewAgeBlockEntityTypes.REACTOR_ROD.create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return (level1, blockPos, blockState, blockEntity) -> {
            if (!(blockEntity instanceof ReactorRodBlockEntity ent)) return;
            ent.tick(blockPos, level1, blockState);
        };
    }

}