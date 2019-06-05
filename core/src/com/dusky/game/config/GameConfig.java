package com.dusky.game.config;


public class GameConfig {
    public static final int WORLD_WIDTH = 960;
    public static final int WORLD_HEIGHT = 544;
    public static final boolean debug = true;

    public static final float UNITS_PER_METER = 32F;
    public static final float UNIT_WIDTH = WORLD_WIDTH / UNITS_PER_METER;
    public static final float UNIT_HEIGHT = WORLD_HEIGHT / UNITS_PER_METER;
}
