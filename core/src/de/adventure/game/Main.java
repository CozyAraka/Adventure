package de.adventure.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.adventure.game.entities.EntityCollector;
import de.adventure.game.entities.player.Player;
import de.adventure.game.items.ItemCollector;
import de.adventure.game.screens.*;

public class Main extends Game {
	//Debug
	private boolean debug;

	//LibGDX
	private BitmapFont font;
	private SpriteBatch batch;
	private Game game;

	//Items
	private ItemCollector itemCollector;

	//Entities
	private EntityCollector entityCollector;

	//Screens
	private MainMenuScreen mainMenuScreen;
	private CharacterCreationScreen characterCreationScreen;
	private MainPlayingScreen mainPlayingScreen;
	private MapScreen mapScreen;
	private PauseScreen pauseScreen;

	//Player
	private Player player;

	public enum GameState {
		RUNNING,
		PAUSED,
		LOADING
	}
	private static GameState gameState;

	public Main(boolean debug) {
		this.debug = debug;
	}

	@Override
	public void create() {
		setGameState(GameState.LOADING);
		Gdx.graphics.setVSync(true);

		//Player
		player = new Player("", 0F, 0F, 0);

		//GDX
		game = this;
		font = new BitmapFont();
		batch = new SpriteBatch();

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
		mainMenuScreen = new MainMenuScreen(this, this);
		characterCreationScreen = new CharacterCreationScreen(this, this);
		mainPlayingScreen = new MainPlayingScreen(this, this);
		mapScreen = new MapScreen(this, this);
		pauseScreen = new PauseScreen(this, this);

		setGameState(GameState.RUNNING);
		//ScreenUtils.clear(0, 0, 0, 1);
		game.setScreen(getMainMenu());
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		super.dispose();
		System.exit(0);
	}

	public MainMenuScreen getMainMenu() {
		return mainMenuScreen;
	}

	public CharacterCreationScreen getCharacterCreation() {
		return characterCreationScreen;
	}

	public MainPlayingScreen getMainPlayingScreen() {
		return mainPlayingScreen;
	}

	public MapScreen getMapScreen() {
		return mapScreen;
	}

	public PauseScreen getPauseScreen() {
		return pauseScreen;
	}

	public GameState getGameState() {
		return gameState;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isDebug() {
		return debug;
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
