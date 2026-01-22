package org.antarcticgardens.newage.compat.modmenu;

import com.simibubi.create.foundation.config.ui.BaseConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screens.Screen;
import org.antarcticgardens.newage.CreateNewAge;

public class NewAgeModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (ConfigScreenFactory<Screen>) screen -> new BaseConfigScreen(screen, CreateNewAge.MOD_ID);
    }
}
