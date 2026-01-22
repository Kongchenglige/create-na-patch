package org.antarcticgardens.newage;

import com.simibubi.create.content.kinetics.base.CutoutRotatingInstance;
import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.ShaftInstance;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import org.antarcticgardens.newage.content.electricity.connector.ElectricalConnectorBlockEntity;
import org.antarcticgardens.newage.content.electricity.connector.ElectricalConnectorRenderer;
import org.antarcticgardens.newage.content.energiser.EnergiserBlockEntity;
import org.antarcticgardens.newage.content.energiser.EnergiserRenderer;
import org.antarcticgardens.newage.content.generation.carbonbrushes.CarbonBrushesBlockEntity;
import org.antarcticgardens.newage.content.generation.carbonbrushes.CarbonBrushesRenderer;
import org.antarcticgardens.newage.content.generation.generatorcoil.GeneratorCoilBlockEntity;
import org.antarcticgardens.newage.content.heat.heater.HeaterBlockEntity;
import org.antarcticgardens.newage.content.heat.heatpipe.HeatPipeBlockEntity;
import org.antarcticgardens.newage.content.heat.heatpump.HeatPumpBlockEntity;
import org.antarcticgardens.newage.content.heat.solarheatingplate.SolarHeatingPlateBlockEntity;
import org.antarcticgardens.newage.content.heat.stirlingengine.StirlingEngineBlockEntity;
import org.antarcticgardens.newage.content.heat.stirlingengine.StirlingEngineInstance;
import org.antarcticgardens.newage.content.heat.stirlingengine.StirlingEngineRenderer;
import org.antarcticgardens.newage.content.motors.MotorBlockEntity;
import org.antarcticgardens.newage.content.motors.extension.MotorExtensionBlockEntity;
import org.antarcticgardens.newage.content.motors.extension.variants.AdvancedMotorExtensionVariant;
import org.antarcticgardens.newage.content.motors.extension.variants.BasicMotorExtensionVariant;
import org.antarcticgardens.newage.content.motors.variants.AdvancedMotorVariant;
import org.antarcticgardens.newage.content.motors.variants.BasicMotorVariant;
import org.antarcticgardens.newage.content.motors.variants.ReinforcedMotorVariant;
import org.antarcticgardens.newage.content.reactor.reactorfuelacceptor.ReactorFuelAcceptorBlockEntity;
import org.antarcticgardens.newage.content.reactor.reactorheatvent.ReactorHeatVentBlockEntity;
import org.antarcticgardens.newage.content.reactor.reactorrod.ReactorRodBlockEntity;
import org.antarcticgardens.newage.tools.HalfShaftRendererThing;


import static org.antarcticgardens.newage.CreateNewAge.REGISTRATE;

public class NewAgeBlockEntityTypes {
    public static final BlockEntityEntry<EnergiserBlockEntity> ENERGISER_T1 = REGISTRATE
            .blockEntity("energiser_t1", EnergiserBlockEntity::newTier1)
            .instance(() -> ShaftInstance::new)
            .validBlocks(NewAgeBlocks.ENERGISER_T1)
            .renderer(() -> EnergiserRenderer::new)
            .register();

    public static final BlockEntityEntry<EnergiserBlockEntity> ENERGISER_T2 = REGISTRATE
            .blockEntity("energiser_t2", EnergiserBlockEntity::newTier2)
            .instance(() -> ShaftInstance::new)
            .validBlocks(NewAgeBlocks.ENERGISER_T2)
            .renderer(() -> EnergiserRenderer::new)
            .register();

    public static final BlockEntityEntry<EnergiserBlockEntity> ENERGISER_T3 = REGISTRATE
            .blockEntity("energiser_t3", EnergiserBlockEntity::newTier3)
            .instance(() -> ShaftInstance::new)
            .validBlocks(NewAgeBlocks.ENERGISER_T3)
            .renderer(() -> EnergiserRenderer::new)
            .register();

    public static final BlockEntityEntry<CarbonBrushesBlockEntity> CARBON_BRUSHES = REGISTRATE
            .blockEntity("carbon_brushes", CarbonBrushesBlockEntity::new)
            .instance(() -> ShaftInstance::new)
            .validBlocks(NewAgeBlocks.CARBON_BRUSHES)
            .renderer(() -> CarbonBrushesRenderer::new)
            .register();

    public static final BlockEntityEntry<HeatPipeBlockEntity> HEAT_PIPE = REGISTRATE
            .blockEntity("heat_pipe", HeatPipeBlockEntity::new)
            .validBlocks(NewAgeBlocks.HEAT_PIPE)
            .register();

    public static final BlockEntityEntry<HeatPumpBlockEntity> HEAT_PUMP = REGISTRATE
            .blockEntity("heat_pump", HeatPumpBlockEntity::new)
            .validBlocks(NewAgeBlocks.HEAT_PUMP)
            .register();


    public static final BlockEntityEntry<SolarHeatingPlateBlockEntity> BASIC_SOLAR_HEATING_PLATE = REGISTRATE
            .blockEntity("basic_solar_heating_plate", SolarHeatingPlateBlockEntity::createBasic)
            .validBlocks(NewAgeBlocks.BASIC_SOLAR_HEATING_PLATE)
            .register();

    public static final BlockEntityEntry<SolarHeatingPlateBlockEntity> ADVANCED_SOLAR_HEATING_PLATE = REGISTRATE
            .blockEntity("advanced_solar_heating_plate", SolarHeatingPlateBlockEntity::createAdvanced)
            .validBlocks(NewAgeBlocks.ADVANCED_SOLAR_HEATING_PLATE)
            .register();


    public static final BlockEntityEntry<HeaterBlockEntity> HEATER = REGISTRATE
            .blockEntity("heater", HeaterBlockEntity::new)
            .validBlocks(NewAgeBlocks.HEATER)
            .register();

    public static final BlockEntityEntry<ReactorRodBlockEntity> REACTOR_ROD = REGISTRATE
            .blockEntity("reactor_rod", ReactorRodBlockEntity::new)
            .validBlocks(NewAgeBlocks.REACTOR_ROD)
            .register();

    public static final BlockEntityEntry<ReactorFuelAcceptorBlockEntity> REACTOR_FUEL_ACCEPTOR = REGISTRATE
            .blockEntity("reactor_fuel_acceptor", ReactorFuelAcceptorBlockEntity::new)
            .validBlocks(NewAgeBlocks.REACTOR_FUEL_ACCEPTOR)
            .register();

    public static final BlockEntityEntry<ReactorHeatVentBlockEntity> REACTOR_HEAT_VENT = REGISTRATE
            .blockEntity("reactor_heat_vent", ReactorHeatVentBlockEntity::new)
            .validBlocks(NewAgeBlocks.REACTOR_HEAT_VENT)
            .register();

    public static final BlockEntityEntry<GeneratorCoilBlockEntity> GENERATOR_COIL = REGISTRATE
            .blockEntity("generator_coil", GeneratorCoilBlockEntity::new)
            .instance(() -> CutoutRotatingInstance::new)
            .validBlocks(NewAgeBlocks.GENERATOR_COIL)
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<StirlingEngineBlockEntity> STIRLING_ENGINE = REGISTRATE
            .blockEntity("stirling_engine", StirlingEngineBlockEntity::new)
            .instance(() -> StirlingEngineInstance::new)
            .validBlocks(NewAgeBlocks.STIRLING_ENGINE)
            .renderer(() -> StirlingEngineRenderer::new)
            .register();


    public static final BlockEntityEntry<MotorBlockEntity> BASIC_MOTOR = REGISTRATE
            .blockEntity("basic_motor", MotorBlockEntity.create(new BasicMotorVariant()))
            .instance(() -> HalfShaftInstance::new)
            .validBlocks(NewAgeBlocks.BASIC_MOTOR)
            .renderer(() -> HalfShaftRendererThing::new)
            .register();


    public static final BlockEntityEntry<MotorBlockEntity> ADVANCED_MOTOR = REGISTRATE
            .blockEntity("advanced_motor", MotorBlockEntity.create(new AdvancedMotorVariant()))
            .instance(() -> HalfShaftInstance::new)
            .validBlocks(NewAgeBlocks.ADVANCED_MOTOR)
            .renderer(() -> HalfShaftRendererThing::new)
            .register();


    public static final BlockEntityEntry<MotorBlockEntity> REINFORCED_MOTOR = REGISTRATE
            .blockEntity("reinforced_motor", MotorBlockEntity.create(new ReinforcedMotorVariant()))
            .instance(() -> HalfShaftInstance::new)
            .validBlocks(NewAgeBlocks.REINFORCED_MOTOR)
            .renderer(() -> HalfShaftRendererThing::new)
            .register();

    public static final BlockEntityEntry<ElectricalConnectorBlockEntity> ELECTRICAL_CONNECTOR = REGISTRATE
            .blockEntity("electrical_connector", ElectricalConnectorBlockEntity::new)
            .validBlocks(NewAgeBlocks.ELECTRICAL_CONNECTOR)
            .renderer(() -> ElectricalConnectorRenderer::new)
            .register();

    public static final BlockEntityEntry<MotorExtensionBlockEntity> BASIC_MOTOR_EXTENSION = REGISTRATE
            .blockEntity("basic_motor_extension", MotorExtensionBlockEntity.create(new BasicMotorExtensionVariant()))
            .validBlocks(NewAgeBlocks.BASIC_MOTOR_EXTENSION)
            .register();

    public static final BlockEntityEntry<MotorExtensionBlockEntity> ADVANCED_MOTOR_EXTENSION = REGISTRATE
            .blockEntity("advanced_motor_extension", MotorExtensionBlockEntity.create(new AdvancedMotorExtensionVariant()))
            .validBlocks(NewAgeBlocks.ADVANCED_MOTOR_EXTENSION)
            .register();

    public static void load() {  }
}
