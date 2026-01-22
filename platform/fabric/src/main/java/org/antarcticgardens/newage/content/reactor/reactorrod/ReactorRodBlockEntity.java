package org.antarcticgardens.newage.content.reactor.reactorrod;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.antarcticgardens.newage.NewAgeBlocks;
import org.antarcticgardens.newage.config.NewAgeConfig;
import org.antarcticgardens.newage.content.heat.HeatBlockEntity;
import org.antarcticgardens.newage.content.reactor.NuclearUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReactorRodBlockEntity extends BlockEntity implements HeatBlockEntity, IHaveGoggleInformation {
    public ReactorRodBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        working = blockState.getValue(ReactorRodBlock.ACTIVE);
    }

    int twoSeconds = 0;
    private boolean working;
    public void tick(BlockPos pos, Level world, BlockState state) {
        var common = NewAgeConfig.getCommon();
        double multiplier = common.overheatingMultiplier.get();
        if (multiplier > 0 && this.heat > 16000*multiplier) {
            heat-=common.nuclearReactorRodHeatLoss.get();
            setChanged();
            HeatBlockEntity.handleOverheat(this, () -> world.setBlock(pos, NewAgeBlocks.CORIUM.getDefaultState(), 3));
        }
        twoSeconds++;
        if (twoSeconds > 40) {
            transferAroundRodOnly(this);
            working = state.getValue(ReactorRodBlock.ACTIVE);

            twoSeconds = 0;
            if (working) {
                NuclearUtil.createRadiation(8, world, pos);
            }
        }
        if (fuel > 0) {
            fuel--;
            if (!working) {
                world.setBlock(pos, state.setValue(ReactorRodBlock.ACTIVE, true), 3);
                working = true;
            }
            heat+=common.nuclearReactorRodHeat.get();
            setChanged();
        } else {
            if (working) {
                world.setBlock(pos, state.setValue(ReactorRodBlock.ACTIVE, false), 3);
                working = false;
                setChanged();
            }
        }
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        HeatBlockEntity.addToolTips(this, tooltip);
        return true;
    }

    static <T extends  BlockEntity & HeatBlockEntity> void transferAroundRodOnly(T self) {
        if (self.getLevel() == null) {
            return;
        }
        float totalToAverage = self.getHeat();
        int totalBlocks = 1;
        HeatBlockEntity[] setters = new HeatBlockEntity[6];
        for (int i = 0 ; i < 6 ; i++) {
            Direction value = Direction.values()[i];
            BlockEntity entity = self.getLevel().getBlockEntity(self.getBlockPos().relative(value));
            if (entity instanceof ReactorRodBlockEntity hbe && hbe.canAdd(value)) {
                setters[i] = hbe;
                totalToAverage += hbe.getHeat();
                totalBlocks++;
            }
        }
        HeatBlockEntity.average(self, totalToAverage, totalBlocks, setters);
    }

    @Override
    public float maxHeat() {
        return 26000;
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

    public float heat = 0;
    public int fuel = 0;

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        heat = tag.getFloat("heat");
        fuel = tag.getInt("fuel");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putFloat("heat", heat);
        tag.putInt("fuel", fuel);
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

}
