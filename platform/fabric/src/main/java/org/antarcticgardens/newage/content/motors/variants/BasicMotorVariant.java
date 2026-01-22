package org.antarcticgardens.newage.content.motors.variants;

import org.antarcticgardens.newage.config.NewAgeConfig;

public class BasicMotorVariant implements IMotorVariant {
    @Override
    public long getMaxCapacity() {
        return NewAgeConfig.getCommon().basicMotorCapacity.get();
    }

    @Override
    public float getSpeed() {
        return NewAgeConfig.getCommon().basicMotorSpeed.get().floatValue();
    }

    @Override
    public float getStress() {
        return NewAgeConfig.getCommon().basicMotorStress.get().floatValue();
    }
}
