package org.antarcticgardens.newage.content.energiser;

import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.instruction.EmitParticlesInstruction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.antarcticgardens.newage.NewAgeItems;
import org.antarcticgardens.newage.content.electricity.connector.ElectricalConnectorBlockEntity;
import org.antarcticgardens.newage.content.electricity.wire.WireType;

public class EnergiserPonder {

    public static void ponder(SceneBuilder scene, SceneBuildingUtil util) {
        scene.configureBasePlate(0, 0, 5);
        scene.title("energiser", "Using an energiser.");
        scene.addKeyframe();
        scene.scaleSceneView(0.7f);
        scene.world.showSection(util.select.layer(0), Direction.UP);
        scene.idle(1);
        scene.idle(10);

        scene.world.showSection(util.select.column(3, 5), Direction.DOWN);

        scene.idle(10);

        scene.world.showSection(util.select.position(4, 3, 5), Direction.DOWN);
        scene.world.modifyBlockEntity(new BlockPos(3, 3, 5), EnergiserBlockEntity.class, EnergiserPonder::nomGiveElectricity);
        scene.world.modifyBlockEntity(new BlockPos(6, 3, 5), ElectricalConnectorBlockEntity.class, (be) -> {
            be.disconnect((ElectricalConnectorBlockEntity) be.getLevel().getBlockEntity(new BlockPos(4, 3, 3)));
        });
        scene.world.showSection(util.select.position(6, 3, 5), Direction.DOWN);

        scene.idle(10);

        scene.world.setKineticSpeed(util.select.position(3, 3, 5), 32);
        scene.effects.rotationSpeedIndicator(new BlockPos(3, 3, 5));

        scene.overlay.showText(260)
                        .text("")
                                .pointAt(new Vec3(3.5 , 3.5, 5.5));

        scene.idle(260);


        scene.idle(10);
        scene.world.createItemOnBeltLike(new BlockPos(3, 1, 5), Direction.DOWN, Items.IRON_INGOT.getDefaultInstance());

        for (int i = 0 ; i < 50 ; i++) {
            scene.world.modifyBlockEntity(new BlockPos(3, 3, 5), EnergiserBlockEntity.class, EnergiserPonder::progressEnergiser);
            scene.idle(1);
        }

        scene.world.removeItemsFromBelt(new BlockPos(3, 1, 5));
        scene.world.createItemOnBeltLike(new BlockPos(3, 1, 5), Direction.DOWN, NewAgeItems.OVERCHARGED_IRON.asStack());
        scene.effects.emitParticles(new Vec3(3.5, 1.8, 5.5), EmitParticlesInstruction.Emitter.simple(ParticleTypes.GLOW, new Vec3(0.2, 0.25, 0)),
                4, 2);

        scene.effects.emitParticles(new Vec3(3.5, 1.8, 5.5), EmitParticlesInstruction.Emitter.simple(ParticleTypes.GLOW, new Vec3(-0.2, 0.25, 0)),
                4, 2);

        scene.idle(100);

        scene.world.showSection(util.select.fromTo(0, 1, 0, 7, 5, 3), Direction.DOWN);

        scene.world.modifyBlockEntity(new BlockPos(4, 3, 3), ElectricalConnectorBlockEntity.class, (be) -> {
            be.connect((ElectricalConnectorBlockEntity) be.getLevel().getBlockEntity(new BlockPos(6, 3, 5)), WireType.COPPER);
        });

        scene.world.setKineticSpeed(util.select.everywhere(), 32);

        scene.idle(10);

        scene.world.createItemOnBelt(new BlockPos(5, 1, 3), Direction.DOWN, Items.GOLD_INGOT.getDefaultInstance());

        scene.idle(30);

        for (int i = 0 ; i < 50 ; i++) {
            scene.world.modifyBlockEntity(new BlockPos(3, 3, 3), EnergiserBlockEntity.class, EnergiserPonder::progressEnergiser);
            scene.idle(1);
        }

        scene.world.removeItemsFromBelt(new BlockPos(3, 1, 3));
        scene.world.createItemOnBeltLike(new BlockPos(3, 1, 3), Direction.DOWN, NewAgeItems.OVERCHARGED_GOLD.asStack());
        scene.effects.emitParticles(new Vec3(3.5, 1.8, 3.5), EmitParticlesInstruction.Emitter.simple(ParticleTypes.GLOW, new Vec3(0.2, 0.25, 0)),
                4, 2);

        scene.idleSeconds(3);


    }


    public static void nomGiveElectricity(EnergiserBlockEntity e) {
        e.energy.insertEnergy(100000, false);
    }

    public static void progressEnergiser(EnergiserBlockEntity e) {
        e.size+=0.17f;
    }

}