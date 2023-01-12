package de.adventure.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.adventure.game.entities.EntityCollector;
import de.adventure.game.items.ItemCollector;
import de.adventure.game.screens.CharacterCreation;
import de.adventure.game.screens.MainMenu;

public class Main extends Game {
	//LibGDX
	private BitmapFont font;
	private SpriteBatch batch;
	private FitViewport viewport;
	private Game game;

	//Items
	private ItemCollector itemCollector;

	//Entities
	private EntityCollector entityCollector;

	//Screens
	private MainMenu mainMenu;
	private CharacterCreation characterCreation;

	public enum GameState {
		RUNNING,
		PAUSED,
		LOADING
	}
	private static GameState gameState;

	@Override
	public void create () {
		setGameState(GameState.LOADING);

		//GDX
		game = this;
		font = new BitmapFont();
		batch = new SpriteBatch();
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		/*batch.begin();
		font.draw(batch, "Loading...", (float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
		batch.end();*/

		//Items
		itemCollector = new ItemCollector();
		itemCollector.registerItems();

		//Entities
		entityCollector = new EntityCollector();
		entityCollector.registerEntities();

		//Screens
		mainMenu = new MainMenu(this, this);
		characterCreation = new CharacterCreation(this, this);

		setGameState(GameState.RUNNING);
		ScreenUtils.clear(0, 0, 0, 1);
		game.setScreen(getMainMenu());
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

	public MainMenu getMainMenu() {
		return mainMenu;
	}

	public CharacterCreation getCharacterCreation() {
		return characterCreation;
	}

	public static GameState getGameState() {
		return gameState;
	}

	public static void setGameState(GameState state) {
		switch(state) {
			case RUNNING:
				gameState = GameState.RUNNING;
				System.out.println("Game running\n");
				break;

			case PAUSED:
				gameState = GameState.PAUSED;
				System.out.println("Game paused!\n");
				break;

			case LOADING:
				gameState = GameState.LOADING;
				System.out.println("Loading...\n");
				break;

			default:
				gameState = GameState.RUNNING;
				break;
		}
	}
}
