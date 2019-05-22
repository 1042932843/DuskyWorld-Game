package com.dusky.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.dusky.game.helpers.AssetLoader;
import com.dusky.game.screens.SplashScreen;

public class DuskyWorld extends Game {
	public static final String TITLE = "DuskyWorld";
	public static final float MUSIC_VOLUME = 0.03f; //3%
	public static final float SFX_VOLUME = 0.5f; //50%
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	
	@Override
	public void create () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		AssetLoader.load();
		setScreen(new SplashScreen(this));
		initMusic();
	}

	private void initMusic() {

	}
	
	@Override
	public void dispose () {
		AssetLoader.dispose();
	}
}
