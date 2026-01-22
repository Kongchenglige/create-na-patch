package org.antarcticgardens.newage;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.block.connected.SimpleCTBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.antarcticgardens.newage.content.electricity.connector.ElectricalConnectorBlock;
import org.antarcticgardens.newage.content.energiser.EnergiserBlock;
import org.antarcticgardens.newage.content.generation.carbonbrushes.CarbonBrushesBlock;
import org.antarcticgardens.newage.content.generation.generatorcoil.GeneratorCoilBlock;
import org.antarcticgardens.newage.content.generation.magnets.ImplementedMagnetBlock;
import org.antarcticgardens.newage.content.heat.heater.HeaterBlock;
import org.antarcticgardens.newage.content.heat.heatpipe.HeatPipeBlock;
import org.antarcticgardens.newage.content.heat.heatpump.HeatPumpBlock;
import org.antarcticgardens.newage.content.heat.solarheatingplate.SolarHeatingPlateBlock;
import org.antarcticgardens.newage.content.heat.stirlingengine.StirlingEngineBlock;
import org.antarcticgardens.newage.content.motors.MotorBlock;
import org.antarcticgardens.newage.content.motors.extension.MotorExtensionBlock;
import org.antarcticgardens.newage.content.motors.extension.variants.AdvancedMotorExtensionVariant;
import org.antarcticgardens.newage.content.motors.extension.variants.BasicMotorExtensionVariant;
import org.antarcticgardens.newage.content.motors.variants.AdvancedMotorVariant;
import org.antarcticgardens.newage.content.motors.variants.BasicMotorVariant;
import org.antarcticgardens.newage.content.motors.variants.ReinforcedMotorVariant;
import org.antarcticgardens.newage.content.reactor.CoriumBlock;
import org.antarcticgardens.newage.content.reactor.ReactorBlock;
import org.antarcticgardens.newage.content.reactor.ReactorTransparentBlock;
import org.antarcticgardens.newage.content.reactor.SolidCoriumBlock;
import org.antarcticgardens.newage.content.reactor.reactorfuelacceptor.ReactorFuelAcceptorBlock;
import org.antarcticgardens.newage.content.reactor.reactorheatvent.ReactorHeatVentBlock;
import org.antarcticgardens.newage.content.reactor.reactorrod.ReactorRodBlock;

import static org.antarcticgardens.newage.CreateNewAge.REGISTRATE;

public class NewAgeBlocks {
    static {
        REGISTRATE.defaultCreativeTab(CreateNewAge.CREATIVE_TAB_KEY);
    }

    public static final BlockEntry<Block> ENERGISER_T1 =
            REGISTRATE.block("energiser_t1", EnergiserBlock::createT1)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item(AssemblyOperatorBlockItem::new)
                    .build()
                    .register();

    public static final BlockEntry<Block> ENERGISER_T2 =
            REGISTRATE.block("energiser_t2", EnergiserBlock::createT2)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item(AssemblyOperatorBlockItem::new)
                    .build()
                    .register();

    public static final BlockEntry<Block> ENERGISER_T3 =
            REGISTRATE.block("energiser_t3", EnergiserBlock::createT3)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(BlockStressDefaults.setImpact(32.0))
                    .item(AssemblyOperatorBlockItem::new)
                    .build()
                    .register();

    public static final BlockEntry<ElectricalConnectorBlock> ELECTRICAL_CONNECTOR =
            REGISTRATE.block("electrical_connector", ElectricalConnectorBlock::new)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(0.4f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<MotorBlock> BASIC_MOTOR =
            REGISTRATE.block("basic_motor", (p) -> new MotorBlock(p,
                                                                  NewAgeBlockEntityTypes.BASIC_MOTOR, new BasicMotorVariant()))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(3.0f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<MotorBlock> ADVANCED_MOTOR =
            REGISTRATE.block("advanced_motor", (p) -> new MotorBlock(p, NewAgeBlockEntityTypes.ADVANCED_MOTOR, new AdvancedMotorVariant()))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(3.5f))
                    .simpleItem()
                    .register();


    public static final BlockEntry<MotorBlock> REINFORCED_MOTOR =
            REGISTRATE.block("reinforced_motor", (p) -> new MotorBlock(p, NewAgeBlockEntityTypes.REINFORCED_MOTOR, new ReinforcedMotorVariant()))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(4.0f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<MotorExtensionBlock> BASIC_MOTOR_EXTENSION =
            REGISTRATE.block("basic_motor_extension", (p) -> new MotorExtensionBlock(p, NewAgeBlockEntityTypes.BASIC_MOTOR_EXTENSION, new BasicMotorExtensionVariant()))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(4.0f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<MotorExtensionBlock> ADVANCED_MOTOR_EXTENSION =
            REGISTRATE.block("advanced_motor_extension", (p) -> new MotorExtensionBlock(p, NewAgeBlockEntityTypes.ADVANCED_MOTOR_EXTENSION, new AdvancedMotorExtensionVariant()))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(4.0f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<CarbonBrushesBlock> CARBON_BRUSHES =
            REGISTRATE.block("carbon_brushes", CarbonBrushesBlock::new)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .addLayer(() -> RenderType::cutout)
                    .simpleItem()
                    .register();

    public static final BlockEntry<GeneratorCoilBlock> GENERATOR_COIL =
            REGISTRATE.block("generator_coil", GeneratorCoilBlock::new)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(BlockStressDefaults.setImpact(24.0f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<ImplementedMagnetBlock> MAGNETITE_BLOCK =
            REGISTRATE.block("magnetite_block", ImplementedMagnetBlock.simple(1))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .simpleItem()
                    .register();

    public static final BlockEntry<ImplementedMagnetBlock> REDSTONE_MAGNET =
            REGISTRATE.block("redstone_magnet", ImplementedMagnetBlock.simple(2))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .transform(b -> {
                        b.onRegister(CreateRegistrate.connectedTextures(() -> new SimpleCTBehaviour(NewAgeSpriteShifts.REDSTONE_MAGNET)));
                        return b;
                    })
                    .simpleItem()
                    .register();

    public static final BlockEntry<ImplementedMagnetBlock> LAYERED_MAGNET =
            REGISTRATE.block("layered_magnet", ImplementedMagnetBlock.simple(4))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .simpleItem()
                    .register();

    public static final BlockEntry<ImplementedMagnetBlock> FLUXUATED_MAGNETITE =
            REGISTRATE.block("fluxuated_magnetite", ImplementedMagnetBlock.simple(8))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .simpleItem()
                    .register();

    public static final BlockEntry<ImplementedMagnetBlock> NETHERITE_MAGNET =
            REGISTRATE.block("netherite_magnet", ImplementedMagnetBlock.simple(24))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .simpleItem()
                    .register();

    public static final BlockEntry<HeatPipeBlock> HEAT_PIPE =
            REGISTRATE.block("heat_pipe", HeatPipeBlock::new)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(properties -> properties.strength(1.6f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<HeatPumpBlock> HEAT_PUMP =
            REGISTRATE.block("heat_pump", HeatPumpBlock::new)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(properties -> properties.strength(1.6f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<HeaterBlock> HEATER =
            REGISTRATE.block("heater", HeaterBlock::new)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(2.0f))
                    .simpleItem()
                    .register();


    public static final BlockEntry<StirlingEngineBlock> STIRLING_ENGINE =
            REGISTRATE.block("stirling_engine", properties -> new StirlingEngineBlock(properties, NewAgeBlockEntityTypes.STIRLING_ENGINE))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(2.0f))
                    .transform(BlockStressDefaults.setCapacity(32.0))
                    .simpleItem()
                    .register();

    public static final BlockEntry<SolidCoriumBlock> SOLID_CORIUM =
            REGISTRATE.block("solid_corium", SolidCoriumBlock::new)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(properties -> properties.strength(50.0f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<CoriumBlock> CORIUM =
            REGISTRATE.block("corium", CoriumBlock::new)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(properties -> properties.strength(70.0f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<ReactorBlock> REACTOR_CASING =
            REGISTRATE.block("reactor_casing", ReactorBlock::new)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .transform(b -> {
                        b.onRegister(CreateRegistrate.connectedTextures(() -> new SimpleCTBehaviour(NewAgeSpriteShifts.REACTOR_CASING)));
                        return b;
                    })
                    .simpleItem()
                    .register();

    public static final BlockEntry<ReactorRodBlock> REACTOR_ROD =
            REGISTRATE.block("reactor_rod", ReactorRodBlock::new)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .simpleItem()
                    .register();

    public static final BlockEntry<ReactorTransparentBlock> REACTOR_GLASS =
            REGISTRATE.block("reactor_glass", ReactorTransparentBlock::new)
                    .initialProperties(()->Blocks.GLASS)
                    .properties(p -> p.isViewBlocking((blockState, blockGetter, blockPos) -> false))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(p -> p.isViewBlocking((a,b,c) -> false))
                    .transform(b -> {
                        b.onRegister(CreateRegistrate.connectedTextures(() -> new SimpleCTBehaviour(NewAgeSpriteShifts.REACTOR_GLASS)));
                        return b;
                    })
                    .addLayer(() -> RenderType::cutout)
                    .simpleItem()
                    .register();

    public static final BlockEntry<ReactorFuelAcceptorBlock> REACTOR_FUEL_ACCEPTOR =
            REGISTRATE.block("reactor_fuel_acceptor", ReactorFuelAcceptorBlock::new)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .simpleItem()
                    .register();


    public static final BlockEntry<ReactorHeatVentBlock> REACTOR_HEAT_VENT =
            REGISTRATE.block("reactor_heat_vent", ReactorHeatVentBlock::new)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .simpleItem()
                    .register();


    public static final BlockEntry<Block> BASIC_SOLAR_HEATING_PLATE =
            REGISTRATE.block("basic_solar_heating_plate", SolarHeatingPlateBlock::createBasic)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .addLayer(() -> RenderType::cutout)
                    .simpleItem()
                    .register();

    public static final BlockEntry<Block> ADVANCED_SOLAR_HEATING_PLATE =
            REGISTRATE.block("advanced_solar_heating_plate", SolarHeatingPlateBlock::createAdvanced)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .addLayer(() -> RenderType::cutout)
                    .simpleItem()
                    .register();


    public static final BlockEntry<Block> THORIUM_ORE =
            REGISTRATE.block("thorium_ore", Block::new)
                    .properties((p) -> p.strength(3.5f))
                    .item(AssemblyOperatorBlockItem::new)
                    .build()
                    .register();

    public static void load() {  }
}
