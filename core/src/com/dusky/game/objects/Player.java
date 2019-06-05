package com.dusky.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dusky.game.config.GameConfig;
import com.dusky.game.objects.weapon.Weapon;
import com.dusky.game.utils.CalculationUtil;
import com.dusky.game.utils.CameraUtil;

import static com.dusky.game.utils.CameraUtil.convertMetersToUnits;
import static com.dusky.game.utils.CameraUtil.convertUnitsToMeters;

public class Player extends Actor {
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
        setPosition(100,200);
        body= createBox2dBody(world);
        //body= createBullet(world);
        body.setUserData(this);
    }

    public Body getBody() {
        return body;
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
        //创建body形状
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(CameraUtil.convertUnitsToMeters(getWidth())/2);

        //创建body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(CameraUtil.convertUnitsToMeters(100), CameraUtil.convertUnitsToMeters(200)));// 设置这个body初始位置
        Body body = world.createBody(bodyDef);

        //创建Fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 1f;//密度
        fixtureDef.friction = 1f;//摩檫力
        fixtureDef.restitution = 0.6f; // 弹力，弹走鱼尾纹
        body.createFixture(fixtureDef);//会返回一个Fixture，用不上
        circleShape.dispose();
        //body.setUserData(this);//绑定此actor（我的设计不需要，draw方法中,player自己管理自己的位置）
        return body;
    }

    /*private Body createBullet(World world) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.5f);
        circleShape.setPosition(new Vector2(CameraUtil.convertUnitsToMeters(getX()), CameraUtil.convertUnitsToMeters
                (getY())));
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        Body bullet = world.createBody(bd);
        bullet.createFixture(circleShape, 1);
        circleShape.dispose();
        float velX = Math.abs( (weapon.getMAX_STRENGTH() * -MathUtils.cos(CalculationUtil.angleBetweenTwoPoints()) * (distance / 100f)));
        float velY = Math.abs( (weapon.getMAX_STRENGTH() * -MathUtils.sin(angle) * (distance / 100f)));
        bullet.setLinearVelocity(velX, velY);
        return bullet;
    }*/

    @Override
    public void act(float delta) {
        super.act(delta);
        runTime += delta;
        if(body!=null){
            setPosition(CameraUtil.convertMetersToUnits(body.getPosition().x-CameraUtil.convertUnitsToMeters(getWidth())/2), CameraUtil.convertMetersToUnits(body.getPosition().y)-CameraUtil.convertUnitsToMeters(getWidth())/2);
        }
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
