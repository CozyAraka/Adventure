package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.adventure.game.Main;
import de.adventure.game.audio.Audio;
import de.adventure.game.entities.player.Player;

import java.util.ArrayList;

public class MainPlayingScreen extends ScreenBase {
    protected final Game game;
    protected final Main main;
    private final Stage stage;
    private TextButton.TextButtonStyle tbStyle;
    private final BitmapFont font;
    private final Skin skin;
    private final Table /*tableMap,*/ tableInventory;
    //private final SpriteBatch batch;

    private int x, y, pixelTileSize;

    private float unitScale, mapWidth, mapHeight;

    //Map Rendering
    private TiledMap tiledMap;
    private TiledMap desert_map_1;

    private TiledMapTileLayer collisionLayer;
    private TiledMapTileLayer desertCollision;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera orthoCam;
    private FitViewport viewport;

    //Physics
    private Player player;
    private World world;
    private Body bodyPlayer;
    private BodyDef bodyDef;
    private PolygonShape shape;
    private CircleShape circleShape;
    private ArrayList<Body> bodies;

    private ShapeRenderer shapeRenderer;

    private Audio mainMusic;

    //Kreiert den Spielscreen
    public MainPlayingScreen(final Game game, final Main main) {
        super(game, main, "MainPlayingScreen");
        this.game = game;
        this.main = main;

        //Music
        mainMusic = new Audio("audio/mainMenuMusic.wav", 0.05F, true);

        //Map width & height (tiles)
        mapWidth = 80F;
        mapHeight = 60F;

        //TileSize
        unitScale = 1/16F;
        pixelTileSize = 16;

        //Positions des Spielers
        x = 0;
        y = 0;

        //Tiled Map
        tiledMap = new TmxMapLoader().load("map/mapFinal.tmx");

        //Collision Layer
        collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Solid");

        world = new World(new Vector2(0f, 0f), true);
        shape = new PolygonShape();
        circleShape = new CircleShape();
        player = main.getPlayer();

        bodyPlayer = player.createBody(world, circleShape, 0.5F, 30, 5);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        //Orthographic Cam
        orthoCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        orthoCam.setToOrtho(false, (float) Gdx.graphics.getWidth() / 80, (float) Gdx.graphics.getHeight() / 80);

        //Viewport
        //viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), orthoCam);

        //Map Renderer
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);

        stage = new Stage();
        font = new BitmapFont();
        skin = new Skin();

        /*
        tableMap = new Table();
        tableMap.setBounds(0, 0, 250, 250);
        tableMap.setX(25F);
        tableMap.setY(Gdx.graphics.getHeight() - 275);
         */

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

        //stage.addActor(tableMap);
        stage.addActor(tableInventory);

        //Setzt den generellen Input Processor zum stage Objekt (wird benutzt damit man überhaupt was machen kann)
        Gdx.input.setInputProcessor(stage);

    }

    //Generelle render Methode die nach dem switchen zu diesem Screen benutzt wird
    @Override
    public void render(float delta) {
        clearColorBuffer();

        //Clipping des Spielers (Man kann hinter blöcken stehen, je nachdem was als Erstes gerendert wird)
        int[] layerToRender1 = {0};
        int[] layerToRender2 = {1, 2};

        processInput();
        orthoCam.position.set(new Vector3(main.getPlayer().getXCord(), main.getPlayer().getYCord(), 0));
        orthoCam.update();
        mapRenderer.setView(orthoCam);

        mapRenderer.render(layerToRender1);

        debugRender(main.isDebug());

        mapRenderer.render(layerToRender2);

        //"Malt" alles auf den screen
        stage.draw();
        world.step(1/60f, 6, 2);
    }

    public void debugRender(boolean isDebug) {
        if(isDebug) {
            Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
            debugRenderer.setDrawBodies(true);
            debugRenderer.setDrawInactiveBodies(true);
            debugRenderer.setDrawContacts(true);
            debugRenderer.render(world, orthoCam.combined);
        }
    }

    public void processInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            game.setScreen(main.getMapScreen());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(main.getPauseScreen());
        }

        int xForce = 0;
        int yForce = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            yForce += 3;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xForce -= 3;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            yForce -= 3;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xForce += 3;
        }

        bodyPlayer.setLinearVelocity(yForce * 2, bodyPlayer.getLinearVelocity().y);
        bodyPlayer.setLinearVelocity(xForce * 2, bodyPlayer.getLinearVelocity().x);
        main.getPlayer().updatePosition(bodyPlayer.getPosition().x, bodyPlayer.getPosition().y);
    }

    public ArrayList<Body> createCollisionBoxes(TiledMapTileLayer tileLayer) {
        ArrayList<Body> bodies = new ArrayList<>();
        int index = 0;

        for(int row = 0; row < tileLayer.getHeight(); row++) {
            for (int column = 0; column < tileLayer.getWidth(); column++) {
                TiledMapTileLayer.Cell cell = tileLayer.getCell(column, row);
                if(cell == null) {
                    continue;
                }

                BodyDef bodyDefinition = new BodyDef();
                bodyDefinition.type = BodyDef.BodyType.StaticBody;
                bodyDefinition.position.set(column + 0.5F, row + 0.5F);
                bodyDefinition.angularDamping = 0F;
                System.out.println(bodyDefinition);

                shape.setAsBox(0.5F, 0.5F);

                FixtureDef fixtureDefinition = new FixtureDef();
                fixtureDefinition.shape = shape;
                fixtureDefinition.friction = 0F;
                fixtureDefinition.restitution = 0F;

                bodies.add(world.createBody(bodyDefinition));
                bodies.get(index).createFixture(fixtureDefinition);
                index++;

            }
        }
        return bodies;
    }

    //Wird ein einziges Mal aufgerufen, und zwar beim switchen zu diesem screen (ist wie die Methode create())
    @Override
    public void show() {
        mainMusic.play();
        Gdx.graphics.setTitle("Adventure");
        Gdx.input.setInputProcessor(stage);
        bodies = createCollisionBoxes(collisionLayer);
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
