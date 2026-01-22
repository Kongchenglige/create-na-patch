package org.antarcticgardens.newage.content.motors.extension.variants;

import org.antarcticgardens.newage.config.NewAgeConfig;

public class BasicMotorExtensionVariant implements IMotorExtensionVariant {
    @Override
    public float getMultiplier() {
        return NewAgeConfig.getCommon().basicMotorExtensionMultiplier.get().floatValue();
    }

    @Override
    public long getExtraCapacity() {
        return NewAgeConfig.getCommon().basicMotorExtensionExtraCapacity.get();
    }

    @Override
    public int getScrollStep() {
        return NewAgeConfig.getCommon().basicMotorExtensionScrollStep.get();
    }
}
