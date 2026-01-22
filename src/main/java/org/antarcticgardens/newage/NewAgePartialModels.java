package org.antarcticgardens.newage;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;

public class NewAgePartialModels {

    public static final PartialModel GENERATOR_COIL = PartialModel.of(
            new ResourceLocation(CreateNewAge.MOD_ID, "block/generation/generator_coil"));

    public static void load() {  }
}
