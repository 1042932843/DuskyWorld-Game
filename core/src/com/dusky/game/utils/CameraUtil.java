package com.dusky.game.utils;

import com.dusky.game.config.GameConfig;

public class CameraUtil {

    public static float convertUnitsToMeters(float pixels){
        return pixels / GameConfig.UNITS_PER_METER;
    }

    public static float convertMetersToUnits(float meters) {
        return meters * GameConfig.UNITS_PER_METER;
    }
}
