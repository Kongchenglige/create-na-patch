package org.antarcticgardens.newage.content.motors.extension.variants;

import org.antarcticgardens.newage.config.NewAgeConfig;

public class AdvancedMotorExtensionVariant implements IMotorExtensionVariant {
    @Override
    public float getMultiplier() {
        return NewAgeConfig.getCommon().advancedMotorExtensionMultiplier.get().floatValue();
    }

    @Override
    public long getExtraCapacity() {
        return NewAgeConfig.getCommon().advancedMotorExtensionExtraCapacity.get();
    }

    @Override
    public int getScrollStep() {
        return NewAgeConfig.getCommon().advancedMotorExtensionScrollStep.get();
    }
}
