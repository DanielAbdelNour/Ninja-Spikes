package game;

import java.util.Set;
import java.util.TreeSet;

import screens.GameScreen;
import utils.EntityLists;
import utils.Utilities;
import actors.Floor;
import actors.LeftSpike;
import actors.LeftWall;
import actors.Player;
import actors.RightSpike;
import actors.RightWall;
import actors.Roof;

import com.badlogic.gdx.math.MathUtils;

public class Model {
	public int level = 2;
	int sp = (int) ((GameScreen.HEIGHT - 67) / 67);
	public boolean gameOver;

	public LeftWall leftWall;
	public RightWall rightWall;
	public Player player;
	public Floor floor;
	public Roof roof;
	public EntityLists el = new EntityLists();
	public int score;

	public Model() {
		leftWall = new LeftWall(this);
		rightWall = new RightWall(this);
		player = new Player(this);
		roof = new Roof(this);
		floor = new Floor(this);
	}

	public void update(float delta) {
		player.update(delta);
		roof.update();
		floor.update();

		updateSpikes();
		// LEFT SPIKES
		for (LeftSpike s : el.leftSpikeList) {
			s.update(delta);
			if (s.disposable) {
				el.leftSpikeList.removeValue(s, true);
			}
		}

		// /RIGHT SPIKES
		for (RightSpike s : el.rightSpikeList) {
			s.update(delta);
			if (s.disposable) {
				el.rightSpikeList.removeValue(s, true);
			}
		}

		if (player.dead && !gameOver) {
			if (Utilities.data.getInteger("obfus--") < score) {
				Utilities.data.putInteger("obfus--", score);
				Utilities.data.flush();
			}
			gameOver = true;
		}
		
		switch(score){
		case 5:
			level = 3;
			break;
		case 10: 
			level = 4;
			player.maxVel = 260;
			break;
		case 15:
			level = 4;
			break;
		case 20:
			level = 5;
			break;
		case 35:
			level = 6;
			player.maxVel = 290;
			break;
		case 50:
			level = 7;
			break;
		}

	}

	public void updateSpikes() {
		if (player.spikeReady) {
			Set<Integer> set = new TreeSet<Integer>();
			Integer[] arr;

			while (set.size() < level) {
				set.add(MathUtils.random(3, sp) * 67);
			}
			arr = set.toArray(new Integer[level]);

			if (player.startLeftSpikes) {
				for (int i = 0; i < level; i++) {
					el.leftSpikeList.add(new LeftSpike(this, arr[i]));
				}
			}
			if (player.startRightSpikes) {
				for (int i = 0; i < level; i++) {
					el.rightSpikeList.add(new RightSpike(this, arr[i]));
				}
			}

			player.spikeReady = false;
		}
	}
}
