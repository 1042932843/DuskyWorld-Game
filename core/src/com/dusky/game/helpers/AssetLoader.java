package com.dusky.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {

  private static final String TEXTURE_LOGO = "logo/banner.jpg";
  public static Texture LOGO;

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
  }

  private static void loadSounds() {

  }

  private static void loadFonts() {

  }



  public static void dispose() {
    LOGO.dispose();
  }
}
