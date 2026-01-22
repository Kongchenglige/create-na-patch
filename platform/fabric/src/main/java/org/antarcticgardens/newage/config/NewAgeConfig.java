package org.antarcticgardens.newage.config;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.antarcticgardens.newage.CreateNewAge;

public class NewAgeConfig {
    private static final NewAgeConfig INSTANCE = new NewAgeConfig();

    private final ClientConfig client;
    private final CommonConfig common;

    public NewAgeConfig() {
        var client = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        this.client = client.getLeft();
        ForgeConfigRegistry.INSTANCE.register(CreateNewAge.MOD_ID, ModConfig.Type.CLIENT, client.getRight());

        var common = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        this.common = common.getLeft();
        ForgeConfigRegistry.INSTANCE.register(CreateNewAge.MOD_ID, ModConfig.Type.COMMON, common.getRight());
    }

    public static ClientConfig getClient() {
        return INSTANCE.client;
    }

    public static CommonConfig getCommon() {
        return INSTANCE.common;
    }
}
