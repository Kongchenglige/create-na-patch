package org.antarcticgardens.newage;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import net.minecraft.resources.ResourceLocation;

import static com.simibubi.create.foundation.block.connected.CTSpriteShifter.getCT;

public class NewAgeSpriteShifts {
    public static CTSpriteShiftEntry REACTOR_CASING = omni("reactor/casing");
    public static CTSpriteShiftEntry REACTOR_GLASS = omni("reactor/glass");
    public static CTSpriteShiftEntry REDSTONE_MAGNET = omni("redstone_magnet");

    private static CTSpriteShiftEntry omni(String name) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, new ResourceLocation(CreateNewAge.MOD_ID, "block/" + name), new ResourceLocation(CreateNewAge.MOD_ID, "block/" + name + "_connected"));
    }



}
