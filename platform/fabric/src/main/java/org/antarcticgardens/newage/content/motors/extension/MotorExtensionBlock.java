package org.antarcticgardens.newage.content.motors.extension;


import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.antarcticgardens.newage.content.motors.MotorBlock;
import org.antarcticgardens.newage.content.motors.MotorBlockEntity;
import org.antarcticgardens.newage.content.motors.extension.variants.IMotorExtensionVariant;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.antarcticgardens.newage.content.reactor.reactorfuelacceptor.ReactorFuelAcceptorBlock.FACING;

public class MotorExtensionBlock extends Block implements IBE<MotorExtensionBlockEntity>, IWrenchable {
    protected static final VoxelShape Y_AXIS_AABB = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(2.0, 2.0, 0.0, 14.0, 14.0, 16.0);
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0, 2.0, 2.0, 16.0, 14.0, 14.0);
    private final BlockEntityEntry<MotorExtensionBlockEntity> entry;
    private final IMotorExtensionVariant variant;

    public MotorExtensionBlock(Properties properties, BlockEntityEntry<MotorExtensionBlockEntity> entry, IMotorExtensionVariant variant) {
        super(properties);
        this.entry = entry;
        this.variant = variant;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.create_new_age.motor_extension").withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(Lang.translate("tooltip.create_new_age.stress_limit_multiplier").style(ChatFormatting.GRAY)
                .component());
        tooltip.add(Lang.text(" ").add(Lang.number((int)(variant.getMultiplier() * 100)).text("%").style(ChatFormatting.AQUA)).component());

        tooltip.add(Lang.translate("tooltip.create_new_age.additional_capacity").style(ChatFormatting.GRAY)
                .component());
        tooltip.add(Lang.text(" ").add(Lang.number(variant.getExtraCapacity()).text("⚡").style(ChatFormatting.AQUA)).component());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace().getOpposite();
        BlockPos pos = context.getClickedPos();
        if (context.getPlayer() != null && context.getPlayer().isCrouching()) {
            direction = direction.getOpposite();
        } else if (!(context.getLevel().getBlockState(pos.relative(direction)).getBlock() instanceof MotorBlock)) {
            for (Direction d : Direction.values()) {
                if (context.getLevel().getBlockState(pos.relative(d)).getBlock() instanceof MotorBlock) {
                    direction = d;
                    break;
                }
            }
        }

        BlockState blockState = super.getStateForPlacement(context).setValue(FACING, direction);
        return blockState;
    }

    @Override
    public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        if (world instanceof ServerLevel) {
            if (player != null && !player.isCreative()) {
                Block.getDrops(state, (ServerLevel)world, pos, world.getBlockEntity(pos), player, context.getItemInHand()).forEach((itemStack) -> {
                    player.getInventory().placeItemBackInInventory(itemStack);
                });
            }

            state.spawnAfterBreak((ServerLevel)world, pos, ItemStack.EMPTY, true);
            world.destroyBlock(pos, false);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        if (state.hasBlockEntity()) {
            BlockEntity entity = context.getLevel().getBlockEntity(context.getClickedPos());
            if (entity instanceof MotorBlockEntity en) {
                en.needsPower = !en.needsPower;
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    public VoxelShape getShape(BlockState arg, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
        return switch ((arg.getValue(FACING)).getAxis()) {
            case X -> X_AXIS_AABB;
            case Z -> Z_AXIS_AABB;
            case Y -> Y_AXIS_AABB;
        };
    }

    @Override
    public Class<MotorExtensionBlockEntity> getBlockEntityClass() {
        return MotorExtensionBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends MotorExtensionBlockEntity> getBlockEntityType() {
        return entry.get();
    }
}
