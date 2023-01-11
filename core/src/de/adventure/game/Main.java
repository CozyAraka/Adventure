package de.adventure.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.adventure.game.items.ItemCollector;
import de.adventure.game.screens.MainMenu;
import de.adventure.game.screens.MainScreen;

public class Main extends Game {
	//LibGDX
	private BitmapFont font;
	private SpriteBatch batch;
	private FitViewport viewport;
	private Game game;

	//Items
	private ItemCollector collector;

	//Screens
	private MainMenu mainMenu;
	private MainScreen mainScreen;

	public enum GameState {
		RUNNING,
		PAUSED,
		LOADING
	}
	private static GameState gameState;

	@Override
	public void create () {
		gameState = GameState.LOADING;
		System.out.println("Loading...\n");

		game = this;
		font = new BitmapFont();
		batch = new SpriteBatch();
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		collector = new ItemCollector();
		collector.loadedItems();

		setMenuScreen();

		System.out.println("Done loading!\n");

		gameState = GameState.RUNNING;
		ScreenUtils.clear(0, 0, 0, 1);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		super.dispose();
		System.exit(0);
	}

	private void setMenuScreen() {
		game.setScreen(new MainMenu(this));
	}

	public static GameState getGameState() {
		return gameState;
	}

	public static void setGameState(GameState state) {
		switch(state) {
			case RUNNING:
				gameState = GameState.RUNNING;
				break;

			case PAUSED:
				gameState = GameState.PAUSED;
				break;

			case LOADING:
				gameState = GameState.LOADING;
				break;

			default:
				gameState = GameState.RUNNING;
				break;
		}
	}
}
