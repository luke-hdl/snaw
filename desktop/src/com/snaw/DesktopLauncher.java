package com.snaw;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(820, 500);
		config.setForegroundFPS(60);
		config.setTitle("SNAW Test Game");
		new Lwjgl3Application(new NightGame(), config);
	}
}
