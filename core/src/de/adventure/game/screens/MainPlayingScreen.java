package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import de.adventure.game.Main;
import de.adventure.game.audio.Audio;

public class MainPlayingScreen extends ScreenBase implements Screen {
    protected final Game game;
    protected final Main main;
    private final Stage stage;
    private TextButton.TextButtonStyle tbStyle;
    private final BitmapFont font;
    private final Skin skin;
    private final Table table, tableMap, tableInventory;

    private int x, y;

    //Map Rendering
    private TiledMap tiledMap;
    private TiledMapTileLayer tileLayer;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera orthoCam;

    private ShapeRenderer shapeRenderer;

    private Audio mainMusic;

    //Kreiert das MainMenu
    public MainPlayingScreen(final Game game, final Main main) {
        super(game, main, "MainPlayingScreen");
        this.game = game;
        this.main = main;

        //Music
        mainMusic = new Audio("audio/mainMenuMusic.wav", 0.15F, true);

        //Positions des Spielers
        x = 960;
        y = 252;

        //Tiled Map
        tiledMap = new TmxMapLoader().load("map/uwu.tmx");
        //tiledMap.getLayers().get(0).getProperties();

        //TODO Collision:
        //tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
        //tileLayer.getCell(1,1);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        //Orthographic Cam
        orthoCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        orthoCam.translate(120*2, 168*2);

        //Map Renderer
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 4);

        stage = new Stage();
        font = new BitmapFont();
        skin = new Skin();

        //Table ist praktisch eine Grid zum Platzieren von Objekten, wie ein Regal
        table = new Table();
        table.setBounds(1, 1, Gdx.graphics.getWidth() - 1, Gdx.graphics.getHeight() - 1);

        tableMap = new Table();
        tableMap.setBounds(0, 0, 250, 250);
        tableMap.setX(25F);
        tableMap.setY(Gdx.graphics.getHeight() - 275);

        tableInventory = new Table();
        tableInventory.setBounds(0, 0, 50, 400);
        tableInventory.setX(25F);
        tableInventory.setY(Gdx.graphics.getHeight() - 800);

        tbStyle = new TextButton.TextButtonStyle();
        tbStyle.font = font;

        //Debug code
        if(main.isDebug()) {
            stage.setDebugAll(true);
        }

        stage.addActor(table);
        stage.addActor(tableMap);
        stage.addActor(tableInventory);

        //Setzt den generellen Input Processor zum stage Objekt (wird benutzt damit man überhaupt was machen kann)
        Gdx.input.setInputProcessor(stage);

    }

    //Generelle render Methode die nach dem switchen zu diesem Screen benutzt wird
    @Override
    public void render(float delta) {
        clearColorBuffer();

        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            game.setScreen(main.getMapScreen());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(main.getPauseScreen());
        }

        int[] layerToRender1 = {0};
        int[] layerToRender2 = {1, 2};

        orthoCam.update();
        mapRenderer.setView(orthoCam);
        mapRenderer.render(layerToRender1);

        if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            y += 48;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            x -= 48;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            y -= 48;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            x += 48;
        }

        shapeRenderer.begin();
        main.getPlayer().updatePosition(x, y);
        main.getPlayer().draw(shapeRenderer);
        //shapeRenderer.rect(x, y, 12*4, 12*4);
        shapeRenderer.end();

        mapRenderer.render(layerToRender2);

        //"Malt" alles auf den screen
        stage.draw();
    }

    //Wird ein einziges Mal aufgerufen, und zwar beim switchen zu diesem screen (ist wie die Methode create())
    @Override
    public void show() {
        mainMusic.play();
        Gdx.graphics.setTitle("Adventure");
        Gdx.input.setInputProcessor(stage);
    }

    //Wird aufgerufen, wenn zu einem anderen Screen geswitcht wird
    @Override
    public void hide() {
        //mainMusic.stop();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose () {
        skin.dispose();
        font.dispose();
        stage.dispose();
    }
}
