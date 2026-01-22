package org.antarcticgardens.newage.content.electricity.wire;

import net.minecraft.world.item.ItemStack;
import org.antarcticgardens.newage.NewAgeItems;
import org.antarcticgardens.newage.config.NewAgeConfig;

public enum WireType {
    COPPER(1024, new int[] { 158, 88, 75, 255 }, new int[] { 173, 108, 92, 255 }, NewAgeItems.COPPER_WIRE::asStack),
    IRON(2048, new int[] { 177, 179, 181, 255 }, new int[] { 164, 165, 166, 255 }, NewAgeItems.IRON_WIRE::asStack),
    GOLD(4096, new int[] { 186, 155, 74, 255 }, new int[] { 171, 141, 63, 255 }, NewAgeItems.GOLDEN_WIRE::asStack),
    DIAMOND(8192, new int[] { 86, 145, 138, 255 }, new int[] { 77, 127, 120, 255 }, NewAgeItems.DIAMOND_WIRE::asStack);

    private final long conductivity;
    private final int[] color1;
    private final int[] color2;
    private final IRegistrateIsAFuckingShitNeverUseIt dropProvider;

    WireType(int conductivity, int[] color1, int[] color2, IRegistrateIsAFuckingShitNeverUseIt dropProvider) {
        this.conductivity = conductivity;
        this.color1 = color1;
        this.color2 = color2;
        this.dropProvider = dropProvider;
    }

    public long getConductivity() {
        return (long) (conductivity * NewAgeConfig.getCommon().conductivityMultiplier.get());
    }

    public int[] getColor1() {
        return color1.clone();
    }

    public int[] getColor2() {
        return color2.clone();
    }

    public ItemStack getDroppedItem() {
        return dropProvider.getDroppedItem();
    }
}
