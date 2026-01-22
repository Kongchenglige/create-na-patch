package org.antarcticgardens.newage.content.energiser;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.antarcticgardens.newage.NewAgeBlockEntityTypes;
import org.antarcticgardens.newage.config.NewAgeConfig;
import org.antarcticgardens.newage.tools.StringFormattingTool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnergiserBlock extends HorizontalKineticBlock implements IBE<EnergiserBlockEntity> {
    private final int tier;

    private BlockEntityEntry<EnergiserBlockEntity> entry;

    public EnergiserBlock(Properties properties, int tier, BlockEntityEntry<EnergiserBlockEntity> entry) {
        super(properties.strength(2.5F, 1.0F));
        this.entry = entry;
        this.tier = tier;
    }

    public static int getStrength(int tier) {
        return (int) ((Math.pow(4, tier)) * NewAgeConfig.getCommon().energiserSpeedMultiplier.get());
    }

    public static long getCapacity(int tier) {
        return (long) (Math.pow(10, tier) * 1000);
    }


    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (context instanceof EntityCollisionContext && ((EntityCollisionContext) context).getEntity() instanceof Player)
            return AllShapes.CASING_14PX.get(Direction.DOWN);

        return AllShapes.MECHANICAL_PROCESSOR_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction preferredSide = getPreferredHorizontalFacing(context);
        if (preferredSide != null)
            return defaultBlockState().setValue(HORIZONTAL_FACING, preferredSide);
        return super.getStateForPlacement(context);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(HORIZONTAL_FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.getValue(HORIZONTAL_FACING).getAxis();
    }

    @Override
    public Class<EnergiserBlockEntity> getBlockEntityClass() {
        return EnergiserBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends EnergiserBlockEntity> getBlockEntityType() {
        return entry.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Lang.translate("tooltip.create_new_age.speed").style(ChatFormatting.GRAY).component());
        tooltip.add(Lang.text(" ").translate("tooltip.create_new_age.energy_per_tick", StringFormattingTool.formatLong(getStrength(tier))).style(ChatFormatting.AQUA)
                        .add(Lang.text(" ").translate("tooltip.create_new_age.per_rpm", 10).style(ChatFormatting.GRAY))
                .component());
        tooltip.add(Lang.translate("tooltip.create_new_age.stores").style(ChatFormatting.GRAY).component());
        tooltip.add(Lang.text(" ").translate("tooltip.create_new_age.energy", StringFormattingTool.formatLong(getCapacity(tier))).style(ChatFormatting.AQUA)
                .component());
    }

    public static Block createT1(Properties properties) {
        return new EnergiserBlock(properties, 1, NewAgeBlockEntityTypes.ENERGISER_T1);
    }

    public static Block createT2(Properties properties) {
        return new EnergiserBlock(properties, 2, NewAgeBlockEntityTypes.ENERGISER_T2);
    }

    public static Block createT3(Properties properties) {
        return new EnergiserBlock(properties, 3, NewAgeBlockEntityTypes.ENERGISER_T3);
    }

}
