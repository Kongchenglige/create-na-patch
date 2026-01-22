package org.antarcticgardens.newage.mixin;

import net.minecraft.world.level.Level;
import org.antarcticgardens.newage.content.electricity.network.ElectricalNetworkTicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Level.class)
public abstract class LevelMixin {
    @Shadow public abstract boolean isClientSide();

    @Inject(method = "tickBlockEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V"))
    private void tickBlockEntities(CallbackInfo ci) {
        if (!this.isClientSide())
            ElectricalNetworkTicker.tickWorld((Level) (Object) this);
    }
}
