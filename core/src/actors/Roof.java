package actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import screens.GameScreen;
import utils.Utilities;
import game.Model;

public class Roof {
	Model model;
	Sprite sprite = new Sprite(Utilities.atlas.findRegion("spikes_blue"));
	Vector2 pos = new Vector2();
	public Rectangle bounds = new Rectangle();

	public Roof(Model model) {
		this.sprite.flip(false, true);
		this.pos.set(-10, GameScreen.HEIGHT - sprite.getHeight() + 105);
	}
	
	public void render(SpriteBatch batch){
		getRoofSprite().draw(batch);
	}
	
	public void update(){
		
	}

	public Sprite getRoofSprite() {
		sprite.setPosition(pos.x, pos.y);
		bounds.set(pos.x, pos.y + 50, sprite.getWidth(), sprite.getHeight());
		return sprite;
	}

}
