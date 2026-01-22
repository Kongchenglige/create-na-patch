package org.antarcticgardens.newage.content.reactor;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.ponder.instruction.EmitParticlesInstruction;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.antarcticgardens.newage.NewAgeBlocks;
import org.antarcticgardens.newage.NewAgeItems;
import org.antarcticgardens.newage.content.heat.heater.HeaterBlock;
import org.antarcticgardens.newage.content.reactor.reactorrod.ReactorRodBlock;

public class ReactorPonder {

    public static void ponder(SceneBuilder scene, SceneBuildingUtil util) {
        scene.configureBasePlate(0, 0, 9);
        scene.title("reactor", "Generating Electricity the Nuclear Way");
        scene.showBasePlate();

        scene.world.showSection(util.select.fromTo(0, 0, 0, 9, 0, 9), Direction.UP);

        scene.idle(10);

        scene.world.showSection(util.select.fromTo(4, 1, 2, 6, 1, 6), Direction.DOWN);

        scene.idle(12);

        scene.overlay.showText(60).placeNearTarget()
                .text("").pointAt(new Vec3(4.5, 3.5, 3.5));

        scene.idle(70);

        scene.world.showSection(util.select.fromTo(5,2, 3, 5, 2, 5), Direction.DOWN);

        scene.overlay.showText(80).placeNearTarget()
                .text("").pointAt(new Vec3(4.5, 4.5, 3.5));

        scene.idle(80);

        scene.addKeyframe();

        scene.idle(5);

        scene.world.showSection(util.select.position(5, 2, 2), Direction.SOUTH);

        scene.idle(10);

        scene.overlay.showText(80).placeNearTarget()
                .text("").pointAt(new Vec3(5.5, 2.5, 2.5));

        scene.idle(10);
        scene.overlay.showOutline(PonderPalette.BLUE, null, util.select.fromTo(5,2, 3, 5, 2, 5), 40);

        scene.idle(80);

        scene.world.showSection(util.select.position(5, 2, 1), Direction.DOWN);
        scene.idle(10);
        scene.overlay.showControls(new InputWindowElement(new Vec3(5.5, 2.5, 1.5), Pointing.DOWN).withItem(NewAgeItems.NUCLEAR_FUEL.asStack()), 30);
        scene.idle(10);
        scene.world.flapFunnel(util.grid.at(5, 2, 1), false);
        scene.idle(10);

        scene.world.modifyBlocks(util.select.fromTo(5, 2, 3, 5, 2, 5), blockState -> blockState.setValue(ReactorRodBlock.ACTIVE, true), false);

        scene.idle(70);
        scene.addKeyframe();

        scene.rotateCameraY(-90);
        scene.idle(20);

        scene.world.showSection(util.select.position(5, 2, 6), Direction.NORTH);

        scene.idle(10);
        scene.overlay.showText(100).placeNearTarget()
                .text("").pointAt(new Vec3(5.5, 2.5, 6.5));

        scene.world.showSection(util.select.fromTo(2, 1, 3, 2, 1, 5), Direction.DOWN);


        scene.idle(5);
        scene.world.showSection(util.select.position(5, 2, 7), Direction.NORTH);

        scene.idle(5);
        scene.world.showSection(util.select.position(5, 1, 7), Direction.NORTH);

        scene.idle(5);
        scene.world.showSection(util.select.position(4, 1, 7), Direction.NORTH);

        scene.idle(5);
        scene.world.showSection(util.select.position(3, 1, 7), Direction.NORTH);

        scene.idle(5);
        scene.world.showSection(util.select.position(2, 1, 7), Direction.NORTH);

        scene.idle(5);
        scene.world.showSection(util.select.position(2, 1, 6), Direction.DOWN);

        scene.idle(15);

        scene.world.modifyBlocks(util.select.fromTo(2, 1, 3, 2, 1, 5), blockState -> blockState.setValue(HeaterBlock.STRENGTH, BlazeBurnerBlock.HeatLevel.SEETHING), false);

        scene.idle(70);

        scene.addKeyframe();

        scene.overlay.showText(120).placeNearTarget()
                .text("").pointAt(new Vec3(5.5, 2.5, 4.5));

        scene.idle(10);

        var ent = scene.world.createEntity(level -> {
            Creeper c = new Creeper(EntityType.CREEPER, level);
            c.setPos(1, 1, 4);
            return c;
        });


        for (int i = 0 ; i < 3 ; i++) {
            scene.idle(20);
            scene.world.modifyEntity(ent, entity -> entity.animateHurt(0));
        }

        for (int i = 0 ; i < 5 ; i++) {
            scene.effects.emitParticles(new Vec3(1 + i * 0.2, 1 + 0.4 * i, 4+ 0.2 * i), EmitParticlesInstruction.Emitter.simple(ParticleTypes.CLOUD, new Vec3(0, 0, 0)), 3, 1);
        }

        scene.idle(2);
        scene.world.modifyEntity(ent, Entity::discard);

        scene.idle(50);

        scene.overlay.showText(120).placeNearTarget()
                .text("").pointAt(new Vec3(5.5, 2.5, 4.5));

        scene.idle(80);

        for (int z = 0 ; z < 5; z++) {
            scene.world.showSection(util.select.position(4, 3, z + 2), Direction.DOWN);
            scene.world.showSection(util.select.position(4, 2, z + 2), Direction.DOWN);
            scene.world.showSection(util.select.position(6, 2, z + 2), Direction.DOWN);
            scene.world.showSection(util.select.position(6, 3, z + 2), Direction.DOWN);
            scene.world.showSection(util.select.position(5, 3, z + 2), Direction.DOWN);
            scene.idle(5);
        }

        scene.idle(40);

        scene.addKeyframe();

        scene.overlay.showText(120).placeNearTarget()
                .text("").pointAt(new Vec3(5.5, 2.5, 4.5));

        scene.idle(30);

        scene.world.setBlocks(util.select.fromTo(2, 1, 3, 2, 1, 5), Blocks.AIR.defaultBlockState(), true);

        scene.world.modifyBlock(util.grid.at(2, 1, 6),
                blockState -> blockState.setValue(BlockStateProperties.NORTH, false),
                false);

        scene.idle(40);

        scene.world.modifyBlocks(util.select.fromTo(5, 2, 3, 5, 2, 5), blockState -> blockState.setValue(ReactorRodBlock.ACTIVE, true), false);

        scene.idle(20);

        scene.world.setBlocks(util.select.fromTo(5, 2, 3, 5, 2, 5), NewAgeBlocks.CORIUM.getDefaultState(), false);

        scene.idle(20);

        scene.world.setBlock(util.grid.at(5, 2, 5), NewAgeBlocks.SOLID_CORIUM.getDefaultState(), false);

        scene.world.setBlock(util.grid.at(5, 2, 4), Blocks.AIR.defaultBlockState(), false);

        scene.world.setBlock(util.grid.at(5, 2, 3),Blocks.AIR.defaultBlockState(), false);

        scene.world.setBlock(util.grid.at(5, 1, 4), NewAgeBlocks.CORIUM.getDefaultState(), false);

        scene.world.setBlock(util.grid.at(5, 1, 3), NewAgeBlocks.CORIUM.getDefaultState(), false);

        scene.idle(20);

        scene.world.setBlock(util.grid.at(5, 1, 4), NewAgeBlocks.SOLID_CORIUM.getDefaultState(), false);

        scene.world.setBlock(util.grid.at(5, 1, 3), Blocks.AIR.defaultBlockState(), false);
        scene.world.setBlock(util.grid.at(5, 0, 3), NewAgeBlocks.CORIUM.getDefaultState(), false);

        scene.idle(20);

        scene.world.setBlock(util.grid.at(5, 0, 3), NewAgeBlocks.SOLID_CORIUM.getDefaultState(), false);


        scene.idle(20);

        scene.markAsFinished();

    }

}
