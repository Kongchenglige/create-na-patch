package org.antarcticgardens.newage.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public final ForgeConfigSpec.ConfigValue<Double> suToEnergy;
    public final ForgeConfigSpec.ConfigValue<Integer> maxRodsInDirection;
    public final ForgeConfigSpec.ConfigValue<Integer> maxCoils;
    public final ForgeConfigSpec.ConfigValue<Double> conductivityMultiplier;
    public final ForgeConfigSpec.ConfigValue<Integer> maxPathfindingDepth;
    public final ForgeConfigSpec.ConfigValue<Double> energiserSpeedMultiplier;
    public final ForgeConfigSpec.ConfigValue<Double> passiveHeatSourceMultiplier;
    public final ForgeConfigSpec.ConfigValue<Double> passivePipeHeatLoss;
    public final ForgeConfigSpec.ConfigValue<Double> heaterRequiredHeatMultiplier;
    public final ForgeConfigSpec.ConfigValue<Double> overheatingMultiplier;
    public final ForgeConfigSpec.ConfigValue<Double> nuclearReactorRodHeat;
    public final ForgeConfigSpec.ConfigValue<Double> nuclearReactorRodHeatLoss;
    public final ForgeConfigSpec.ConfigValue<Double> solarPanelHeatMultiplier;
    public final ForgeConfigSpec.ConfigValue<Integer> maxWireLength;

    public final ForgeConfigSpec.ConfigValue<Double> motorSUMultiplier;
    public final ForgeConfigSpec.ConfigValue<Integer> basicMotorCapacity;
    public final ForgeConfigSpec.ConfigValue<Double> basicMotorStress;
    public final ForgeConfigSpec.ConfigValue<Double> basicMotorSpeed;
    public final ForgeConfigSpec.ConfigValue<Integer> advancedMotorCapacity;
    public final ForgeConfigSpec.ConfigValue<Double> advancedMotorStress;
    public final ForgeConfigSpec.ConfigValue<Double> advancedMotorSpeed;
    public final ForgeConfigSpec.ConfigValue<Integer> reinforcedMotorCapacity;
    public final ForgeConfigSpec.ConfigValue<Double> reinforcedMotorStress;
    public final ForgeConfigSpec.ConfigValue<Double> reinforcedMotorSpeed;
    public final ForgeConfigSpec.ConfigValue<Double> basicMotorExtensionMultiplier;
    public final ForgeConfigSpec.ConfigValue<Integer> basicMotorExtensionExtraCapacity;
    public final ForgeConfigSpec.ConfigValue<Integer> basicMotorExtensionScrollStep;
    public final ForgeConfigSpec.ConfigValue<Double> advancedMotorExtensionMultiplier;
    public final ForgeConfigSpec.ConfigValue<Integer> advancedMotorExtensionExtraCapacity;
    public final ForgeConfigSpec.ConfigValue<Integer> advancedMotorExtensionScrollStep;


    public CommonConfig(ForgeConfigSpec.Builder builder) {
        suToEnergy = builder
                .comment(
                        "Responsible for how much energy is generated per 1 stress unit in a tick",
                        "Default value is supposed to be compatible with default configuration of Create: Crafts and Additions"
                ).defineInRange("suToEnergy", 0.029296875, 0, Double.MAX_VALUE);

        maxCoils = builder
                .comment("How many coils can the carbon brushes collect energy from")
                .defineInRange("maxCoils", 8, 0, Integer.MAX_VALUE);

        maxRodsInDirection = builder
                .comment("How many reactor rods can a fuel inserter or a heat vent have in a single direction")
                .defineInRange("maxRodsInDirection", 32, 0, Integer.MAX_VALUE);

        conductivityMultiplier = builder
                .comment("Multiplier of wire conductivity")
                .defineInRange("conductivityMultiplier", 1.0, 0, Double.MAX_VALUE);

        maxPathfindingDepth = builder
                .comment("Maximum depth of network pathfinding")
                .defineInRange("maxPathfindingDepth", 32, 1, Integer.MAX_VALUE);

        energiserSpeedMultiplier = builder
                .comment("Multiplier of energising speed")
                .defineInRange("energiserSpeedMultiplier", 1.0, 0, Double.MAX_VALUE);

        overheatingMultiplier = builder
                .comment("Multiplier for the temperature at which components overheat. Set to -1 to disable overheating.")
                .defineInRange("overheatingMultiplier", 1.0, -1, Double.MAX_VALUE);

        passiveHeatSourceMultiplier = builder
                .comment("Multiplier for how much heat pipes obtain from passive heat sources like Lava or even Boilers.")
                .defineInRange("passiveHeatSourceMultiplier", 1.0, 0, Double.MAX_VALUE);

        passivePipeHeatLoss = builder
                .comment("How much heat do pipes loose per second.")
                .defineInRange("passivePipeHeatLoss", 1.0, 0, Double.MAX_VALUE);

        heaterRequiredHeatMultiplier = builder
                .comment("Multiplier for how much heat a boiler needs.")
                .defineInRange("boilerRequiredHeatMultiplier", 1.0, 0, Double.MAX_VALUE);

        nuclearReactorRodHeat = builder
                .comment("How much heat per tick a nuclear reactor rod generate.")
                .defineInRange("ReactorRodHeat", 30.0, 0, Double.MAX_VALUE);

        nuclearReactorRodHeatLoss = builder
                .comment("How much heat per tick a nuclear reactor rod looses above the 16000*overheatingMultiplier.")
                .defineInRange("ReactorRodHeatLoss", 25.0, 0, Double.MAX_VALUE);

        solarPanelHeatMultiplier = builder
                .comment("Multiplier for how much heat solar panels output.")
                .defineInRange("solarPanelHeatMultiplier", 1.0, 0, Double.MAX_VALUE);

        maxWireLength = builder
                .comment("Maximum wire length")
                .defineInRange("maxWireLength", 16, 1, Integer.MAX_VALUE);

        builder.push("Motors");

        motorSUMultiplier = builder
                .comment("Maximum motor SU multiplier")
                .defineInRange("motorSuMultiplier", 1.0, 0.0, Double.MAX_VALUE);

        basicMotorCapacity = builder
            .comment("Internal energy capacity of a basic motor")
            .defineInRange("basicMotorCapacity", 16000, 1, Integer.MAX_VALUE);

        advancedMotorCapacity = builder
            .comment("Internal energy capacity of an advanced motor")
            .defineInRange("advancedMotorCapacity", 64000, 1, Integer.MAX_VALUE);

        reinforcedMotorCapacity = builder
            .comment("Internal energy capacity of a reinforced motor")
            .defineInRange("reinforcedMotorCapacity", 128000, 1, Integer.MAX_VALUE);

        basicMotorSpeed = builder
            .comment("Top Speed of a basic motor")
            .defineInRange("basicMotorSpeed", 128, 1, Double.MAX_VALUE);

        advancedMotorSpeed = builder
            .comment("Top Speed of an advanced motor")
            .defineInRange("advancedMotorSpeed", 256, 1, Double.MAX_VALUE);

        reinforcedMotorSpeed = builder
            .comment("Top Speed of a reinforced motor")
            .defineInRange("reinforcedMotorSpeed", 256, 1, Double.MAX_VALUE);

        basicMotorStress = builder
            .comment("Generated SU of a basic motor")
            .defineInRange("basicMotorStress", 512, 1, Double.MAX_VALUE);

        advancedMotorStress = builder
            .comment("Generated SU of an advanced motor")
            .defineInRange("advancedMotorStress", 2048, 1, Double.MAX_VALUE);

        reinforcedMotorStress = builder
            .comment("Generated SU of a reinforced motor")
            .defineInRange("reinforcedMotorStress", 8192, 1, Double.MAX_VALUE);

        builder.pop().push("Motor Extensions");

        basicMotorExtensionMultiplier = builder
            .comment("Power Multiplier of a basic motor extension")
            .defineInRange("basicMotorExtensionMultiplier", 2.0, 1.0, Double.MAX_VALUE);

        advancedMotorExtensionMultiplier = builder
            .comment("Power Multiplier of a basic motor extension")
            .defineInRange("advancedMotorExtensionMultiplier", 8.0, 1.0, Double.MAX_VALUE);

        basicMotorExtensionExtraCapacity = builder
            .comment("Extra energy capacity of a basic motor extension")
            .defineInRange("basicMotorExtensionExtraCapacity", 64000, 1, Integer.MAX_VALUE);

        advancedMotorExtensionExtraCapacity = builder
            .comment("Extra energy capacity of an advanced motor extension")
            .defineInRange("advancedMotorExtensionExtraCapacity", 256000, 1, Integer.MAX_VALUE);

        basicMotorExtensionScrollStep = builder
            .comment("Basic motor extension scroll step")
            .defineInRange("basicMotorExtensionScrollStep", 1, 1, Integer.MAX_VALUE);

        advancedMotorExtensionScrollStep = builder
            .comment("Advanced motor extension scroll step")
            .defineInRange("advancedMotorExtensionScrollStep", 8, 1, Integer.MAX_VALUE);
        
        builder.pop();
    }
}
