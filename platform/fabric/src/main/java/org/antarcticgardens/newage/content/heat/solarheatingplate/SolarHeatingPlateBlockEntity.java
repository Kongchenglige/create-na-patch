package org.antarcticgardens.newage.content.heat.solarheatingplate;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.antarcticgardens.newage.config.NewAgeConfig;
import org.antarcticgardens.newage.content.heat.HeatBlockEntity;
import org.antarcticgardens.newage.tools.StringFormattingTool;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SolarHeatingPlateBlockEntity extends BlockEntity implements HeatBlockEntity, IHaveGoggleInformation {
    private final int energyPerSecond;
    private float last;

    public SolarHeatingPlateBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, int energyPerSecond) {
        super(type, pos, blockState);
        this.energyPerSecond = energyPerSecond;
    }

    public static SolarHeatingPlateBlockEntity createBasic(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        return new SolarHeatingPlateBlockEntity(type, pos, blockState, 20);
    }

    public static SolarHeatingPlateBlockEntity createAdvanced(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        return new SolarHeatingPlateBlockEntity(type, pos, blockState, 60);
    }

    public float heat = 0;

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        heat = tag.getFloat("heat");
        last = tag.getFloat("last");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putFloat("heat", heat);
        tag.putFloat("last", last);
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

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        HeatBlockEntity.addToolTips(this, tooltip);

        Lang.translate("tooltip.create_new_age.generating")
                .style(ChatFormatting.GRAY).forGoggles(tooltip, 1);
        Lang.translate("tooltip.create_new_age.temperature.ps", StringFormattingTool.formatFloat(last))
                .style(ChatFormatting.AQUA).forGoggles(tooltip, 2);

        return true;
    }


    public void tick(BlockPos blockPos, Level world, BlockState blockState) {
        var common = NewAgeConfig.getCommon();
        double generationMultiplier = common.solarPanelHeatMultiplier.get();

        int dark = 0;
        if (world.isClientSide()) {
            // update sky could also work but this feels more right.
            double d = 1.0 - (double)(world.getRainLevel(1.0F) * 5.0F) / 16.0;
            double e = 1.0 - (double)(world.getThunderLevel(1.0F) * 5.0F) / 16.0;
            double f = 0.5 + 2.0 * Mth.clamp(Mth.cos(world.getTimeOfDay(1.0F) * 6.2831855F), -0.25, 0.25);
            dark = (int)((1.0 - f * d * e) * 11.0);
        } else {
            dark = world.getSkyDarken();
        }
        dark *= 2;
        HeatBlockEntity.transferAround(this);
        HeatBlockEntity.handleOverheat(this);

        float light = world.getBrightness(LightLayer.SKY, blockPos.above()) - dark;
        last = (float) Math.max((light/15f)*energyPerSecond*generationMultiplier - Math.max(0, heat - (20 * energyPerSecond*generationMultiplier)), 0);
        addHeat(last);
        HeatBlockEntity.trySync(this);
    }
}
