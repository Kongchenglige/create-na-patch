package org.antarcticgardens.newage.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class NewAgeConfig {
    private static final NewAgeConfig INSTANCE = new NewAgeConfig();

    private final ClientConfig client;
    private final CommonConfig common;

    public NewAgeConfig() {
        var client = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        this.client = client.getLeft();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, client.getRight());

        var common = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        this.common = common.getLeft();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, common.getRight());
    }

    public static ClientConfig getClient() {
        return INSTANCE.client;
    }

    public static CommonConfig getCommon() {
        return INSTANCE.common;
    }
}
