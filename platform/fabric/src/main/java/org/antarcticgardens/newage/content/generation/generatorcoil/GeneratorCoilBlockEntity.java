package org.antarcticgardens.newage.content.generation.generatorcoil;

import com.google.common.collect.Lists;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.antarcticgardens.newage.CreateNewAge;
import org.antarcticgardens.newage.NewAgeTags;
import org.antarcticgardens.newage.config.NewAgeConfig;
import org.antarcticgardens.newage.content.generation.magnets.IMagneticBlock;
import org.antarcticgardens.newage.tools.RelativeBlockPos;
import org.antarcticgardens.newage.tools.StringFormattingTool;

import java.util.List;

public class GeneratorCoilBlockEntity extends KineticBlockEntity {
    public final List<BlockPos> magnetPositions;

    private float lastStress = 0.0f;
    private int generatedEnergy = 0;

    public GeneratorCoilBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);

        RelativeBlockPos relativePosition = new RelativeBlockPos(worldPosition,
                state.getValue(RotatedPillarKineticBlock.AXIS));

        magnetPositions = Lists.newArrayList(
                relativePosition.up(2).right(1).getPos(),
                relativePosition.up(2).getPos(),
                relativePosition.up(2).left(1).getPos(),

                relativePosition.left(2).up(1).getPos(),
                relativePosition.left(2).getPos(),
                relativePosition.left(2).down(1).getPos(),

                relativePosition.down(2).left(1).getPos(),
                relativePosition.down(2).getPos(),
                relativePosition.down(2).right(1).getPos(),

                relativePosition.right(2).down(1).getPos(),
                relativePosition.right(2).getPos(),
                relativePosition.right(2).up(1).getPos()
        );

        setLazyTickRate(20);
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return new AABB(worldPosition).inflate(1);
    }

    private float plainStress;

    @Override
    public float calculateStressApplied() {
        plainStress = super.calculateStressApplied();
        float stress = plainStress;

        for (BlockPos pos : magnetPositions) {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof IMagneticBlock magneticBlock)
                stress += magneticBlock.getStrength();
            else if (state.is(NewAgeTags.CUSTOM_MAGNET_TAG)) {
                for (TagKey<Block> blockTagKey : state.getTags().toList()) {
                    if (blockTagKey.location().getNamespace().equals(CreateNewAge.MOD_ID)) {
                        String path = blockTagKey.location().getPath();
                        if (path.startsWith("magnets/strength_")) {
                            try {
                                stress += Integer.parseInt(path.substring(17));
                                break;
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                CreateNewAge.LOGGER.error("BAD TAG " + blockTagKey + " on item " + state.getBlock());
                            }
                        }
                    }
                }
            }
        }

        this.lastStressApplied = stress;

        return stress;
    }

    @Override
    public void tick() {
        super.tick();
        generatedEnergy = (int) ((lastStressApplied - plainStress) * Math.abs(this.getSpeed()) * NewAgeConfig.getCommon().suToEnergy.get());
    }

    @Override
    public void lazyTick() {
        if (level == null || level.isClientSide)
            return;
        float stress = calculateStressApplied();
        var network = getOrCreateNetwork();

        if (network != null && lastStress != stress) {
            network.updateStressFor(this, stress);
            network.updateStress();
            sendData();

            lastStress = stress;
        }
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        Lang.translate("tooltip.create_new_age.efficiency").style(ChatFormatting.GRAY).forGoggles(tooltip, 1);
        Lang.translate("tooltip.create_new_age.percent", StringFormattingTool.formatPercentFloat(
                (lastStressApplied-plainStress)/lastStressApplied
        )).style(ChatFormatting.AQUA).forGoggles(tooltip, 2);
        return super.addToGoggleTooltip(tooltip, isPlayerSneaking);
    }

    public int takeGeneratedEnergy() {
        int energy = generatedEnergy;
        generatedEnergy = 0;
        return energy;
    }
}
