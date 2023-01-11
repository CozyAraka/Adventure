package de.adventure.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {
	private BitmapFont font;
	private SpriteBatch batch;

	public enum GameState {
		RUNNING,
		PAUSED,
		LOADING
	}
	private static GameState gameState;

	@Override
	public void create () {
		font = new BitmapFont();
		batch = new SpriteBatch();

		gameState = GameState.RUNNING;
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
	public void dispose () {
		batch.dispose();
		font.dispose();
		System.exit(0);
	}

	private boolean check = false;

	public void pauseKeyCheck() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.P) && !check) {
			check = true;
			pause();

		}else if(Gdx.input.isKeyJustPressed(Input.Keys.P) && check) {
			check = false;
			resume();
		}
	}

	public static GameState getGameState() {
		return gameState;
	}

	public static void setGameState(GameState state) {
		switch(state) {
			case RUNNING:
				break;

			case PAUSED:
				break;

			case LOADING:
				break;

			default:
				gameState = GameState.RUNNING;
				break;
		}
	}
}
