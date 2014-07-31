package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Utilities {
	public static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/output/atlas.atlas"));
	public static Preferences data = Gdx.app.getPreferences("NUM#");
}
