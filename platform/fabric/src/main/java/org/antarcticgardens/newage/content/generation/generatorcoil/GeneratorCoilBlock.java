package org.antarcticgardens.newage.content.generation.generatorcoil;

import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.antarcticgardens.newage.CreateNewAge;
import org.antarcticgardens.newage.NewAgeBlockEntityTypes;

public class GeneratorCoilBlock extends RotatedPillarKineticBlock implements IBE<GeneratorCoilBlockEntity> {
    public GeneratorCoilBlock(Properties properties) {
        super(properties.strength(2.0F, 1.0F));
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(AXIS);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.getValue(AXIS);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        if (player.isShiftKeyDown() || !player.mayBuild())
            return InteractionResult.PASS;

        ItemStack itemInHand = player.getItemInHand(hand);
        
        IPlacementHelper helper = PlacementHelpers.get(CreateNewAge.getMagnetPlacementHelperId());
        if (helper.matchesItem(itemInHand))
            return helper.getOffset(player, world, state, pos, ray)
                    .placeInWorld(world, (BlockItem) itemInHand.getItem(), player, hand, ray);

        return InteractionResult.PASS;
    }

    @Override
    public Class<GeneratorCoilBlockEntity> getBlockEntityClass() {
        return GeneratorCoilBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends GeneratorCoilBlockEntity> getBlockEntityType() {
        return NewAgeBlockEntityTypes.GENERATOR_COIL.get();
    }
}
