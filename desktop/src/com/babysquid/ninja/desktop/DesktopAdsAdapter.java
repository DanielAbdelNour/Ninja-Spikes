package com.babysquid.ninja.desktop;

import utils.AdsInterface;

public class DesktopAdsAdapter implements AdsInterface{

	@Override
	public void loadAd() {
		System.out.println("loaded");
	}

	@Override
	public void showAd() {
		System.out.println("show");
		
	}

	@Override
	public void hideAd() {
		
	}

	@Override
	public void login() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitScore(int score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showLeaderboard() {
		// TODO Auto-generated method stub
		
	}

}
