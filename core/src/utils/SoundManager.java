package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	
	public static Sound point = Gdx.audio.newSound(Gdx.files.internal("sounds/point2.wav"));
	public static Sound jump = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
	public static Sound dead = Gdx.audio.newSound(Gdx.files.internal("sounds/dead.wav"));

}
