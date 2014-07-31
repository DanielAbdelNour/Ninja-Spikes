package game;

import screens.GameScreen;
import utils.Utilities;
import actors.LeftSpike;
import actors.RightSpike;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class View {
	Model model;
	SpriteBatch batch;
	Stage stage;
	Sprite white = new Sprite(Utilities.atlas.findRegion("white"));
	public Sprite circle = new Sprite(Utilities.atlas.findRegion("circle"));
	BitmapFont font = new BitmapFont(Gdx.files.internal("font/font_big.fnt"));
	float wa = 1;

	public View(Model model) {
		this.model = model;
		font.setColor(new Color(0.2f, 0.2f, 0.2f, 0.4f));
		batch = new SpriteBatch();
		white.setSize(GameScreen.WIDTH, GameScreen.HEIGHT);
		circle.setPosition(GameScreen.WIDTH / 2 - circle.getWidth() / 2, ((GameScreen.HEIGHT / 2) + 50) - circle.getHeight() / 2);
	}

	public void render(float delta) {
		batch.setProjectionMatrix(GameScreen.cam.combined);

		batch.begin();
		circle.draw(batch);
		font.draw(batch, "" + model.score, circle.getX() + (circle.getOriginX()) - font.getBounds("" + model.score).width / 2,
				circle.getY() + (circle.getOriginY()) + (font.getBounds("" + model.score).height / 2));
		for (LeftSpike s : model.el.leftSpikeList) {
			s.render(batch);
		}
		for (RightSpike s : model.el.rightSpikeList) {
			s.render(batch);
		}

		model.leftWall.render(batch);
		model.rightWall.render(batch);
		model.player.render(batch);
		model.floor.render(batch);
		model.roof.render(batch);

		if (model.player.dead) {
			white.draw(batch);
			white.setAlpha(wa);
			wa *= 0.95f;
		}
		
		batch.end();
	}

	public void update(float delta) {

	}
}
