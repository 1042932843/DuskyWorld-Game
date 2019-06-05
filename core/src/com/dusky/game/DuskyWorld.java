package com.dusky.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.dusky.game.helpers.AssetLoader;
import com.dusky.game.screens.SplashScreen;

public class DuskyWorld extends Game {
	public static final String TITLE = "DuskyWorld";
	public static final float MUSIC_VOLUME = 0.03f; //3%
	public static final float SFX_VOLUME = 0.5f; //50%

	private final AssetManager assetManager = new AssetManager();
	@Override
	public void create () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		AssetLoader.load();
		setScreen(new SplashScreen(this));
		initMusic();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
	}
	public AssetManager getAssetManager() {
		return assetManager;
	}
	private void initMusic() {

	}
	
	@Override
	public void dispose () {
		AssetLoader.dispose();
	}
}
