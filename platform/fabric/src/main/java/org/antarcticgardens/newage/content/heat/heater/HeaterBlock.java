package org.antarcticgardens.newage.content.heat.heater;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.antarcticgardens.newage.NewAgeBlockEntityTypes;
import org.antarcticgardens.newage.config.NewAgeConfig;
import org.antarcticgardens.newage.content.heat.HeatBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.antarcticgardens.newage.content.heat.heatpipe.HeatPipeBlock.massPipe;

public class HeaterBlock extends Block implements EntityBlock, IWrenchable {

    public static final EnumProperty<BlazeBurnerBlock.HeatLevel> STRENGTH = BlazeBurnerBlock.HEAT_LEVEL;
    public HeaterBlock(Properties properties) {
        super(properties.lightLevel(state -> state.getValue(STRENGTH).ordinal()));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STRENGTH);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return NewAgeBlockEntityTypes.HEATER.create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        final int on = massPipe;
        massPipe++;
        if (massPipe >= 20) {
            massPipe = 0;
        }
        return (world, blockPos, blockState, sel) -> {
            if ((world.getGameTime() + on) % 20 != 0 || !(sel instanceof HeaterBlockEntity self) || self.getLevel() == null) return;
            HeatBlockEntity.transferAround(self);
            Double mult = NewAgeConfig.getCommon().heaterRequiredHeatMultiplier.get();
            HeatBlockEntity.handleOverheat(self);
            if (self.heat > 500 * mult) {
                self.heat -= (float) (500 * mult);
                level.setBlock(blockPos, state.setValue(STRENGTH, BlazeBurnerBlock.HeatLevel.SEETHING), 3);
            } else if (self.heat > 400 * mult) {
                self.heat -= (float) (400 * mult);
                level.setBlock(blockPos, state.setValue(STRENGTH, BlazeBurnerBlock.HeatLevel.KINDLED), 3);
            } else if (self.heat > 100 * mult) {
                self.heat -= (float) (100 * mult);
                level.setBlock(blockPos, state.setValue(STRENGTH, BlazeBurnerBlock.HeatLevel.FADING), 3);
            } else if (self.heat > 50 * mult) {
                self.heat -= (float) (50 * mult);
                level.setBlock(blockPos, state.setValue(STRENGTH, BlazeBurnerBlock.HeatLevel.SMOULDERING), 3);
            } else {
                level.setBlock(blockPos, state.setValue(STRENGTH, BlazeBurnerBlock.HeatLevel.NONE), 3);
            }
            self.setChanged();
            HeatBlockEntity.trySync(self);
        };
    }



}
