package org.antarcticgardens.newage.content.electricity.network;

import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.level.block.entity.BlockEntity;
import team.reborn.energy.api.EnergyStorage;

@SuppressWarnings("UnstableApiUsage")
public record EnergyStorageWrapper(BlockEntity entity, EnergyStorage storage) {
    public long insert(long maxAmount, boolean simulate) {
        try (Transaction txn = Transaction.openNested(Transaction.getCurrentUnsafe())) {
            long inserted = storage.insert(maxAmount, txn);

            if (simulate)
                txn.abort();
            else
                txn.commit();

            return inserted;
        }
    }
    
    public long extract(long maxAmount, boolean simulate) {
        try (Transaction txn = Transaction.openNested(Transaction.getCurrentUnsafe())) {
            long inserted = storage.extract(maxAmount, txn);

            if (simulate)
                txn.abort();
            else
                txn.commit();

            return inserted;
        }
    }
}
