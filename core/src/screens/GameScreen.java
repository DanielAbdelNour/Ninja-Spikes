package screens;

import game.Model;
import game.View;
import utils.Utilities;
import com.babysquid.ninja.NinjaSpikes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GameScreen implements Screen {
	public static float WIDTH;
	public static float HEIGHT;

	public static float timeScale = 1f;
	Model model;
	View view;
	Stage stage;
	public static OrthographicCamera cam;

	boolean begin;
	Image playImage;
	Label label;
	Image leaderBoards;
	int highScore;
	NinjaSpikes game;
	boolean lb;
	InputListener leaderBoardlistener;

	public GameScreen(NinjaSpikes game) {
		this.game = game;
		game.adsInterface.loadAd();
		game.adsInterface.showAd();
	}

	@Override
	public void show() {
		highScore = Utilities.data.getInteger("obfus--");
		stage = new Stage(new ExtendViewport(480, 854));
		cam = (OrthographicCamera) stage.getCamera();
		WIDTH = cam.viewportWidth;
		HEIGHT = cam.viewportHeight;

		model = new Model();
		view = new View(model);
		setStage();
	}

	public void setStage() {
		playImage = new Image(Utilities.atlas.findRegion("start"));
		playImage.setPosition(WIDTH / 2 - playImage.getWidth() / 2, HEIGHT / 2 + 200);
		playImage.setOrigin(playImage.getWidth() / 2, playImage.getHeight() / 2);
		playImage.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(1.1f, 1.1f, 1f, Interpolation.linear), Actions.scaleTo(1f, 1f, 1f, Interpolation.linear))));

		BitmapFont font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
		label = new Label("HIGH SCORE: " + highScore, new LabelStyle(font, new Color(0.4f, 0.4f, 0.4f, 0.7f)));
		label.setPosition(WIDTH / 2 - label.getWidth() / 2, 10);
		
		leaderBoards = new Image(Utilities.atlas.findRegion("leaderboard"));
		leaderBoards.setPosition(WIDTH/4f - leaderBoards.getWidth()/2, 420);
		leaderBoards.setOrigin(leaderBoards.getWidth()/2, leaderBoards.getHeight()/2);
		leaderBoards.addAction(Actions.forever(Actions.sequence(Actions.rotateTo(-5, 1f, Interpolation.fade), Actions.rotateTo(5, 1f, Interpolation.fade))));
		
		leaderBoardlistener = new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				lb = true;
				game.adsInterface.showLeaderboard();
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				lb = false;
				super.touchUp(event, x, y, pointer, button);
			}
		};
		
		leaderBoards.addListener(leaderBoardlistener);

		stage.addActor(playImage);
		stage.addActor(label);
		stage.addActor(leaderBoards);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0.92f, 0.98f, 1f, 0.24f);
		delta = Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f) * timeScale;
		
		if (Gdx.input.justTouched() && !begin && !lb) {
			begin = true;
			playImage.addAction(Actions.fadeOut(0.3f));
			label.addAction(Actions.fadeOut(0.3f));
			leaderBoards.addAction(Actions.fadeOut(0.3f));
			leaderBoards.removeListener(leaderBoardlistener);
		}
				
		if (delta > 0 && begin) {
			model.update(delta);
			view.update(delta);
		}

		view.render(delta);

		if (model.player.dead && model.player.da < 0.1f && Gdx.input.justTouched()) {
			game.adsInterface.submitScore(model.score);
			game.setScreen(new GameScreen(game));
		}

		updateStage();
	}

	public void updateStage() {
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
