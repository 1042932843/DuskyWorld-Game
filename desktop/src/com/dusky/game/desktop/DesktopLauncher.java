package com.dusky.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dusky.game.DuskyWorld;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = DuskyWorld.WIDTH;
		config.height = DuskyWorld.HEIGHT;
		config.title = DuskyWorld.TITLE;
		new LwjglApplication(new DuskyWorld(), config);
	}
}
