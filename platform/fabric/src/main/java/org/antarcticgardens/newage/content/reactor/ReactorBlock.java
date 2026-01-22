package org.antarcticgardens.newage.content.reactor;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.world.level.block.Block;

public class ReactorBlock extends Block implements IWrenchable {
    public ReactorBlock(Properties properties) {
        super(properties.strength(6.0f));
    }
}
