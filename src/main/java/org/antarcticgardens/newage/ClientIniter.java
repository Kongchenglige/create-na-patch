package org.antarcticgardens.newage;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientIniter {
    public static void onInitializeClient(final FMLClientSetupEvent event) {
        CreateNewAgeClient.onInitializeClient(event);
    }
}
