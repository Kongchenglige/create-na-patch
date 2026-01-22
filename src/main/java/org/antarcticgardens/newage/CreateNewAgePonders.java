package org.antarcticgardens.newage;

import com.simibubi.create.Create;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.level.PonderLevel;
import net.createmod.ponder.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import org.antarcticgardens.newage.content.electricity.ElectricityPonder;
import org.antarcticgardens.newage.content.energiser.EnergiserPonder;
import org.antarcticgardens.newage.content.generation.GenerationPonder;
import org.antarcticgardens.newage.content.heat.HeatingPonder;
import org.antarcticgardens.newage.content.heat.heater.HeaterPonder;
import org.antarcticgardens.newage.content.motors.MotorPonder;
import org.antarcticgardens.newage.content.motors.extension.MotorExtensionPonder;
import org.antarcticgardens.newage.content.reactor.ReactorPonder;

public class CreateNewAgePonders implements PonderPlugin {
    @Override
    public String getModId() {
        return CreateNewAge.MOD_ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        register(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        register(helper);
    }

    @Override
    public void registerSharedText(SharedTextRegistrationHelper helper) {
        PonderPlugin.super.registerSharedText(helper);
    }

    @Override
    public void onPonderLevelRestore(PonderLevel ponderLevel) {
        PonderPlugin.super.onPonderLevelRestore(ponderLevel);
    }

    @Override
    public void indexExclusions(IndexExclusionHelper helper) {
        PonderPlugin.super.indexExclusions(helper);
    }

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.addStoryBoard(NewAgeBlocks.ENERGISER_T1, "energiser", EnergiserPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.ENERGISER_T2, "energiser", EnergiserPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.ENERGISER_T3, "energiser", EnergiserPonder::ponder);

        HELPER.addStoryBoard(NewAgeBlocks.HEAT_PIPE, "heating", HeatingPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.HEAT_PUMP, "heating", HeatingPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.BASIC_SOLAR_HEATING_PLATE, "heating", HeatingPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.ADVANCED_SOLAR_HEATING_PLATE, "heating", HeatingPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.STIRLING_ENGINE, "heating", HeatingPonder::ponder);

        HELPER.addStoryBoard(NewAgeBlocks.HEATER, "heater", HeaterPonder::ponder);

        HELPER.addStoryBoard(NewAgeBlocks.REACTOR_CASING, "reactor", ReactorPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.REACTOR_GLASS, "reactor", ReactorPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.REACTOR_FUEL_ACCEPTOR, "reactor", ReactorPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.REACTOR_ROD, "reactor", ReactorPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.REACTOR_HEAT_VENT, "reactor", ReactorPonder::ponder);
        HELPER.addStoryBoard(NewAgeItems.NUCLEAR_FUEL, "reactor", ReactorPonder::ponder);

        HELPER.addStoryBoard(NewAgeBlocks.ELECTRICAL_CONNECTOR, "wires", ElectricityPonder::ponder);
        HELPER.addStoryBoard(NewAgeItems.COPPER_WIRE, "wires", ElectricityPonder::ponder);
        HELPER.addStoryBoard(NewAgeItems.DIAMOND_WIRE, "wires", ElectricityPonder::ponder);
        HELPER.addStoryBoard(NewAgeItems.GOLDEN_WIRE, "wires", ElectricityPonder::ponder);
        HELPER.addStoryBoard(NewAgeItems.IRON_WIRE, "wires", ElectricityPonder::ponder);

        HELPER.addStoryBoard(NewAgeBlocks.CARBON_BRUSHES, "generation", GenerationPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.GENERATOR_COIL, "generation", GenerationPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.MAGNETITE_BLOCK, "generation", GenerationPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.REDSTONE_MAGNET, "generation", GenerationPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.LAYERED_MAGNET, "generation", GenerationPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.FLUXUATED_MAGNETITE, "generation", GenerationPonder::ponder);
        HELPER.addStoryBoard(NewAgeBlocks.NETHERITE_MAGNET, "generation", GenerationPonder::ponder);

        HELPER.forComponents(NewAgeBlocks.BASIC_MOTOR, NewAgeBlocks.ADVANCED_MOTOR, NewAgeBlocks.REINFORCED_MOTOR)
          .addStoryBoard("motor", MotorPonder::motor);

        HELPER.forComponents(NewAgeBlocks.BASIC_MOTOR_EXTENSION, NewAgeBlocks.ADVANCED_MOTOR_EXTENSION)
                .addStoryBoard("motor_extension", MotorExtensionPonder::motorExtension);

    }

    static ResourceLocation ELECTRICAL = new ResourceLocation(CreateNewAge.MOD_ID, "electrical");
    static ResourceLocation WIRING = new ResourceLocation(CreateNewAge.MOD_ID, "wiring");
    static ResourceLocation MAGNETS = new ResourceLocation(CreateNewAge.MOD_ID, "magnets");
    static ResourceLocation ELECTRICITY_GENERATION = new ResourceLocation(CreateNewAge.MOD_ID, "electricity_generation");
    static ResourceLocation HEATING = new ResourceLocation(CreateNewAge.MOD_ID, "heating");
    static ResourceLocation REACTOR = new ResourceLocation(CreateNewAge.MOD_ID, "reactor");
    static ResourceLocation MOTOR_EXTENSION = new ResourceLocation(CreateNewAge.MOD_ID, "motor_extension");

    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<RegistryEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.registerTag(ELECTRICAL)
                .item(NewAgeBlocks.ENERGISER_T3.get())
                .addToIndex()
                .register();

        HELPER.registerTag(WIRING)
                .item(NewAgeItems.COPPER_WIRE)
                .addToIndex()
                .register();

        HELPER.registerTag(MAGNETS)
                .item(NewAgeBlocks.REDSTONE_MAGNET.get())
                .addToIndex()
                .register();

        HELPER.registerTag(ELECTRICITY_GENERATION)
                .item(NewAgeBlocks.GENERATOR_COIL)
                .addToIndex()
                .register();

        HELPER.registerTag(HEATING)
                .item(NewAgeBlocks.HEAT_PIPE.get())
                .addToIndex()
                .register();

        HELPER.registerTag(REACTOR)
                .item(NewAgeBlocks.REACTOR_ROD.get())
                .addToIndex()
                .register();

        HELPER.registerTag(MOTOR_EXTENSION)
                .item(NewAgeBlocks.BASIC_MOTOR_EXTENSION.get())
                .addToIndex()
                .register();

        HELPER.addToTag(ELECTRICAL)
                .add(NewAgeBlocks.ENERGISER_T1)
                .add(NewAgeBlocks.ENERGISER_T2)
                .add(NewAgeBlocks.ENERGISER_T3)

                .add(NewAgeBlocks.BASIC_MOTOR)
                .add(NewAgeBlocks.ADVANCED_MOTOR)
                .add(NewAgeBlocks.REINFORCED_MOTOR)

                .add(NewAgeBlocks.GENERATOR_COIL)

                .add(NewAgeBlocks.ELECTRICAL_CONNECTOR);

        HELPER.addToTag(WIRING)
                .add(NewAgeBlocks.ELECTRICAL_CONNECTOR)

                .add(NewAgeItems.COPPER_WIRE)
                .add(NewAgeItems.IRON_WIRE)
                .add(NewAgeItems.GOLDEN_WIRE)
                .add(NewAgeItems.DIAMOND_WIRE);

        HELPER.addToTag(MAGNETS)
                .add(NewAgeBlocks.MAGNETITE_BLOCK)
                .add(NewAgeBlocks.REDSTONE_MAGNET)
                .add(NewAgeBlocks.LAYERED_MAGNET)
                .add(NewAgeBlocks.FLUXUATED_MAGNETITE)
                .add(NewAgeBlocks.NETHERITE_MAGNET);

        HELPER.addToTag(ELECTRICITY_GENERATION)
                .add(NewAgeBlocks.CARBON_BRUSHES)
                .add(NewAgeBlocks.GENERATOR_COIL)
                .add(NewAgeBlocks.MAGNETITE_BLOCK)
                .add(NewAgeBlocks.REDSTONE_MAGNET)
                .add(NewAgeBlocks.LAYERED_MAGNET)
                .add(NewAgeBlocks.FLUXUATED_MAGNETITE)
                .add(NewAgeBlocks.NETHERITE_MAGNET);

        HELPER.addToTag(HEATING)
                .add(NewAgeBlocks.HEAT_PIPE)
                .add(NewAgeBlocks.HEAT_PUMP)
                .add(NewAgeBlocks.HEATER)
                .add(NewAgeBlocks.STIRLING_ENGINE)
                .add(NewAgeBlocks.REACTOR_ROD)
                .add(NewAgeBlocks.BASIC_SOLAR_HEATING_PLATE)
                .add(NewAgeBlocks.ADVANCED_SOLAR_HEATING_PLATE);

        HELPER.addToTag(REACTOR)
                .add(NewAgeBlocks.REACTOR_CASING)
                .add(NewAgeBlocks.REACTOR_GLASS)
                .add(NewAgeBlocks.REACTOR_ROD)
                .add(NewAgeBlocks.REACTOR_HEAT_VENT)
                .add(NewAgeBlocks.REACTOR_FUEL_ACCEPTOR)
                .add(NewAgeItems.NUCLEAR_FUEL);

        HELPER.addToTag(MOTOR_EXTENSION)
                .add(NewAgeBlocks.BASIC_MOTOR_EXTENSION)
                .add(NewAgeBlocks.ADVANCED_MOTOR_EXTENSION);
    }
}
