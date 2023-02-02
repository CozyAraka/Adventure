package de.adventure.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;
import de.adventure.game.entities.EntityCollector;
import de.adventure.game.entities.player.Player;
import de.adventure.game.inventory.InventoryScreen;
import de.adventure.game.items.ItemCollector;
import de.adventure.game.savemanager.FileHandler;
import de.adventure.game.savemanager.SaveManager;
import de.adventure.game.screens.*;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Game {
	//Debug
	private final boolean debug;

	//LibGDX
	private BitmapFont font;
	private SpriteBatch batch;
	private static Game game;

	//Items
	private ItemCollector itemCollector;

	//Entities
	private EntityCollector entityCollector;

	//Screens
	private static MainMenuScreen mainMenuScreen;
	private static CharacterCreationScreen characterCreationScreen;
	private static MainPlayingScreen mainPlayingScreen;
	private static MapScreen mapScreen;
	private static PauseScreen pauseScreen;
	private static InventoryScreen inventoryScreen;

	//Player
	private Player player;

	//Stuff
	public static final Logger logger = new Logger("Adventure");
	public static final AssetManager assets = new AssetManager();
	public static final Random random = new Random();
	public static SpriteBatch spriteBatch;
	public static ModelBatch modelBatch;
	private static Stage stage;

	private FileHandler fileHandler;
	private SaveManager saveManager;

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
		if(debug) {
			Gdx.app.setLogLevel(Application.LOG_DEBUG);
		}else {
			Gdx.app.setLogLevel(Application.LOG_ERROR);
		}

		Main.assets.load("skins/uiskin.json", Skin.class);
		Main.assets.load("icons/icons.atlas", TextureAtlas.class);
		Main.assets.finishLoading();
		Gdx.graphics.setVSync(false);
		stage = new Stage();

		//Player
		player = new Player("", 0F, 0F, 0);

		//GDX
		game = this;
		font = new BitmapFont();
		batch = new SpriteBatch();

		//Items
		itemCollector = new ItemCollector();
		itemCollector.registerItems();

		//Entities
		entityCollector = new EntityCollector();
		entityCollector.registerEntities();

		setGameState(GameState.RUNNING);

		//Working save manager!
		ArrayList<String> str = new ArrayList<>();
		str.add("cordsX:56.5");
		str.add("cordsY:20.5");
		str.add("facing:left");
		str.add("hp:100.0");
		str.add("mp:100.0");
		str.add("money:20.45");

		saveManager = new SaveManager("save1.adv", this);
		//saveManager.saveGame(str);
		saveManager.loadSave();

		//Screens
		mainMenuScreen = new MainMenuScreen(this, this);
		characterCreationScreen = new CharacterCreationScreen(this, this);
		mainPlayingScreen = new MainPlayingScreen(this, this);
		mapScreen = new MapScreen(this, this);
		pauseScreen = new PauseScreen(this, this);
		inventoryScreen = new InventoryScreen();

		game.setScreen(getMainMenu());
	}

	public SaveManager getSaveManager() {
		return saveManager;
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

	public static Stage getStage() {
		return stage;
	}

	public MainMenuScreen getMainMenu() {
		return mainMenuScreen;
	}

	public CharacterCreationScreen getCharacterCreation() {
		return characterCreationScreen;
	}

	public static MainPlayingScreen getMainPlayingScreen() {
		return mainPlayingScreen;
	}

	public MapScreen getMapScreen() {
		return mapScreen;
	}

	public PauseScreen getPauseScreen() {
		return pauseScreen;
	}

	public static InventoryScreen getInventoryScreen() {
		return inventoryScreen;
	}

	public static void setGameScreen(Screen screen) {
		game.setScreen(screen);
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
