package actors;

import game.Model;
import screens.GameScreen;
import utils.Utilities;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class LeftWall {
	Model model;
	Sprite sprite = new Sprite(Utilities.atlas.findRegion("wall_blue"));
	Vector2 pos = new Vector2(0, 0);
	Rectangle bounds = new Rectangle();

	public LeftWall(Model model) {
		this.model = model;
	}

	public void render(SpriteBatch batch) {
		getWallSprite().draw(batch);
	}

	public Sprite getWallSprite() {
		sprite.setSize(14, GameScreen.HEIGHT);
		sprite.setPosition(pos.x, pos.y);
		return sprite;
	}

	public Rectangle getBounds() {
		bounds.set(pos.x, pos.y, sprite.getWidth(), sprite.getHeight());
		return bounds;
	}

}
