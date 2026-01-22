package org.antarcticgardens.newage.content.heat.heatpump;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.antarcticgardens.newage.content.heat.HeatBlockEntity;
import org.antarcticgardens.newage.tools.StringFormattingTool;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HeatPumpBlockEntity extends BlockEntity implements HeatBlockEntity, IHaveGoggleInformation {
    public HeatPumpBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public float heat = 0;

    public float lastPump = 0;

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        heat = tag.getFloat("heat");
        lastPump = tag.getFloat("last");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putFloat("heat", heat);
        tag.putFloat("last", lastPump);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        Lang.translate("tooltip.create_new_age.pump").style(ChatFormatting.GRAY).forGoggles(tooltip, 1);
        Lang.translate("tooltip.create_new_age.temperature.ps", StringFormattingTool.formatFloat(lastPump)).style(ChatFormatting.AQUA).forGoggles(tooltip, 2);
        return true;
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

    @Override
    public boolean canAdd(Direction from) {
        if (getLevel() == null)
            return false;
        return from != getLevel().getBlockState(getBlockPos()).getValue(HeatPumpBlock.FACING).getOpposite();
    }
}
