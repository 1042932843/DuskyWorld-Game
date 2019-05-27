package com.dusky.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dusky.game.objects.weapon.Weapon;

public class Player extends Actor {

    private int health;//角色当前血量
    private int maxHealth;//角色最大血量

    private int total_Damages;//一次游戏的伤害总量

    private Weapon weapon;//持有武器

    private TextureRegion region;
    private Animation<TextureRegion> animation;

    private float runTime;

    public Player(String name,int maxHealth, Weapon weapon, Animation<TextureRegion> animation) {
        this.health=maxHealth;//初始时maxHealth=health
        this.maxHealth = maxHealth;
        this.weapon = weapon;//装载武器
        this.animation = animation;
        this.region = animation.getKeyFrame(0);
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
        setName(name);
    }

    public TextureRegion getRegion() {
        return region;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
    }


    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * 受到伤害或者治疗
     * @param point
     */
    public void changeHealth(int point) {
        if (health > 0) {
            if (health+point < 0) {
                health = 0;//生命下限是0点
            }else if(health+point>=maxHealth){
                health = maxHealth;//生命上限是maxHealth点
            } else {
                this.health = health + point;
            }
        }
    }

    public void changeTotalDamage(){
        total_Damages=total_Damages+weapon.getPower();//伤害总和=前总和+此次武器伤害
    }

    public int getTotal_Damages() {
        return total_Damages;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        runTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (region == null || !isVisible()) {
            return;
        }

        batch.draw(
                animation.getKeyFrame(runTime),
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );
    }

}
