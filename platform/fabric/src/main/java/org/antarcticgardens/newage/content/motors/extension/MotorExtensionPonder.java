package org.antarcticgardens.newage.content.motors.extension;

import com.simibubi.create.content.kinetics.gauge.StressGaugeBlockEntity;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.antarcticgardens.newage.content.electricity.connector.ElectricalConnectorBlockEntity;
import org.antarcticgardens.newage.content.electricity.wire.WireType;

public class MotorExtensionPonder {
    public static void motorExtension(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("motor_extension", "Motor Extensions");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.addKeyframe();
        
        BlockPos stressometer = util.grid.at(1, 1, 2);
        
        scene.world.modifyBlockEntityNBT(util.select.position(stressometer), StressGaugeBlockEntity.class,
                nbt -> nbt.putFloat("Value", 1.0f));

        scene.world.modifyBlockEntity(util.grid.at(2, 2, 2), ElectricalConnectorBlockEntity.class, be ->
                be.connect((ElectricalConnectorBlockEntity) be.getLevel().getBlockEntity(util.grid.at(4, 2, 2)), WireType.GOLD));
        
        scene.idle(10);
        scene.world.showSection(util.select.position(2, 1, 2), Direction.DOWN);
        scene.idle(5);
        scene.world.showSection(util.select.position(stressometer), Direction.DOWN);

        redstoneIdle(scene, stressometer, 1);
        scene.world.showSection(util.select.position(2, 2, 2), Direction.DOWN);
        scene.world.showSection(util.select.position(4, 2, 2), Direction.DOWN);
        redstoneIdle(scene, stressometer, 1);
        
        redstoneIdle(scene, stressometer, 4);
        
        scene.overlay.showText(60)
                .pointAt(stressometer.getCenter())
                .placeNearTarget()
                .text("Tired of your motors being overstressed? Use a motor extension!");

        redstoneIdle(scene, stressometer, 14);
        
        scene.world.showSection(util.select.position(3, 1, 2), Direction.SOUTH);

        scene.overlay.showText(60)
                .pointAt(util.grid.at(3, 1, 2).getCenter())
                .placeNearTarget()
                .text("Extensions allow you to configure motor stress capacity multiplier");

        redstoneIdle(scene, stressometer, 14);
        
        Vec3 blockSurface = util.vector.blockSurface(util.grid.at(3, 1, 2), Direction.NORTH)
                .add(-1 / 16f, 0, 3 / 16f);
        scene.overlay.showFilterSlotInput(blockSurface, Direction.NORTH, 80);
        scene.overlay.showControls(new InputWindowElement(blockSurface, Pointing.DOWN).rightClick(), 60);

        redstoneIdle(scene, stressometer, 14);

        scene.world.modifyBlockEntityNBT(util.select.position(stressometer), StressGaugeBlockEntity.class,
                nbt -> nbt.putFloat("Value", 0.5f));
        
        scene.world.setKineticSpeed(util.select.fromTo(1, 1, 2, 3, 1, 2), 64);

        scene.idle(30);
        
        scene.overlay.showText(60)
                .pointAt(util.grid.at(3, 1, 2).getCenter())
                .placeNearTarget()
                .text("Note that energy consumption is proportional to stress capacity");
        
        scene.idle(60);
    }
    
    private static void redstoneIdle(SceneBuilder scene, BlockPos pos, int iterations) {
        for (int i = 0; i < iterations; i++) {
            scene.effects.indicateRedstone(pos);
            scene.idle(5);
        }
    }
}
