package org.antarcticgardens.newage;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class NewAgeTags {
    public static final TagKey<Item> MAGNET_TAG = new TagKey<>(Registries.ITEM, new ResourceLocation(CreateNewAge.MOD_ID, "magnet"));

    public static final TagKey<Block> CUSTOM_MAGNET_TAG = new TagKey<>(Registries.BLOCK, new ResourceLocation(CreateNewAge.MOD_ID, "custom_magnet"));


}
