package org.antarcticgardens.newage;

import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.fabricmc.api.ClientModInitializer;
import org.antarcticgardens.newage.content.electricity.ElectricityPonder;
import org.antarcticgardens.newage.content.energiser.EnergiserPonder;
import org.antarcticgardens.newage.content.generation.GenerationPonder;
import org.antarcticgardens.newage.content.heat.HeatingPonder;
import org.antarcticgardens.newage.content.heat.heater.HeaterPonder;
import org.antarcticgardens.newage.content.motors.MotorPonder;
import org.antarcticgardens.newage.content.motors.extension.MotorExtensionPonder;
import org.antarcticgardens.newage.content.reactor.ReactorPonder;

public class CreateNewAgeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        // ponders
        PonderRegistrationHelper helper = new PonderRegistrationHelper(CreateNewAge.MOD_ID);

        var electrical = helper.createTag("electrical")
                .item(NewAgeBlocks.ENERGISER_T3.get())
                .addToIndex();

        var wires = helper.createTag("wiring")
                .item(NewAgeItems.COPPER_WIRE)
                .addToIndex();

        var magnets = helper.createTag("magnets")
                .item(NewAgeBlocks.REDSTONE_MAGNET.get())
                .addToIndex();

        var electricityGeneration = helper.createTag("electricity_generation")
                .item(NewAgeBlocks.GENERATOR_COIL)
                .addToIndex();

        var heating = helper.createTag("heating")
                .item(NewAgeBlocks.HEAT_PIPE.get())
                .addToIndex();

        var reactor = helper.createTag("reactor")
                .item(NewAgeBlocks.REACTOR_ROD.get())
                .addToIndex();
        
        var motorExtension = helper.createTag("motor_extension")
                .item(NewAgeBlocks.BASIC_MOTOR_EXTENSION.get())
                .addToIndex();

        PonderRegistry.TAGS.forTag(electrical)
                .add(NewAgeBlocks.ENERGISER_T1)
                .add(NewAgeBlocks.ENERGISER_T2)
                .add(NewAgeBlocks.ENERGISER_T3)

                .add(NewAgeBlocks.BASIC_MOTOR)
                .add(NewAgeBlocks.ADVANCED_MOTOR)
                .add(NewAgeBlocks.REINFORCED_MOTOR)

                .add(NewAgeBlocks.GENERATOR_COIL)

                .add(NewAgeBlocks.ELECTRICAL_CONNECTOR);

        PonderRegistry.TAGS.forTag(wires)
                .add(NewAgeBlocks.ELECTRICAL_CONNECTOR)

                .add(NewAgeItems.COPPER_WIRE)
                .add(NewAgeItems.IRON_WIRE)
                .add(NewAgeItems.GOLDEN_WIRE)
                .add(NewAgeItems.DIAMOND_WIRE);

        PonderRegistry.TAGS.forTag(magnets)
                .add(NewAgeBlocks.MAGNETITE_BLOCK)
                .add(NewAgeBlocks.REDSTONE_MAGNET)
                .add(NewAgeBlocks.LAYERED_MAGNET)
                .add(NewAgeBlocks.FLUXUATED_MAGNETITE)
                .add(NewAgeBlocks.NETHERITE_MAGNET);

        PonderRegistry.TAGS.forTag(electricityGeneration)
                .add(NewAgeBlocks.CARBON_BRUSHES)
                .add(NewAgeBlocks.GENERATOR_COIL)
                .add(NewAgeBlocks.MAGNETITE_BLOCK)
                .add(NewAgeBlocks.REDSTONE_MAGNET)
                .add(NewAgeBlocks.LAYERED_MAGNET)
                .add(NewAgeBlocks.FLUXUATED_MAGNETITE)
                .add(NewAgeBlocks.NETHERITE_MAGNET);

        PonderRegistry.TAGS.forTag(heating)
                .add(NewAgeBlocks.HEAT_PIPE)
                .add(NewAgeBlocks.HEAT_PUMP)
                .add(NewAgeBlocks.HEATER)
                .add(NewAgeBlocks.STIRLING_ENGINE)
                .add(NewAgeBlocks.REACTOR_ROD)
                .add(NewAgeBlocks.BASIC_SOLAR_HEATING_PLATE)
                .add(NewAgeBlocks.ADVANCED_SOLAR_HEATING_PLATE);

        PonderRegistry.TAGS.forTag(reactor)
                .add(NewAgeBlocks.REACTOR_CASING)
                .add(NewAgeBlocks.REACTOR_GLASS)
                .add(NewAgeBlocks.REACTOR_ROD)
                .add(NewAgeBlocks.REACTOR_HEAT_VENT)
                .add(NewAgeBlocks.REACTOR_FUEL_ACCEPTOR)
                .add(NewAgeItems.NUCLEAR_FUEL);
        
        PonderRegistry.TAGS.forTag(motorExtension)
                .add(NewAgeBlocks.BASIC_MOTOR_EXTENSION)
                .add(NewAgeBlocks.ADVANCED_MOTOR_EXTENSION);


        helper.addStoryBoard(NewAgeBlocks.ENERGISER_T1, "energiser", EnergiserPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.ENERGISER_T2, "energiser", EnergiserPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.ENERGISER_T3, "energiser", EnergiserPonder::ponder);

        helper.addStoryBoard(NewAgeBlocks.HEAT_PIPE, "heating", HeatingPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.HEAT_PUMP, "heating", HeatingPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.BASIC_SOLAR_HEATING_PLATE, "heating", HeatingPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.ADVANCED_SOLAR_HEATING_PLATE, "heating", HeatingPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.STIRLING_ENGINE, "heating", HeatingPonder::ponder);

        helper.addStoryBoard(NewAgeBlocks.HEATER, "heater", HeaterPonder::ponder);

        helper.addStoryBoard(NewAgeBlocks.REACTOR_CASING, "reactor", ReactorPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.REACTOR_GLASS, "reactor", ReactorPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.REACTOR_FUEL_ACCEPTOR, "reactor", ReactorPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.REACTOR_ROD, "reactor", ReactorPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.REACTOR_HEAT_VENT, "reactor", ReactorPonder::ponder);
        helper.addStoryBoard(NewAgeItems.NUCLEAR_FUEL, "reactor", ReactorPonder::ponder);

        helper.addStoryBoard(NewAgeBlocks.ELECTRICAL_CONNECTOR, "wires", ElectricityPonder::ponder, electrical);
        helper.addStoryBoard(NewAgeItems.COPPER_WIRE, "wires", ElectricityPonder::ponder);
        helper.addStoryBoard(NewAgeItems.DIAMOND_WIRE, "wires", ElectricityPonder::ponder);
        helper.addStoryBoard(NewAgeItems.GOLDEN_WIRE, "wires", ElectricityPonder::ponder);
        helper.addStoryBoard(NewAgeItems.IRON_WIRE, "wires", ElectricityPonder::ponder);

        helper.addStoryBoard(NewAgeBlocks.CARBON_BRUSHES, "generation", GenerationPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.GENERATOR_COIL, "generation", GenerationPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.MAGNETITE_BLOCK, "generation", GenerationPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.REDSTONE_MAGNET, "generation", GenerationPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.LAYERED_MAGNET, "generation", GenerationPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.FLUXUATED_MAGNETITE, "generation", GenerationPonder::ponder);
        helper.addStoryBoard(NewAgeBlocks.NETHERITE_MAGNET, "generation", GenerationPonder::ponder);

        helper.forComponents(NewAgeBlocks.BASIC_MOTOR, NewAgeBlocks.ADVANCED_MOTOR, NewAgeBlocks.REINFORCED_MOTOR)
                .addStoryBoard("motor", MotorPonder::motor);
        
        helper.forComponents(NewAgeBlocks.BASIC_MOTOR_EXTENSION, NewAgeBlocks.ADVANCED_MOTOR_EXTENSION)
                .addStoryBoard("motor_extension", MotorExtensionPonder::motorExtension);


        // ToolTip
        addToolTipModifier(NewAgeBlocks.ENERGISER_T1);
        addToolTipModifier(NewAgeBlocks.ENERGISER_T2);
        addToolTipModifier(NewAgeBlocks.ENERGISER_T3);
        addToolTipModifier(NewAgeBlocks.STIRLING_ENGINE);
        addToolTipModifier(NewAgeBlocks.GENERATOR_COIL);

    }

    public void addToolTipModifier(BlockEntry<?> entry) {
        TooltipModifier.REGISTRY.register(entry.asItem(), KineticStats.create(entry.asItem()));
    }
}
