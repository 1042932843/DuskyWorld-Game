package com.dusky.game.objects.weapon;

public class Weapon {
    private int x;//位置x
    private int y;//位置y
    private float rotation;//旋转角度
    private int power;//威力（伤害程度）
    private int speed;//速度（思必得）
    int mode;//模式（攻击模式）

    public float getMAX_STRENGTH() {
        return MAX_STRENGTH;
    }

    private float MAX_STRENGTH=20;//最大力度


    public Weapon(int power, int speed, int mode,float MAX_STRENGTH) {
        this.power = power;
        this.speed = speed;
        this.mode = mode;
        this.MAX_STRENGTH=MAX_STRENGTH;
    }

    public int getPower() {
        return power;
    }
}
