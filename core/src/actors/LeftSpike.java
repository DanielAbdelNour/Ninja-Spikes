package actors;

import game.Model;
import screens.GameScreen;
import utils.Utilities;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class LeftSpike {
	public static int spikePositions;
	public boolean disposable;

	Sprite sprite = new Sprite(Utilities.atlas.findRegion("left_spike_blue"));
	Vector2 pos = new Vector2();
	Vector2 vel = new Vector2(200, 0);
	Model model;
	
	public float[] verts =  new float[6];
	public Polygon bounds;
	
	public LeftSpike(Model model, float randY) {
		this.model = model;
		spikePositions = (int) (GameScreen.HEIGHT / sprite.getHeight());

		pos.set(-sprite.getWidth(), randY);
		
		verts[0] = 0;
		verts[1] = 0;
		
		verts[2] = sprite.getWidth();
		verts[3] = sprite.getHeight()/2;
		
		verts[4] = 0;
		verts[5] = sprite.getHeight();
				
		bounds = new Polygon(verts);
		
		bounds.setOrigin(pos.x, pos.y);
	}

	public void update(float delta) {
		move(delta);

		if (model.player.rightSide && pos.x < 0) {
			vel.x = 200;
		} else if (!model.player.rightSide) {
			vel.x = -200;
		} else {
			vel.x = 0;
		}

		if (!model.player.rightSide && pos.x < -sprite.getWidth()) {
			disposable = true;
		}
		
		if(overlaps(bounds, model.player.circle)){
			model.player.dead = true;
			//model.player.vel.x = -model.player.vel.x;
		}
	}

	public void move(float delta) {
		vel.scl(delta);
		pos.add(vel);
		vel.scl(1 / delta);
		
		bounds.setPosition(pos.x, pos.y);
	}

	public void render(SpriteBatch batch) {
		getSpikeSprite().draw(batch);
				
	}

	public Sprite getSpikeSprite() {
		sprite.setPosition(pos.x, pos.y);
		return sprite;
	}
	
	public boolean overlaps(Polygon polygon, Circle circle) {
	    float []vertices=polygon.getTransformedVertices();
	    Vector2 center=new Vector2(circle.x, circle.y);
	    float squareRadius=circle.radius*circle.radius;
	    for (int i=0;i<vertices.length;i+=2){
	        if (i==0){
	            if (Intersector.intersectSegmentCircle(new Vector2(vertices[vertices.length-2], vertices[vertices.length-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
	                return true;
	        } else {
	            if (Intersector.intersectSegmentCircle(new Vector2(vertices[i-2], vertices[i-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
	                return true;
	        }
	    }
	    return false;
	}

}
