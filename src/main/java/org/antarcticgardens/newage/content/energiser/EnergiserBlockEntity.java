package org.antarcticgardens.newage.content.energiser;

import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.antarcticgardens.newage.energy.InsertOnlyResizableEnergyContainer;
import org.antarcticgardens.newage.tools.StringFormattingTool;

import java.util.List;

public class EnergiserBlockEntity extends KineticBlockEntity implements BotariumEnergyBlock<WrappedBlockEnergyContainer> {
    public WrappedBlockEnergyContainer energy;

    public int tier;
    public float size = 0f;
    private EnergiserBehaviour energisingBehaviour;
    private InsertOnlyResizableEnergyContainer mut;

    public EnergiserBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int tier) {
        super(type, pos, state);
        this.tier = tier;
        this.energisingBehaviour.tier = tier;
        if (mut == null) {
            getOrCreateNetwork();
            mut = new InsertOnlyResizableEnergyContainer((long) (Math.pow(10, tier) * 1000));
        }
        mut.setMaxCapacity((long) (Math.pow(10, tier) * 1000));
    }

    protected AABB createRenderBoundingBox() {
        var pos = new Vec3(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());
        return new AABB(pos.add(-1, -3, -1), pos.add(1, 1, 1));
    }

    public static EnergiserBlockEntity newTier1(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        return new EnergiserBlockEntity(type, pos, state, 1);
    }
    public static EnergiserBlockEntity newTier2(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        return new EnergiserBlockEntity(type, pos, state, 2);
    }

    public static EnergiserBlockEntity newTier3(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        return new EnergiserBlockEntity(type, pos, state, 3);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        energisingBehaviour = new EnergiserBehaviour(this);
        behaviours.add(energisingBehaviour);
    }

    public long lastCharged = -1;
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate("tooltip.create_new_age.energy_stats")
                .style(ChatFormatting.WHITE).forGoggles(tooltip);

        CreateLang.translate("tooltip.create_new_age.energy_stored")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);
        CreateLang.translate("tooltip.create_new_age.energy_storage", StringFormattingTool.formatLong(energy.getStoredEnergy()), StringFormattingTool.formatLong(energy.getMaxCapacity()))
                .style(ChatFormatting.AQUA).forGoggles(tooltip, 1);

        if (lastCharged != -1) {
            CreateLang.translate("tooltip.create_new_age.energy_usage")
                    .style(ChatFormatting.GRAY)
                    .forGoggles(tooltip);
            CreateLang.translate("tooltip.create_new_age.energy_per_tick", StringFormattingTool.formatLong(lastCharged))
                    .style(ChatFormatting.AQUA).forGoggles(tooltip, 1);
        }

        return super.addToGoggleTooltip(tooltip, isPlayerSneaking);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage() {
        return energy == null ? energy = new WrappedBlockEnergyContainer(this, mut = new InsertOnlyResizableEnergyContainer(0)) : energy;
    }

    @Override
    public float calculateStressApplied() {
        float impact;
        if (this.tier == 1) {
            impact = 4.0f;
        } else if (this.tier == 2) {
            impact = 8.0f;
        } else {
            impact = 32.0f;
        }
        this.lastStressApplied = impact;
        return impact;
    }
}
