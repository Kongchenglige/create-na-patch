package org.antarcticgardens.newage.content.generation.magnets;

import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ImplementedMagnetBlock extends Block implements IMagneticBlock {
    private final int strength;

    public ImplementedMagnetBlock(Properties properties, int strength) {
        super(properties.strength(5.0F, 7.0F));
        this.strength = strength;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Lang.translate("tooltip.create_new_age.magnetic_force").style(ChatFormatting.GRAY).component());
        tooltip.add(Lang.text(" " + strength).style(ChatFormatting.AQUA)
                .component());
    }

    @Override
    public float getStrength() {
        return strength;
    }

    public static NonNullFunction<Properties, ImplementedMagnetBlock> simple(int level) {
        return (p) -> new ImplementedMagnetBlock(p, level);
    }

}
