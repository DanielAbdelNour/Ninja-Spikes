package actors;

import game.Model;
import screens.GameScreen;
import utils.SoundManager;
import utils.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player {
	float gravity = -900;
	Model model;

	Sprite sprite = new Sprite(Utilities.atlas.findRegion("ninja70"));
	float width = sprite.getWidth(), height = sprite.getHeight();
	public Rectangle bounds = new Rectangle(sprite.getBoundingRectangle());

	Vector2 pos = new Vector2((GameScreen.WIDTH / 2) - width / 2, ((GameScreen.HEIGHT / 2) + 50) - height / 2);
	Vector2 vel = new Vector2(230, 0);
	public float maxVel = 230;

	public boolean startRightSpikes;
	public boolean startLeftSpikes;
	float sx = 0.4f;

	Array<Particle> particleList = new Array<Particle>();
	float particleDelay = 0.125f;
	boolean doParticles;
	int particleCount;
	int particleTotal;

	public boolean dead;

	public boolean rightSide;
	public boolean leftSide;
	public boolean spikeReady;

	public Circle circle = new Circle();

	float ds = 1;
	public float da = 1;

	boolean doDead;

	public Player(Model model) {
		this.model = model;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	public void render(SpriteBatch batch) {
		for (Particle p : particleList) {
			p.getParticleSprite().draw(batch);
		}

		getNinjaSprite().draw(batch);
	}

	public void update(float delta) {
		move(delta);

		if (!dead) {
			checkTouch();
			checkBounds();
		}
		updateParticles(delta);

		for (Particle p : particleList) {
			p.update(delta);
			if (p.a < 0.1f) {
				particleList.removeValue(p, true);
				particleCount--;
			}
		}

		if (dead) {
			doDead();
		}
	}

	public void doDead() {

		gravity = 0;

		if (!doDead) {
			SoundManager.dead.play();
			// vel.x = -vel.x;
			if (vel.x > 0)
				maxVel = -700;
			else
				maxVel = 700;
			doDead = true;
		}

		// sprite.setRegion(Utilities.atlas.findRegion("ninja_dead70"));
		sprite.rotate(10);
		if (bounds.overlaps(model.floor.bounds) || bounds.overlaps(model.roof.bounds)) {
			vel.y = -vel.y;
			if (vel.x > 0) {
				maxVel = 700;
			} else {
				maxVel = -700;
			}
		}
		if (bounds.overlaps(model.rightWall.getBounds())) {
			maxVel = -700;
			if (vel.y < 0)
				vel.y = -700;
			else
				vel.y = 700;
		}
		if (bounds.overlaps(model.leftWall.getBounds())) {
			maxVel = 700;
			if (vel.y < 0)
				vel.y = -700;
			else
				vel.y = 700;
		}

	}

	public void updateParticles(float delta) {
		if (doParticles && particleTotal > 0) {
			particleDelay += delta;
			if (particleDelay > 0.125f) {
				particleList.add(new Particle(sprite.getBoundingRectangle().getCenter(new Vector2())));
				particleTotal--;
				particleDelay = 0;
			}
		} else {
			doParticles = false;
			particleDelay = 0.125f;
		}
	}

	public void checkTouch() {
		if (Gdx.input.justTouched()) {
			vel.y = 450;
			if (!doParticles) {
				doParticles = true;
				particleTotal += 5;
			}
			SoundManager.jump.play();
		}

	}

	public void checkBounds() {
		if (bounds.overlaps(model.rightWall.getBounds())) {
			pos.x = model.rightWall.getBounds().x - width;
			// vel.x = -vel.x;
			maxVel = -maxVel;
			startLeftSpikes = true;
			sx = 0.55f;

			rightSide = true;
			leftSide = false;
			spikeReady = true;

			if (!dead)
				model.score++;

			SoundManager.point.play();
		}

		if (bounds.overlaps(model.leftWall.getBounds())) {
			pos.x = model.leftWall.getBounds().x + model.leftWall.getBounds().width;
			// vel.x = -vel.x;
			maxVel = -maxVel;
			startRightSpikes = true;
			sx = 0.55f;

			leftSide = true;
			rightSide = false;
			spikeReady = true;

			if (!dead)
				model.score++;

			SoundManager.point.play();
		}

		if ((bounds.overlaps(model.roof.bounds) || bounds.overlaps(model.floor.bounds)) && !dead) {
			dead = true;
		}

	}

	public void move(float delta) {
		vel.y += gravity * delta;
		vel.x = maxVel;

		vel.scl(delta);
		pos.add(vel);
		vel.scl(1 / delta);

		bounds.setPosition(pos);
		circle.set(sprite.getBoundingRectangle().getCenter(new Vector2()), sprite.getWidth() / 2.2f);
	}

	public Sprite getNinjaSprite() {
		if (!dead) {
			if (!sprite.isFlipX() && vel.x < 0) {
				sprite.flip(true, false);
			}
			if (sprite.isFlipX() && vel.x > 0) {
				sprite.flip(true, false);
			}
			sprite.setOriginCenter();

			if (vel.x > 0) {
				sprite.setRotation(MathUtils.clamp(vel.y * 0.04f, -10, 15));
			} else {
				sprite.setRotation(MathUtils.clamp(-vel.y * 0.04f, -10, 15));
			}

			if (sx < 1) {
				sx += 0.01f;
				float s = sx / 1;
				sprite.setScale(Interpolation.bounce.apply(s), 1);
			}
		} else {
			da *= 0.95f;
			ds *= 0.99f;
			sprite.setAlpha(da);
			sprite.setScale(ds);
		}

		sprite.setSize(width, height);
		sprite.setPosition(pos.x, pos.y);
		return sprite;
	}

	public class Particle {
		final float MAX_LIFE = 0.25f;
		Sprite pSprite = new Sprite(Utilities.atlas.findRegion("particle"));
		Vector2 pPos;
		float particleLife;
		boolean fade;
		float a = 1;
		float s = 1;

		public Particle(Vector2 pPos) {
			this.pPos = new Vector2(pPos.x - (pSprite.getWidth() / 2), pPos.y - (pSprite.getHeight() / 2));
			pSprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			particleCount++;
		}

		public void update(float delta) {
			particleLife += delta;

			if (particleLife >= MAX_LIFE) {
				fade = true;
			}
		}

		public Sprite getParticleSprite() {
			if (fade) {
				a *= 0.76f;
				pSprite.setColor(1, 1, 1, a);

				s *= 0.93f;
				pSprite.setScale(a);
			}

			pSprite.setOriginCenter();
			pSprite.setPosition(pPos.x, pPos.y);
			return pSprite;
		}
	}
}
