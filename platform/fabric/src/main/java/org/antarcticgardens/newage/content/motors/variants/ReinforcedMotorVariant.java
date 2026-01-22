package org.antarcticgardens.newage.content.motors.variants;

import org.antarcticgardens.newage.config.NewAgeConfig;

public class ReinforcedMotorVariant implements IMotorVariant {
    @Override
    public long getMaxCapacity() {
        return NewAgeConfig.getCommon().reinforcedMotorCapacity.get();
    }

    @Override
    public float getSpeed() {
        return NewAgeConfig.getCommon().reinforcedMotorSpeed.get().floatValue();
    }

    @Override
    public float getStress() {
        return NewAgeConfig.getCommon().reinforcedMotorStress.get().floatValue();
    }
}
