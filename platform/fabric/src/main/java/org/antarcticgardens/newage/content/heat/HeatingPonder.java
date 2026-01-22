package org.antarcticgardens.newage.content.heat;

import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.antarcticgardens.newage.NewAgeBlocks;

public class HeatingPonder {

    public static void ponder(SceneBuilder scene, SceneBuildingUtil util) {
        scene.configureBasePlate(0, 0, 5);
        scene.title("heating", "Using Heating");
        scene.showBasePlate();

        scene.world.showSection(util.select.fromTo(0, 0, 0, 5, 0, 5), Direction.UP);

        scene.idle(10);
        scene.world.showSection(util.select.position(1, 1, 1), Direction.DOWN);
        scene.overlay.showText(80).placeNearTarget()
                .text("").pointAt(new Vec3(1.5, 2, 1.5));

        scene.idle(90);
        scene.addKeyframe();
        scene.rotateCameraY(45);
        scene.idle(20);

        scene.world.showSection(util.select.position(2, 1, 1), Direction.DOWN);
        scene.world.modifyBlock(util.grid.at(2, 1, 1),
                blockState -> blockState.setValue(BlockStateProperties.SOUTH, false),
                false);
        scene.idle(10);
        scene.world.showSection(util.select.position(3, 1, 1), Direction.DOWN);
        scene.world.modifyBlock(util.grid.at(3, 1, 1),
                blockState -> blockState.setValue(BlockStateProperties.UP, false),
                false);
        scene.idle(10);

        scene.overlay.showText(80).placeNearTarget()
                .text("").pointAt(new Vec3(3.0, 1.8, 1.5));


        scene.idle(90);

        showTripleText(scene);

        scene.idle(20);
        showTripleText(scene);

        scene.idle(20);
        showTripleText(scene);

        scene.idle(40);

        scene.addKeyframe();
        scene.idle(15);

        scene.world.modifyBlock(util.grid.at(3, 1, 1),
                blockState -> blockState.setValue(BlockStateProperties.UP, true),
                false);
        scene.world.showSection(util.select.position(3, 2, 1), Direction.DOWN);
        scene.rotateCameraY(35);
        scene.idleSeconds(1);

        scene.overlay.showText(60).placeNearTarget()
                .text("").pointAt(new Vec3(3.5, 2.5, 1.5));
        scene.world.setKineticSpeed(util.select.position(3, 2, 1), 8);
        scene.effects.rotationSpeedIndicator(util.grid.at(3, 2, 1));

        scene.idleSeconds(4);

        scene.world.setBlock(util.grid.at(1, 1, 1), NewAgeBlocks.ADVANCED_SOLAR_HEATING_PLATE.getDefaultState(), true);

        scene.idle(20);

        scene.overlay.showText(60).placeNearTarget()
                .text("").pointAt(new Vec3(3.5, 2.5, 1.5));
        scene.world.setKineticSpeed(util.select.position(3, 2, 1), 16);
        scene.effects.rotationSpeedIndicator(util.grid.at(3, 2, 1));

        scene.idleSeconds(4);

        scene.addKeyframe();
        scene.idle(15);

        scene.world.showSection(util.select.fromTo(2, 1, 2,2, 2, 3), Direction.UP);
        scene.world.modifyBlock(util.grid.at(2, 1, 1),
                blockState -> blockState.setValue(BlockStateProperties.SOUTH, true),
                false);

        scene.idleSeconds(1);

        scene.rotateCameraY(90);

        scene.world.setKineticSpeed(util.select.position(3, 2, 1), 8);
        scene.effects.rotationSpeedIndicator(util.grid.at(3, 2, 1));

        scene.world.setKineticSpeed(util.select.position(2, 2, 3), 16);
        scene.effects.rotationSpeedIndicator(util.grid.at(2, 2, 3));

        scene.idle(15);

        scene.overlay.showText(60).placeNearTarget()
                .text("").pointAt(new Vec3(2.5, 2.5, 3.5));

        scene.idleSeconds(4);

        scene.world.setBlock(util.grid.at(2, 2, 3), Blocks.AIR.defaultBlockState(), true);
        scene.world.modifyBlock(util.grid.at(2, 1, 3),
                blockState -> blockState.setValue(BlockStateProperties.UP, false),
                false);

        scene.idle(10);

        scene.overlay.showText(60).placeNearTarget()
                .text("").pointAt(new Vec3(2.5, 1.5, 3.5));

        scene.idle(40);
        scene.world.setBlock(util.grid.at(2, 1, 3), Blocks.LAVA.defaultBlockState(), true);
        scene.idle(60);
    }

    private static void showTripleText(SceneBuilder scene) {
        scene.overlay.showText(20).placeNearTarget()
                .text("").pointAt(new Vec3(1.5, 2, 1.5));

        scene.overlay.showText(20).placeNearTarget()
                .text("").pointAt(new Vec3(2.5, 1.8, 1.5));

        scene.overlay.showText(20).placeNearTarget()
                .text("").pointAt(new Vec3(3.5, 1.8, 1.5));
    }

}
