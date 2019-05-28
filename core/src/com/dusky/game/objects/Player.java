package com.dusky.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dusky.game.DuskyWorld;
import com.dusky.game.objects.weapon.Weapon;

public class Player extends Actor {
    private static final int VELOCITY_LIMIT = -345;
    private Body body;

    private int health;//角色当前血量
    private int maxHealth;//角色最大血量

    private int total_Damages;//一次游戏的伤害总量

    private Weapon weapon;//持有武器

    private TextureRegion region;
    private Animation<TextureRegion> animation;

    private float runTime;

    public Player(String name,int maxHealth, Weapon weapon, Animation<TextureRegion> animation,World world) {
        this.health=maxHealth;//初始时maxHealth=health
        this.maxHealth = maxHealth;
        this.weapon = weapon;//装载武器
        this.animation = animation;
        this.region = animation.getKeyFrame(0);
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
        setName(name);
        setPosition(DuskyWorld.WIDTH/2,DuskyWorld.HEIGHT/2);
        body= createBox2dBody(world);

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

    private Body createBox2dBody(World world){
        //创建body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX(), getY());// 设置这个body初始位置
        Body body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(getWidth()/2);//半径

        //创建Fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1f;//密度
        fixtureDef.friction = 1f;//摩檫力
        fixtureDef.restitution = 0.6f; // 弹力，弹走鱼尾纹
        body.createFixture(fixtureDef);//会返回一个Fixture，用不上
        circle.dispose();
        //body.setUserData(this);//绑定此actor（我的设计不需要，draw方法中,player自己管理自己的位置）
        return body;
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
        if(body!=null){
            setPosition(body.getPosition().x,body.getPosition().y);
        }
        batch.draw(
                animation.getKeyFrame(runTime),
                getX()-getWidth()/2, getY()-getHeight()/2,
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );

    }

}
