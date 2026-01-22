package org.antarcticgardens.newage.content.generation.carbonbrushes;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.utility.Lang;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.antarcticgardens.newage.config.NewAgeConfig;
import org.antarcticgardens.newage.content.generation.generatorcoil.GeneratorCoilBlock;
import org.antarcticgardens.newage.content.generation.generatorcoil.GeneratorCoilBlockEntity;
import org.antarcticgardens.newage.energy.EnergyHooks;
import org.antarcticgardens.newage.energy.ExtractOnlyResizableEnergyContainer;
import org.antarcticgardens.newage.tools.StringFormattingTool;

import java.util.List;

public class CarbonBrushesBlockEntity extends KineticBlockEntity implements BotariumEnergyBlock<ExtractOnlyResizableEnergyContainer>, IHaveGoggleInformation {
    private final ExtractOnlyResizableEnergyContainer energyContainer;

    private int lastOutput = 0;

    public CarbonBrushesBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        energyContainer = new ExtractOnlyResizableEnergyContainer(0);

        setLazyTickRate(20);
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        compound.putInt("lastOutput", lastOutput);
        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        lastOutput = compound.getInt("lastOutput");
        super.read(compound, clientPacket);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        Lang.translate("tooltip.create_new_age.energy_stats")
                .style(ChatFormatting.WHITE).forGoggles(tooltip);

        Lang.translate("tooltip.create_new_age.energy_output")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);

        Lang.translate("tooltip.create_new_age.energy_per_tick", StringFormattingTool.formatLong(lastOutput))
                .style(ChatFormatting.AQUA)
                .forGoggles(tooltip, 1);

        return true;
    }

    private int syncOut = 0;

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide) return;
        Direction facing = getBlockState().getValue(DirectionalKineticBlock.FACING);

        energyContainer.setMaxCapacity(lastOutput * 20L);

        int coilsLeft = NewAgeConfig.getCommon().maxCoils.get();
        lastOutput = 0;
        coilsLeft = processCoil(worldPosition, facing, coilsLeft);
        processCoil(worldPosition, facing.getOpposite(), coilsLeft);

        EnergyHooks.distributeEnergyNearby(this);
    }

    @Override
    public void lazyTick() {
        if (level == null || level.isClientSide) return;
        if (syncOut > 0) {
            syncOut = 0;
            setChanged();
            sendData();
        }
    }

    private int processCoil(BlockPos pos, Direction dir, int left) {
        if (left <= 0)
            return 0;

        pos = pos.relative(dir);

        if (level.getBlockEntity(pos) instanceof GeneratorCoilBlockEntity coil && coil.getBlockState().getValue(GeneratorCoilBlock.AXIS).test(dir)) {
            int energy = coil.takeGeneratedEnergy();
            lastOutput += energy;
            syncOut += energy;
            energyContainer.internalInsert(energy, false);
            return processCoil(pos, dir, left-1);
        }
        return left;
    }

    @Override
    public ExtractOnlyResizableEnergyContainer getEnergyStorage() {
        return energyContainer;
    }
}
