package com.dusky.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dusky.game.accessors.SpriteAccessor;
import com.dusky.game.DuskyWorld;
import com.dusky.game.helpers.AssetLoader;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class SplashScreen implements Screen {

  private static final String TAG = SplashScreen.class.getSimpleName();
  private static final float SCALE = 0.618f;

  private TweenManager tweenManager;
  private SpriteBatch spriteBatch;
  private Sprite sprite;
  private DuskyWorld game;

  public SplashScreen(DuskyWorld duskyWorld) {
    game = duskyWorld;
  }

  @Override public void show() {
    spriteBatch = new SpriteBatch();
    sprite = new Sprite(AssetLoader.LOGO);
    sprite.setColor(1, 1, 1, 0);

      //设置sprite大小
    float width = Gdx.graphics.getWidth();
    float height = Gdx.graphics.getHeight();
    float scale = width * SCALE / sprite.getWidth();
    sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);

    //设置sprite位置
    float positionX = width / 2 - sprite.getWidth() / 2;
    float positionY = height * SCALE-sprite.getHeight()/2;
    sprite.setPosition(positionX, positionY);

    setupTween();
  }

  private void setupTween() {
    Tween.registerAccessor(Sprite.class, new SpriteAccessor());
    tweenManager = new TweenManager();

    TweenCallback cb = new TweenCallback() {
      @Override public void onEvent(int type, BaseTween<?> source) {
        //动画回调

      }
    };

    Tween.to(sprite, SpriteAccessor.ALPHA, 2f)//2s的alpha渐变效果，实现splash效果，展示LOGO
        .target(1)
        .ease(TweenEquations.easeInOutQuad)
        .repeatYoyo(1, 0.4f)
        .setCallback(cb)
        .setCallbackTriggers(TweenCallback.COMPLETE)
        .start(tweenManager);
  }

  @Override public void render(float delta) {
    tweenManager.update(delta);
    Gdx.gl.glClearColor(0, 0, 0, 0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    spriteBatch.begin();
    sprite.draw(spriteBatch);
    spriteBatch.end();
  }

  @Override public void resize(int width, int height) {
    Gdx.app.log(TAG, "resize");
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
    spriteBatch.dispose();
  }
}
