package com.babysquid.ninja.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.babysquid.ninja.NinjaSpikes;

public class DesktopLauncher {
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		DesktopAdsAdapter adsAdapter =  new DesktopAdsAdapter();
		
		TexturePacker.process("bin/images", "bin/images/output", "atlas");
		
		config.width = 480;
		config.height = 800;
		
		new LwjglApplication(new NinjaSpikes(adsAdapter), config);
	}
}
