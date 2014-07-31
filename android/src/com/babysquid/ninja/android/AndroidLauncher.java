package com.babysquid.ninja.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.babysquid.ninja.NinjaSpikes;
import com.babysquid.ninja.android.GameHelper.GameHelperListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;

public class AndroidLauncher extends AndroidApplication {
	InterstitialAd interstitial = new InterstitialAd(this);
	AdRequest adRequest = new AdRequest.Builder().build();
	AndroidAdsAdapter adsAdapter = new AndroidAdsAdapter(this);
	GameHelper gameHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		interstitial.setAdUnitId("ca-app-pub-1175283916040967/4726894737");
		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);

		GameHelperListener listener = new GameHelper.GameHelperListener() {
			@Override
			public void onSignInSucceeded() {
				System.out.println("signin success");
			}

			@Override
			public void onSignInFailed() {
				System.out.println("signin failed");
			}

		};
		gameHelper.setup(listener);
		gameHelper.enableDebugLog(true);

		initialize(new NinjaSpikes(adsAdapter), config);
	}

	@Override
	protected void onStart() {
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		gameHelper.onStop();
	}

	public void loadAd() {
		@SuppressWarnings("unused")
		Runnable r = new Runnable() {
			@Override
			public void run() {
				interstitial.loadAd(adRequest);
			}
		};
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	public void showAd() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					if (interstitial.isLoaded()) {
						interstitial.show();
					} else {
						AdRequest interstitialRequest = new AdRequest.Builder().build();
						interstitial.loadAd(interstitialRequest);
					}
				}
			});
		} catch (Exception e) {
		}
	}

	public void signIn() {
		if (!gameHelper.isConnecting()) {
			gameHelper.beginUserInitiatedSignIn();
		}
	}

	public void getLeaderboard() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {

					if (gameHelper.isSignedIn())
						startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(gameHelper.getApiClient()), 5001);
					else
						gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (Exception e) {
		}
	}

	public void submitScore(int score) {
		if (gameHelper.isSignedIn())
			Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_high_scores), score);
	}
}
