package org.antarcticgardens.newage.content.generation;

import com.simibubi.create.content.kinetics.gauge.StressGaugeBlockEntity;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.antarcticgardens.newage.NewAgeBlocks;
import org.antarcticgardens.newage.content.electricity.connector.ElectricalConnectorBlockEntity;
import org.antarcticgardens.newage.content.electricity.wire.WireType;

public class GenerationPonder {


    public static void ponder(SceneBuilder scene, SceneBuildingUtil util) {
        scene.configureBasePlate(0, 0, 9);
        scene.title("generation", "Electricity Generation");
        scene.scaleSceneView(0.7f);
        scene.addKeyframe();

        scene.idle(20);

        scene.world.showSection(util.select.layer(0), Direction.UP);

        scene.idle(10);

        scene.world.showSection(util.select.position(5, 1, 7), Direction.DOWN);
        scene.world.showSection(util.select.position(5, 2, 7), Direction.DOWN);

        scene.idle(5);

        scene.world.showSection(util.select.position(3, 1, 7), Direction.DOWN);
        scene.world.showSection(util.select.position(3, 2, 7), Direction.DOWN);

        scene.idle(5);

        scene.world.showSection(util.select.position(3, 1, 3), Direction.DOWN);
        scene.world.showSection(util.select.position(3, 2, 3), Direction.DOWN);

        scene.idle(5);

        scene.world.showSection(util.select.position(5, 1, 3), Direction.DOWN);
        scene.world.showSection(util.select.position(5, 2, 3), Direction.DOWN);

        scene.idle(10);

        scene.world.showSection(util.select.position(5, 3, 7), Direction.DOWN);
        scene.world.showSection(util.select.position(4, 3, 7), Direction.DOWN);
        scene.world.showSection(util.select.position(3, 3, 7), Direction.DOWN);

        scene.idle(10);

        scene.world.showSection(util.select.position(5, 3, 3), Direction.DOWN);
        scene.world.showSection(util.select.position(4, 3, 3), Direction.DOWN);
        scene.world.showSection(util.select.position(3, 3, 3), Direction.DOWN);

        scene.idle(20);

        scene.world.setKineticSpeed(util.select.fromTo(4, 4, 3, 4, 3, 8), 32);

        scene.idle(35);

        scene.world.showSection(util.select.fromTo(4, 3, 6, 4, 3, 4), Direction.DOWN);

        scene.idle(15);

        scene.world.showSection(util.select.fromTo(5, 1, 6, 3, 1, 5), Direction.EAST);

        scene.idle(15);

        scene.world.showSection(util.select.fromTo(2, 2, 5, 2, 4, 6), Direction.EAST);

        scene.world.showSection(util.select.fromTo(6, 2, 5, 6, 4, 6), Direction.WEST);

        scene.idle(15);

        scene.world.showSection(util.select.fromTo(5, 5, 6, 3, 5, 5), Direction.DOWN);

        scene.idle(20);

        scene.idle(15);

        scene.overlay.showText(100)
                .pointAt(new Vec3(4.5, 5.5, 5.5))
                .text("If you are reading this please disable ponder debug mode.");

        scene.idle(110);

        scene.world.showSection(util.select.position(4, 1, 1), Direction.DOWN);

        scene.idle(5);

        scene.world.showSection(util.select.position(5, 1, 1), Direction.DOWN);

        scene.idle(5);

        scene.world.showSection(util.select.position(6, 1, 1), Direction.DOWN);

        scene.idle(5);

        scene.world.showSection(util.select.position(4, 2, 4), Direction.SOUTH);

        scene.idle(5);

        scene.world.showSection(util.select.position(4, 2, 1), Direction.DOWN);

        scene.idle(10);

        scene.overlay.showText(100)
                .pointAt(new Vec3(4.5, 3.5, 4.5))
                .text("");
        scene.overlay.chaseBoundingBoxOutline(PonderPalette.BLUE, new Object(), new AABB(4, 3, 4, 5, 4, 5), 100);

        scene.idle(110);

        scene.world.modifyBlockEntity(new BlockPos(4, 2, 4), ElectricalConnectorBlockEntity.class, en -> {
            en.connect((ElectricalConnectorBlockEntity) en.getLevel().getBlockEntity(new BlockPos(4, 2, 1)), WireType.IRON);
        });

        scene.idle(2);

        scene.world.setKineticSpeed(util.select.fromTo(4,1, 1, 6, 1, 1), 16);

        scene.idle(20);

        scene.addKeyframe();

        scene.idle(20);

        scene.overlay.showText(140)
                .pointAt(new Vec3(4.5, 3.5, 4.5))
                .text("");

        scene.idle(150);

        scene.overlay.showText(20)
                .pointAt(new Vec3(4.5, 3.5, 5.5))
                .text("");

        scene.idle(20);

        replaceCoil(scene, util, NewAgeBlocks.REDSTONE_MAGNET.getDefaultState());
        scene.overlay.showText(20)
                .pointAt(new Vec3(4.5, 3.5, 5.5))
                .text("");


        scene.idle(30);

        scene.rotateCameraY(180);

        scene.idle(20);

        scene.world.showSection(util.select.position(4, 3, 8), Direction.NORTH);

        scene.world.modifyBlockEntityNBT(util.select.position(4, 3, 8), StressGaugeBlockEntity.class,
                nbt -> nbt.putFloat("Value", .25f));

        scene.idle(20);

        scene.overlay.showText(150)
                .pointAt(new Vec3(4.5, 3.5, 8.5))
                .text("");

        scene.idle(160);

        replaceCoil(scene, util, NewAgeBlocks.LAYERED_MAGNET.getDefaultState());

        scene.world.modifyBlockEntityNBT(util.select.position(4, 3, 8), StressGaugeBlockEntity.class,
                nbt -> nbt.putFloat("Value", .5f));

        scene.idle(20);

        replaceCoil(scene, util, NewAgeBlocks.FLUXUATED_MAGNETITE.getDefaultState());

        scene.world.modifyBlockEntityNBT(util.select.position(4, 3, 8), StressGaugeBlockEntity.class,
                nbt -> nbt.putFloat("Value", 1.0f));

        scene.idle(20);

        scene.overlay.showText(100)
                .pointAt(new Vec3(4.5, 4.5, 4.5))
                .text("If you are reading this please disable ponder debug mode.");

        scene.idle(100);

        scene.markAsFinished();

    }

    private static final int WAIT_TIME = 1;
    public static void replaceCoil(SceneBuilder scene, SceneBuildingUtil util, BlockState state) {
        for (int i = 0 ; i < 3 ; i++) {
            scene.world.setBlock(new BlockPos(3+i, 1, 6), state, true);
            scene.idle(WAIT_TIME);
        }
        for (int i = 0 ; i < 3 ; i++) {
            scene.world.setBlock(new BlockPos(6, 2+i, 6), state, true);
            scene.idle(WAIT_TIME);
        }
        for (int i = 0 ; i < 3 ; i++) {
            scene.world.setBlock(new BlockPos(5-i, 5, 6), state, true);
            scene.idle(WAIT_TIME);
        }
        for (int i = 0 ; i < 3 ; i++) {
            scene.world.setBlock(new BlockPos(2, 4-i, 6), state, true);
            scene.idle(WAIT_TIME);
        }

        for (int i = 0 ; i < 3 ; i++) {
            scene.world.setBlock(new BlockPos(3+i, 1, 5), state, true);
            scene.idle(WAIT_TIME);
        }
        for (int i = 0 ; i < 3 ; i++) {
            scene.world.setBlock(new BlockPos(6, 2+i, 5), state, true);
            scene.idle(WAIT_TIME);
        }
        for (int i = 0 ; i < 3 ; i++) {
            scene.world.setBlock(new BlockPos(5-i, 5, 5), state, true);
            scene.idle(WAIT_TIME);
        }
        for (int i = 0 ; i < 3 ; i++) {
            scene.world.setBlock(new BlockPos(2, 4-i, 5), state, true);
            scene.idle(WAIT_TIME);
        }

    }

}
