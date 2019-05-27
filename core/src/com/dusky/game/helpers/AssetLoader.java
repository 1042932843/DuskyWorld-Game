package com.dusky.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

  private static final String TEXTURE_LOGO = "logo/banner.jpg";
  public static Texture LOGO;
  private static final String TEXTURE_REGION_PLAYER = "player/animation_bird.png";
  public static Texture TEXTURE_PLAYER_ANIMATION;
  public static final int ANIMATION_PLAYER_FRAME_COUNT = 3;

  public static TextureRegion[] PLAYER;
    public static Animation<TextureRegion> PLAYER_ANIMATION;

  private AssetLoader(){
    // Empty
  }

  public static void load() {
    loadTextures();
    loadSounds();
    loadFonts();
  }

  private static void loadTextures() {
      LOGO = new Texture(Gdx.files.internal(TEXTURE_LOGO));
      TEXTURE_PLAYER_ANIMATION = new Texture(TEXTURE_REGION_PLAYER);
      TextureRegion textureRegion = new TextureRegion(TEXTURE_PLAYER_ANIMATION);
      int frameWidth = TEXTURE_PLAYER_ANIMATION.getWidth() / ANIMATION_PLAYER_FRAME_COUNT;
      // Create frames from the every single texture of the animation
      PLAYER = new TextureRegion[ANIMATION_PLAYER_FRAME_COUNT];
      for (int i = 0; i < ANIMATION_PLAYER_FRAME_COUNT; i++) {
          PLAYER[i] = new TextureRegion(textureRegion, i * frameWidth, 0, frameWidth, textureRegion.getRegionHeight());
      }
      PLAYER_ANIMATION = new Animation<TextureRegion>(0.06f, PLAYER);
      PLAYER_ANIMATION.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
  }

  private static void loadSounds() {

  }

  private static void loadFonts() {

  }



  public static void dispose() {
    LOGO.dispose();
  }
}
