package actors;

import game.Model;
import utils.Utilities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Floor {
	Model model;
	Sprite sprite = new Sprite(Utilities.atlas.findRegion("spikes_blue"));
	Vector2 pos = new Vector2(-10, -5);
	public Rectangle bounds =  new Rectangle();

	public Floor(Model model) {
		this.model = model;
	}
	
	public void render(SpriteBatch batch){
		getFloorSprite().draw(batch);
	}
	
	public void update(){
		
	}

	public Sprite getFloorSprite() {
		sprite.setPosition(pos.x, pos.y);
		bounds.set(pos.x, pos.y, sprite.getWidth(), sprite.getHeight() - 50);
		return sprite;
	}

}
