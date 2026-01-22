package org.antarcticgardens.newage.content.motors.variants;

import org.antarcticgardens.newage.config.NewAgeConfig;

public class AdvancedMotorVariant implements IMotorVariant {
    @Override
    public long getMaxCapacity() {
        return NewAgeConfig.getCommon().advancedMotorCapacity.get();
    }

    @Override
    public float getSpeed() {
        return NewAgeConfig.getCommon().advancedMotorSpeed.get().floatValue();
    }

    @Override
    public float getStress() {
        return NewAgeConfig.getCommon().advancedMotorStress.get().floatValue();
    }
}
