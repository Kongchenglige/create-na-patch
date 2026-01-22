package org.antarcticgardens.newage;

import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.createmod.catnip.config.ui.BaseConfigScreen;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CreateNewAgeClient {


    public static void onInitializeClient(final FMLClientSetupEvent event) {

        // ponders
        PonderIndex.addPlugin(new CreateNewAgePonders());


        // ToolTip
        addToolTipModifier(NewAgeBlocks.ENERGISER_T1.get());
        addToolTipModifier(NewAgeBlocks.ENERGISER_T2.get());
        addToolTipModifier(NewAgeBlocks.ENERGISER_T3.get());
        addToolTipModifier(NewAgeBlocks.STIRLING_ENGINE.get());
        addToolTipModifier(NewAgeBlocks.GENERATOR_COIL.get());

        ModContainer modContainer = ModList.get()
                .getModContainerById(CreateNewAge.MOD_ID)
                .orElseThrow(() -> new IllegalStateException("What the..."));

        modContainer.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (mc, previousScreen) -> new BaseConfigScreen(previousScreen, CreateNewAge.MOD_ID)));
    }

    public static void addToolTipModifier(Block entry) {
        TooltipModifier.REGISTRY.register(entry.asItem(), KineticStats.create(entry.asItem()));
    }
}
