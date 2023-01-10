package de.adventure.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {
	private final Game game = this;

	@Override
	public void create () {
		ScreenUtils.clear(0, 0, 0, 1);
	}

	@Override
	public void render () {
		pauseKeyCheck();
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			dispose();
		}
	}

	@Override
	public void pause() {
		Gdx.graphics.setForegroundFPS(30);
	}

	@Override
	public void resume() {
		Gdx.graphics.setForegroundFPS(240);
	}

	@Override
	public void dispose () {
		System.exit(0);
	}

	private boolean check = false;

	public void pauseKeyCheck() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.P) && !check) {
			System.out.println("1");
			check = true;
			pause();

		}else if(Gdx.input.isKeyJustPressed(Input.Keys.P) && check) {
			System.out.println("2");
			check = false;
			resume();
		}
	}

	public Game getGame() {
		return game;
	}
}
