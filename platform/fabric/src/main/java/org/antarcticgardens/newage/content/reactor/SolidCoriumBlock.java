package org.antarcticgardens.newage.content.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.antarcticgardens.newage.content.heat.HeatBlockEntity;

public class SolidCoriumBlock extends Block {
    public SolidCoriumBlock(Properties properties) {
        super(properties.randomTicks());
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        NuclearUtil.createRadiation(8, level, pos);
        for (Direction dir : Direction.values()) {
            var be = level.getBlockEntity(pos.relative(dir));
            if (be instanceof HeatBlockEntity heat) {
                heat.addHeat(random.nextFloat() * 100f);
                level.sendBlockUpdated(pos.relative(dir), be.getBlockState(), be.getBlockState(), Block.UPDATE_CLIENTS);
            }
        }
    }
}
