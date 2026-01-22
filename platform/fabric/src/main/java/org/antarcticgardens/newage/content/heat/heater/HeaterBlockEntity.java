package org.antarcticgardens.newage.content.heat.heater;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.antarcticgardens.newage.config.NewAgeConfig;
import org.antarcticgardens.newage.content.heat.HeatBlockEntity;
import org.antarcticgardens.newage.tools.StringFormattingTool;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HeaterBlockEntity extends BlockEntity implements HeatBlockEntity, IHaveGoggleInformation {
    public HeaterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public float heat = 0;

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        heat = tag.getFloat("heat");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putFloat("heat", heat);
    }


    @Override
    public float getHeat() {
        return heat;
    }

    @Override
    public void addHeat(float amount) {
        heat += amount;
        setChanged();
    }

    @Override
    public void setHeat(float amount) {
        heat = amount;
        setChanged();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Nullable
    @Override
    public float[] getHeatTiers() {
        return new float[] {
                50,
                100,
                400,
                500
        };
    }

    @Override
    public float getTierHeat() {
        BlazeBurnerBlock.HeatLevel strength = getBlockState().getValue(HeaterBlock.STRENGTH);
        double heat = 0;
        Double mult = NewAgeConfig.getCommon().heaterRequiredHeatMultiplier.get();
        switch (strength) {
            case NONE -> {
                heat = 0;
            }
            case SMOULDERING -> {
                heat = 50 * mult;
            }
            case FADING -> {
                heat = 100 * mult;
            }
            case KINDLED -> {
                heat = 400 * mult;
            }
            case SEETHING -> {
                heat = 500 * mult;
            }
        }
        return (float)heat;
    }

    @Override
    public double getHeatTierMultiplier() {
        return NewAgeConfig.getCommon().heaterRequiredHeatMultiplier.get();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        HeatBlockEntity.addToolTips(this, tooltip);
        BlazeBurnerBlock.HeatLevel strength = getBlockState().getValue(HeaterBlock.STRENGTH);
        double heat = 0;

        Double mult = NewAgeConfig.getCommon().heaterRequiredHeatMultiplier.get();

        switch (strength) {

            case NONE -> {
                heat = 0;
            }
            case SMOULDERING -> {
                heat = 50 * mult;
            }
            case FADING -> {
                heat = 100 * mult;
            }
            case KINDLED -> {
                heat = 400 * mult;
            }
            case SEETHING -> {
                heat = 500 * mult;
            }
        }

        Lang.translate("tooltip.create_new_age.releasing")
                .style(ChatFormatting.GRAY).forGoggles(tooltip, 1);
        Lang.translate("tooltip.create_new_age.temperature.ps", StringFormattingTool.formatFloat((float)heat))
                .style(ChatFormatting.AQUA).forGoggles(tooltip, 2);
        return true;
    }

}
