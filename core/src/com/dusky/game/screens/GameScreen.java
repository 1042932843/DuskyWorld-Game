package com.dusky.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dusky.game.DuskyWorld;
import com.dusky.game.helpers.AssetLoader;
import com.dusky.game.objects.Player;
import com.dusky.game.objects.weapon.Weapon;

import java.util.ArrayList;

import static com.dusky.game.objects.weapon.Mode.MODE_LONG_RANGE_STRAIGHT;


public class GameScreen implements Screen {

  private static final String TAG = "GameScreen";

  private Stage stage;

  private ArrayList<Player> teamA=new ArrayList<Player>();
  private ArrayList<Player> teamB=new ArrayList<Player>();


  private DuskyWorld game;

  GameScreen(DuskyWorld duskyWorld) {
    game = duskyWorld;
    Gdx.app.log("GameScreen", "attached");
    //使用伸展视口创建舞台
    stage = new Stage(new StretchViewport(DuskyWorld.WIDTH, DuskyWorld.HEIGHT));

    Player player=new Player("Dusky",100,new Weapon(1,1,MODE_LONG_RANGE_STRAIGHT), AssetLoader.PLAYER_ANIMATION);
    player.setPosition(DuskyWorld.WIDTH/2,DuskyWorld.HEIGHT/2);
    teamA.add(player);
    initPlayer();
    initFPS();
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
    Label label = (Label) stage.getRoot().findActor("fpsLabel");//获取名为fpsLabel的标签
    label.setText("FPS:" + Gdx.graphics.getFramesPerSecond());
    label.setY(0);
    label.setX(0);
    // 更新舞台逻辑
    stage.act();
    // 绘制舞台
    stage.draw();

  }

  @Override public void resize(int width, int height) {
    Gdx.app.log(TAG, "resize");
  }

  @Override public void show() {
    Gdx.app.log(TAG, "show");
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
  }
}
