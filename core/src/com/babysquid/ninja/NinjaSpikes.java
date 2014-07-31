package com.babysquid.ninja;

import screens.GameScreen;
import screens.SplashScreen;
import utils.AdsInterface;

import com.badlogic.gdx.Game;

public class NinjaSpikes extends Game {
	GameScreen gameScreen;
	SplashScreen splashScreen;
	public AdsInterface adsInterface;

	public NinjaSpikes(AdsInterface adsInterface) {
		this.adsInterface = adsInterface;
	}

	@Override
	public void create() {
		splashScreen = new SplashScreen(this);
		//gameScreen = new GameScreen(this);
		this.setScreen(splashScreen);
	}

}
