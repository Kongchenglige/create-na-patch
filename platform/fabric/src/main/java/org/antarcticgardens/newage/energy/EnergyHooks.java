package org.antarcticgardens.newage.energy;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.base.PlatformEnergyManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import static earth.terrarium.botarium.common.energy.util.EnergyHooks.moveEnergy;
import static earth.terrarium.botarium.common.energy.util.EnergyHooks.safeGetBlockEnergyManager;

public class EnergyHooks {
    public static <T extends BlockEntity & BotariumEnergyBlock<?>> void distributeEnergyNearby(T energyBlock, long amount) {
        BlockPos blockPos = energyBlock.getBlockPos();
        Level level = energyBlock.getLevel();
        if (level == null) return;
        Direction.stream()
                .map(direction -> Pair.of(direction, level.getBlockEntity(blockPos.relative(direction))))
                .filter(pair -> pair.getSecond() != null)
                .map(pair -> Pair.of(safeGetBlockEnergyManager(pair.getSecond(), pair.getFirst().getOpposite()), pair.getFirst()))
                .filter(pair -> pair.getFirst().isPresent())
                .forEach(pair -> {
                    PlatformEnergyManager externalEnergy = pair.getFirst().get();
                    safeGetBlockEnergyManager(energyBlock, pair.getSecond())
                            .ifPresent(platformEnergyManager -> moveEnergy(platformEnergyManager, externalEnergy, amount == -1 ? energyBlock.getEnergyStorage().getStoredEnergy() : amount));
                });
    }

    public static <T extends BlockEntity & BotariumEnergyBlock<?>> void distributeEnergyNearby(T energyBlock) {
        distributeEnergyNearby(energyBlock, -1);
    }
}
