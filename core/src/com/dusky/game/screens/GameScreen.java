package com.dusky.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dusky.game.DuskyWorld;
import com.dusky.game.config.GameConfig;
import com.dusky.game.helpers.AssetLoader;
import com.dusky.game.objects.Player;
import com.dusky.game.objects.weapon.Weapon;

import java.util.ArrayList;

import static com.dusky.game.objects.weapon.Mode.MODE_LONG_RANGE_STRAIGHT;


public class GameScreen implements Screen {

  private static final String TAG = "GameScreen";

  private Stage stage;
  private World world;
  private Vector2 velocity,acceleration;
  private static final float GRAVITY = -100f;

  private ArrayList<Player> teamA=new ArrayList<Player>();
  private ArrayList<Player> teamB=new ArrayList<Player>();


  private DuskyWorld game;
  private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
  private OrthographicCamera camera;

  GameScreen(DuskyWorld duskyWorld) {
    game = duskyWorld;
  }

  /**
   * 遍历玩家列表，加入舞台
   */
  private void initPlayer(){
      for(Player player:teamA){
        stage.addActor(player);
      }
      for(Player player:teamB){
        stage.addActor(player);
      }
  }

  private void initFPS(){
    Label.LabelStyle labelStyle =new Label.LabelStyle(new BitmapFont(), Color.RED);//创建一个Label样式，使用默认黑色字体
    Label label =new Label("FPS:", labelStyle);//创建标签，显示的文字是FPS：
    label.setName("fpsLabel");//设置标签名称为fpsLabel
    label.setY(0);//设置Y为0，即显示在最上面
    label.setX(0);//设置X值，显示在屏幕最左侧
    stage.addActor(label);//将标签添加到舞台
  }

  @Override public void render(float delta) {
    world.step(1/60f, 6, 2);
    Gdx.gl.glClearColor(0, 0, 0, 0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Label label = (Label) stage.getRoot().findActor("fpsLabel");//获取名为fpsLabel的标签
    label.setText("FPS:" + Gdx.graphics.getFramesPerSecond());
    // 更新舞台逻辑
    stage.act();
    // 绘制舞台
    stage.draw();
    camera.update();
    if (GameConfig.debug) {
      debugRenderer.render(world, camera.combined);
    }
  }

  @Override public void resize(int width, int height) {
    Gdx.app.log(TAG, "resize");
  }

  @Override public void show() {
    Gdx.app.log(TAG, "show");
    acceleration = new Vector2(0, GRAVITY);
    world = new World(acceleration, true);
    //使用伸展视口创建舞台
    stage = new Stage(new StretchViewport(DuskyWorld.WIDTH, DuskyWorld.HEIGHT));
    camera = new OrthographicCamera(DuskyWorld.WIDTH, DuskyWorld.HEIGHT);
    createStaticBody();
    Player player=new Player("Dusky",100,new Weapon(1,1,MODE_LONG_RANGE_STRAIGHT), AssetLoader.PLAYER_ANIMATION,world);
    teamA.add(player);
    camera.position.set(teamA.get(0).getX(), teamA.get(0).getY(), 0);
    camera.update();
    initPlayer();
    initFPS();
  }

  @Override public void hide() {
    Gdx.app.log(TAG, "hide");
  }

  @Override public void pause() {
    Gdx.app.log(TAG, "pause");
  }

  @Override public void resume() {
    Gdx.app.log(TAG, "resume");
  }

  @Override public void dispose() {
    // 场景被销毁时释放资源
    if (stage != null) {
      stage.dispose();
    }
    if(debugRenderer!=null){
      debugRenderer.dispose();
    }
    if(world!=null){
      world.dispose();
    }
  }

  private void createStaticBody()
  {
    // Create our body definition
    BodyDef groundBodyDef = new BodyDef();
    // Set its world position
    groundBodyDef.position.set(new Vector2(0, 20));

    // Create a body from the defintion and add it to the world
    Body groundBody = world.createBody(groundBodyDef);

    // Create a polygon shape
    PolygonShape groundBox = new PolygonShape();
    // Set the polygon shape as a box which is twice the size of our view
    // port and 4 high
    // (setAsBox takes half-width and half-height as arguments)
    groundBox.setAsBox(800, 2.0f);
    // Create a fixture from our polygon shape and add it to our ground body
    groundBody.createFixture(groundBox, 0.0f);
    // Clean up after ourselves
    groundBox.dispose();
  }
}
