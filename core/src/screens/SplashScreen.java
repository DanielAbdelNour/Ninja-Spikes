package screens;

import utils.Utilities;

import com.babysquid.ninja.NinjaSpikes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class SplashScreen implements Screen{
	Stage s = new Stage(new ExtendViewport(480, 854));
	Image im = new Image(Utilities.atlas.findRegion("splash"));
	NinjaSpikes game;
	
	public SplashScreen(NinjaSpikes game){
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		s.draw();
		s.act();
	}

	@Override
	public void resize(int width, int height) {
		s.getViewport().update(width, height);
	}

	@Override
	public void show() {
		OrthographicCamera cam = (OrthographicCamera) s.getCamera();
		float WIDTH = cam.viewportWidth;
		float HEIGHT = cam.viewportHeight;
		im.setSize(WIDTH, HEIGHT);
		//im.setPosition(0, 0);
		im.addAction(Actions.sequence(Actions.delay(3), Actions.fadeOut(0.8f), Actions.run(new Runnable(){
			@Override
			public void run() {
				game.setScreen(new GameScreen(game));
			}
			
		})));
		s.addActor(im);
		
		System.out.println(im.getWidth());
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
