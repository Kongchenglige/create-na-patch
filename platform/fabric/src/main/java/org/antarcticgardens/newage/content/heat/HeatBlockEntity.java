package org.antarcticgardens.newage.content.heat;

import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.antarcticgardens.newage.config.NewAgeConfig;
import org.antarcticgardens.newage.tools.StringFormattingTool;

import javax.annotation.Nullable;
import java.util.List;

public interface HeatBlockEntity {
    float getHeat();
    void addHeat(float amount);

    void setHeat(float amount);

    /**
     * @param from the side of block relative to the block accessing
     */
    default boolean canConnect(Direction from) {
        return true;
    }

    /**
     * @param from the side of block relative to the block accessing
     */
    default boolean canAdd(Direction from) {
        return canConnect(from);
    }
    default float maxHeat() { return 10_000; }

    default float getTierHeat() {
        return getHeat();
    }

    @Nullable
    default float[] getHeatTiers() {
        return new float[] {};
    }

    default double getHeatTierMultiplier() {
        return 1.0f;
    }

    static <T extends  BlockEntity & HeatBlockEntity> void addToolTips(T self, List<Component> tooltip) {
        LangBuilder builder = Lang.translate("tooltip.create_new_age.temperature", StringFormattingTool.formatFloat(self.getHeat()));
        float max = self.maxHeat() * (float)(double)NewAgeConfig.getCommon().overheatingMultiplier.get();
        if (max < 0) {
            builder.style(ChatFormatting.AQUA);
        } else if (self.getHeat() >= max) {
            builder.style(ChatFormatting.DARK_RED);
        } else if (self.getHeat() >= max * 0.9) {
            builder.style(ChatFormatting.RED);
        } else if (self.getHeat() >= max * 0.75) {
            builder.style(ChatFormatting.GOLD);
        } else if (self.getHeat() >= max * 0.65) {
            builder.style(ChatFormatting.YELLOW);
        } else {
            builder.style(ChatFormatting.AQUA);
        }


        builder.add(Lang.text(" / ")
                .add(Lang.translate("tooltip.create_new_age.temperature", max > 0 ? StringFormattingTool.formatFloat(max) : "∞")).style(ChatFormatting.DARK_GRAY)
        );

        builder.forGoggles(tooltip, 1);

        float[] tiers = self.getHeatTiers();

        if (tiers == null)
            return;

        float mult = (float) self.getHeatTierMultiplier();
        float tierHeat = self.getTierHeat();
        for (int i = 0 ; i < tiers.length ; i++) {
            float tis = tiers[i] * mult;
            float next = Float.MAX_VALUE;
            if (tiers.length > i + 1) {
                next = tiers[i + 1] * mult;
            }
            if (tis <= tierHeat && next > tierHeat) {
                builder = Lang.text("> ").add(Lang.translate("tooltip.create_new_age.temperature", StringFormattingTool.formatFloat(tis)));
                builder.style(ChatFormatting.GRAY);
                builder.forGoggles(tooltip, 0);
            } else {
                builder = Lang.text("").add(Lang.translate("tooltip.create_new_age.temperature", StringFormattingTool.formatFloat(tis)));
                builder.style(ChatFormatting.DARK_GRAY);
                builder.forGoggles(tooltip, 2);
            }
        }

    }

    static <T extends  BlockEntity & HeatBlockEntity> void handleOverheat(T self, Runnable onOverHeat) {
        if (self.getLevel() == null) {
            return;
        }

        double multiplier = NewAgeConfig.getCommon().overheatingMultiplier.get();
        if (multiplier > 0 && self.getHeat() > self.maxHeat() * NewAgeConfig.getCommon().overheatingMultiplier.get()) {
            onOverHeat.run();
        }
    }

    static <T extends  BlockEntity & HeatBlockEntity> void handleOverheat(T self) {
        handleOverheat(self, () -> self.getLevel().setBlock(self.getBlockPos(), Blocks.LAVA.defaultBlockState(), 3));
    }

    static <T extends  BlockEntity & HeatBlockEntity> void transferAround(T self) {
        if (self.getLevel() == null) {
            return;
        }
        float totalToAverage = self.getHeat();
        int totalBlocks = 1;
        HeatBlockEntity[] setters = new HeatBlockEntity[6];
        for (int i = 0 ; i < 6 ; i++) {
            Direction value = Direction.values()[i];
            BlockEntity entity = self.getLevel().getBlockEntity(self.getBlockPos().relative(value));
            if (entity instanceof HeatBlockEntity hbe && hbe.canAdd(value)) {
                setters[i] = hbe;
                totalToAverage += hbe.getHeat();
                totalBlocks++;
            }
        }
        average(self, totalToAverage, totalBlocks, setters);

        self.setChanged();
    }

    static <T extends BlockEntity & HeatBlockEntity> void trySync(T self) {
        if (self.getLevel() instanceof ServerLevel level && self.getLevel().getGameTime() % 160 == 0) {
            BlockState state = self.getBlockState();
            level.sendBlockUpdated(self.getBlockPos(), state, state, 3);
        }
    }

    static <T extends BlockEntity & HeatBlockEntity> void average(T self, float totalToAverage, int totalBlocks, HeatBlockEntity[] setters) {
        float setAmount = totalToAverage / totalBlocks;
        self.setHeat(setAmount);
        int i = 0;
        for (HeatBlockEntity hbe : setters) {
            if (hbe != null) {
                hbe.setHeat(setAmount);
            }
            i++;
        }
    }

}
