package org.antarcticgardens.newage.content.heat.heater;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public class HeaterPonder {
    public static void ponder(SceneBuilder scene, SceneBuildingUtil util) {
        scene.configureBasePlate(0, 0, 5);
        scene.title("heater", "Using Heater");
        scene.showBasePlate();

        scene.world.showSection(util.select.fromTo(0, 0, 0, 5, 0, 5), Direction.UP);

        scene.idle(10);

        scene.world.showSection(util.select.fromTo(1, 1, 1, 5, 1, 5), Direction.DOWN);

        scene.world.modifyBlock(util.grid.at(2, 1, 1),
                blockState -> blockState.setValue(HeaterBlock.STRENGTH, BlazeBurnerBlock.HeatLevel.NONE),
                false);

        scene.idle(10);


        scene.idle(10);

        scene.overlay.showText(100).placeNearTarget()
                .text("").pointAt(new Vec3(2.6, 1.5, 2.4));

        scene.idle(110);

        scene.world.showSection(util.select.position(2, 1, 0), Direction.UP);

        scene.idle(20);

        scene.overlay.showText(90).placeNearTarget()
                .text("").pointAt(new Vec3(2.6, 1.5, 2.4));

        scene.idle(30);

        scene.world.modifyBlock(util.grid.at(2, 1, 1),
                blockState -> blockState.setValue(HeaterBlock.STRENGTH, BlazeBurnerBlock.HeatLevel.SMOULDERING),
                false);

        scene.idle(50);

        scene.world.modifyBlock(util.grid.at(2, 1, 1),
                blockState -> blockState.setValue(HeaterBlock.STRENGTH, BlazeBurnerBlock.HeatLevel.KINDLED),
                false);

        scene.idle(100);

        scene.world.modifyBlock(util.grid.at(2, 1, 1),
                blockState -> blockState.setValue(HeaterBlock.STRENGTH, BlazeBurnerBlock.HeatLevel.SEETHING),
                false);
    }
}
