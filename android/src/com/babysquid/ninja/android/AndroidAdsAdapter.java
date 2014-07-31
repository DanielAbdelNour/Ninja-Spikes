package com.babysquid.ninja.android;

import utils.AdsInterface;

public class AndroidAdsAdapter implements AdsInterface {
	AndroidLauncher context;
	
	public AndroidAdsAdapter(AndroidLauncher context){
		this.context = context;
	}

	@Override
	public void loadAd() {
		System.out.println("loading ad");
		context.loadAd();
	}

	@Override
	public void showAd() {
		System.out.println("showing ad");
		context.showAd();
	}

	@Override
	public void hideAd() {
		
	}

	@Override
	public void login() {
		
	}

	@Override
	public void submitScore(int score) {
		System.out.println("score submitted: " + score);
		context.submitScore(score);
	}

	@Override
	public void showLeaderboard() {
		context.getLeaderboard();
	}

}
