package com.dusky.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dusky.game.DuskyWorld;
import com.dusky.game.control.PlayerControl;
import com.dusky.game.helpers.AssetLoader;
import com.dusky.game.objects.Player;
import com.dusky.game.objects.weapon.Weapon;
import com.dusky.game.test.TiledObjectBodyBuilder;

import java.util.ArrayList;

import static com.dusky.game.config.GameConfig.UNIT_HEIGHT;
import static com.dusky.game.config.GameConfig.UNIT_WIDTH;
import static com.dusky.game.config.GameConfig.WORLD_HEIGHT;
import static com.dusky.game.config.GameConfig.WORLD_WIDTH;
import static com.dusky.game.objects.weapon.Mode.MODE_LONG_RANGE_STRAIGHT;

public class GameScreen extends ScreenAdapter {


    private World world;
    private Stage stage;
    private Box2DDebugRenderer debugRenderer;
    private Viewport viewport;
    private static final float GRAVITY = -10;
    private SpriteBatch batch;
    private DuskyWorld game;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private OrthographicCamera box2dCam,camera;
    private Array<Body> toRemove = new Array<Body>();
    private ArrayList<Player> teamA=new ArrayList<Player>();
    private ArrayList<Player> teamB=new ArrayList<Player>();

    public GameScreen(DuskyWorld game) {
        this.game = game;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
    }

    /**
     * 遍历玩家列表，加入舞台
     */
    private void initPlayer(){
        PlayerControl playerControl=new PlayerControl(teamA.get(0),viewport);
        Gdx.input.setInputProcessor(playerControl);

        for(Player player:teamA){
            stage.addActor(player);
        }
        for(Player player:teamB){
            stage.addActor(player);
        }
    }
    /**
     * 初始化fps模块
     */
    private void initFPS(){
        Label.LabelStyle labelStyle =new Label.LabelStyle(new BitmapFont(), Color.RED);//创建一个Label样式，使用默认黑色字体
        Label label =new Label("FPS:", labelStyle);//创建标签，显示的文字是FPS：
        label.setName("fpsLabel");//设置标签名称为fpsLabel
        label.setY(0);//设置Y为0，即显示在最上面
        label.setX(0);//设置X值，显示在屏幕最左侧
        stage.addActor(label);//将标签添加到舞台
    }
    private void updateFPS() {
        Label label = (Label) stage.getRoot().findActor("fpsLabel");//获取名为fpsLabel的标签
        label.setText("FPS:" + Gdx.graphics.getFramesPerSecond());
    }

    @Override
    public void show() {
        super.show();
        world = new World(new Vector2(0, GRAVITY), true);
        //使用视口创建舞台
        stage = new Stage();
        //debugRenderer
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);
        stage.setViewport(viewport);
        batch = new SpriteBatch();
        tiledMap = game.getAssetManager().get("nuttybirds.tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);
        orthogonalTiledMapRenderer.setView(camera);

        TiledObjectBodyBuilder.buildBuildingBodies(tiledMap, world);
        box2dCam = new OrthographicCamera(UNIT_WIDTH, UNIT_HEIGHT);
        TiledObjectBodyBuilder.buildFloorBodies(tiledMap, world);
        TiledObjectBodyBuilder.buildBirdBodies(tiledMap, world);

        Player player=new Player("Dusky",100,new Weapon(1,1,MODE_LONG_RANGE_STRAIGHT,20), AssetLoader.PLAYER_ANIMATION,world);
        teamA.add(player);
        initFPS();
        initPlayer();
    }



    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        clearScreen();
        draw();
        drawDebug();
    }

    private void draw(){
        orthogonalTiledMapRenderer.render();
        // 更新舞台逻辑
        stage.act();
        // 绘制舞台
        stage.draw();
    }

    private void drawDebug() {
        debugRenderer.render(world, box2dCam.combined);
        updateFPS();
    }


    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void update(float delta) {
        clearDeadBodies();
        world.step(delta, 6, 2);
        box2dCam.position.set(UNIT_WIDTH / 2, UNIT_HEIGHT / 2, 0);
        box2dCam.update();
    }


    private void clearDeadBodies() {
        for (Body body : toRemove) {
            world.destroyBody(body);
        }
        toRemove.clear();
    }

}
