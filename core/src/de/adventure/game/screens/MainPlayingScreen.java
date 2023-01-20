package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.adventure.game.Main;
import de.adventure.game.audio.Audio;
import de.adventure.game.entities.player.Player;
import de.adventure.game.entities.statue.Statue;
import de.adventure.game.input.HitboxListener;

import java.util.ArrayList;

public class MainPlayingScreen extends ScreenBase {
    protected final Game game;
    protected final Main main;
    private final Stage stage;
    private TextButton.TextButtonStyle tbStyle;
    private final BitmapFont font;
    private final Skin skin;
    /*private final Table tableMap, tableInventory;*/
    //private final SpriteBatch batch;

    private int x, y, pixelTileSize;

    private float unitScale, mapWidth, mapHeight;

    //Map Rendering
    private TiledMap tiledMap;
    private TiledMap desert_map_1;

    private TiledMapTileLayer solidLayer, solidUnderLayer;
    private TiledMapTileLayer interactableLayer;
    private TiledMapTileLayer interactableTopLayer, solidTopLayer, decorationLayer, decorationTopLayer, shadowLayer, groundLayer, groundTopLayer;

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
    private Statue statue;
    private HitboxListener hitboxListener;

    private Box2DDebugRenderer debugRenderer;

    //Player Animation
    private Animation<TextureRegion> walkAnimationUp, walkAnimationDown, walkAnimationLeft, walkAnimationRight;
    private Animation<TextureRegion> idleAnimationUp, idleAnimationDown, idleAnimationLeft, idleAnimationRight;
    private Texture walkSheetRight;
    private SpriteBatch spriteBatch;
    private Sprite playerSprite;
    private TextureRegion currentFrame;

    //Kreiert den Spielscreen
    public MainPlayingScreen(final Game game, final Main main) {
        super(game, main, "MainPlayingScreen");
        this.game = game;
        this.main = main;

        //Player Animation
        walkAnimationUp = new Animation<TextureRegion>(0.025f, createTextureRegion(6, 1, new Texture("player/WalkUp.png")));
        idleAnimationUp = new Animation<TextureRegion>(0.025f, createTextureRegion(6, 1, new Texture("player/IdleUp.png")));
        walkAnimationDown = new Animation<TextureRegion>(0.025f, createTextureRegion(6, 1, new Texture("player/WalkDown.png")));
        idleAnimationDown = new Animation<TextureRegion>(0.025f, createTextureRegion(6, 1, new Texture("player/IdleDown.png")));
        walkAnimationLeft = new Animation<TextureRegion>(0.025f, createTextureRegion(6, 1, new Texture("player/WalkLeft.png")));
        idleAnimationLeft = new Animation<TextureRegion>(0.025f, createTextureRegion(6, 1, new Texture("player/IdleLeft.png")));
        walkAnimationRight = new Animation<TextureRegion>(0.025f, createTextureRegion(6, 1, new Texture("player/WalkRight.png")));
        idleAnimationRight = new Animation<TextureRegion>(0.025f, createTextureRegion(6, 1, new Texture("player/IdleRight.png")));
        spriteBatch = new SpriteBatch();

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

        //Statue test
        /*
        statue = new Statue("\n An der Statue steht geschrieben \n" +
                "\nVersuche doch mal E um mit Dingen im Universum zu Interagieren!", 100, 20);*/

        debugRenderer = new Box2DDebugRenderer();
        //Tiled Map
        tiledMap = new TmxMapLoader().load("map/mapFinal.tmx");

        //Collision Layer
        solidLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Solid");
        solidUnderLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Solid_Under");
        interactableLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Interactable");

        //Rest Layer
        interactableTopLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Top_Interactable");
        solidTopLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Top_Solid");
        decorationLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Decoration");
        decorationTopLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Top_Decoration");
        shadowLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Shadow");
        groundLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Ground");
        groundTopLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Top_Ground");

        world = new World(new Vector2(0f, 0f), true);
        shape = new PolygonShape();
        circleShape = new CircleShape();
        player = main.getPlayer();

        bodyPlayer = player.createBody(world, circleShape, 0.5F, 30, 5);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        //Orthographic Cam
        orthoCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Cam zoom (für das spiel später Gdx.graphics.getWidth() & getHeight() / 80!!!)
        orthoCam.setToOrtho(false, (float) Gdx.graphics.getWidth() / 80, (float) Gdx.graphics.getHeight() / 80);

        //Viewport
        //viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), orthoCam);

        //Map Renderer
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);

        stage = new Stage();
        hitboxListener = new HitboxListener(stage);

        font = new BitmapFont();
        skin = new Skin();

        /*
        tableMap = new Table();
        tableMap.setBounds(0, 0, 250, 250);
        tableMap.setX(25F);
        tableMap.setY(Gdx.graphics.getHeight() - 275);
         */

        /*
        tableInventory = new Table();
        tableInventory.setBounds(0, 0, 50, 400);
        tableInventory.setX(25F);
        tableInventory.setY(Gdx.graphics.getHeight() - 800);
         */

        tbStyle = new TextButton.TextButtonStyle();
        tbStyle.font = font;

        //Debug code
        if(main.isDebug()) {
            stage.setDebugAll(true);
        }

        //stage.addActor(tableMap);
        //stage.addActor(tableInventory);

        //Setzt den generellen Input Processor zum stage Objekt (wird benutzt damit man überhaupt was machen kann)
        Gdx.input.setInputProcessor(stage);
        world.setContactListener(hitboxListener);

    }

    public TextureRegion[] createTextureRegion(int columns, int rows, Texture texture) {
        TextureRegion[][] textureRegion = TextureRegion.split(texture, texture.getWidth() / columns, texture.getHeight() / rows);
        TextureRegion[] walkFrames = new TextureRegion[columns * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                walkFrames[index++] = textureRegion[i][j];
            }
        }
        return walkFrames;
    }

    private float elapsed = 0F;
    public void waterAnimation() {
        elapsed += Gdx.graphics.getDeltaTime();

        int[] waterAnim2 = {3};
        int[] waterAnim3 = {4};
        int[] waterAnim4 = {5};
        int[] waterAnim5 = {6};
        int[] waterAnim6 = {7};
        int[] waterAnim7 = {8};
        int[] waterAnim8 = {9};

        if(elapsed > 0.8F) {
            elapsed = 0;
        }else if(elapsed > 0.7F) {
            mapRenderer.render(waterAnim8);
        }else if(elapsed > 0.6F) {
            mapRenderer.render(waterAnim7);
        }else if(elapsed > 0.5F) {
            mapRenderer.render(waterAnim6);
        }else if(elapsed > 0.4F) {
            mapRenderer.render(waterAnim5);
        }else if(elapsed > 0.3F) {
            mapRenderer.render(waterAnim4);
        }else if(elapsed > 0.2F) {
            mapRenderer.render(waterAnim3);
        }else if(elapsed > 0.1F) {
            mapRenderer.render(waterAnim2);
        }
    }
    //Generelle render Methode die nach dem switchen zu diesem Screen benutzt wird
    private float accumulatedTime = 0F;
    @Override
    public void render(float delta) {
        clearColorBuffer();
        accumulatedTime += Gdx.graphics.getDeltaTime();

        //Clipping des Spielers (Man kann hinter blöcken stehen, je nachdem was als Erstes gerendert wird)
        //15 = Top_Solid
        //14 = Solid
        int[] groundTopGround = {0, 1};
        int[] behindPlayer = {10, 11, 12, 13, 14, 16};
        int[] overPlayer = {15, 17};

        processInput();
        orthoCam.position.set(new Vector3(main.getPlayer().getXCord(), main.getPlayer().getYCord(), 0));
        orthoCam.update();

        mapRenderer.setView(orthoCam);
        mapRenderer.render(groundTopGround);
        waterAnimation();

        playerSprite = new Sprite(currentFrame);
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2 - 125F, (float) Gdx.graphics.getHeight() / 2 - 125F);
        playerSprite.setSize(250F, 250F);



        mapRenderer.render(behindPlayer);

        //debugRender(main.isDebug());
        spriteBatch.begin();
        playerSprite.draw(spriteBatch);
        spriteBatch.end();

        mapRenderer.render(overPlayer);

        //"Malt" alles auf den screen
        //statue.throwText(stage);
        stage.draw();
        world.step(1/60f, 6, 2);
    }

    //Der debug renderer hat nen memory leak lol
    public void debugRender(boolean isDebug) {
        if(isDebug) {
            debugRenderer.setDrawBodies(true);
            debugRenderer.setDrawInactiveBodies(true);
            debugRenderer.setDrawContacts(true);
            debugRenderer.render(world, orthoCam.combined);
        }
    }

    public enum Orientation {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    private Orientation orientation = Orientation.RIGHT;

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
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

        switch(orientation) {
            case UP:
                currentFrame = idleAnimationUp.getKeyFrame(accumulatedTime / 8, true);
                break;

            case DOWN:
                currentFrame = idleAnimationDown.getKeyFrame(accumulatedTime / 8, true);
                break;

            case LEFT:
                currentFrame = idleAnimationLeft.getKeyFrame(accumulatedTime / 8, true);
                break;

            case RIGHT:
                currentFrame = idleAnimationRight.getKeyFrame(accumulatedTime / 8, true);
                break;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            yForce = 3;
            currentFrame = walkAnimationUp.getKeyFrame(accumulatedTime / 4, true);
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                yForce = 4;
                currentFrame = walkAnimationUp.getKeyFrame(accumulatedTime / 3, true);
            }

            setOrientation(Orientation.UP);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xForce = -3;
            currentFrame = walkAnimationLeft.getKeyFrame(accumulatedTime / 4, true);
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                xForce = -4;
                currentFrame = walkAnimationLeft.getKeyFrame(accumulatedTime / 3, true);
            }
            setOrientation(Orientation.LEFT);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            yForce = -3;
            currentFrame = walkAnimationDown.getKeyFrame(accumulatedTime / 4, true);
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                yForce = -4;
                currentFrame = walkAnimationDown.getKeyFrame(accumulatedTime / 3, true);
            }
            setOrientation(Orientation.DOWN);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xForce = 3;
            currentFrame = walkAnimationRight.getKeyFrame(accumulatedTime / 4, true);
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                xForce = 4;
                currentFrame = walkAnimationRight.getKeyFrame(accumulatedTime / 3, true);
            }
            setOrientation(Orientation.RIGHT);
        }

        bodyPlayer.setLinearVelocity(yForce * 1, bodyPlayer.getLinearVelocity().y);
        bodyPlayer.setLinearVelocity(xForce * 1, bodyPlayer.getLinearVelocity().x);
        main.getPlayer().updatePosition(bodyPlayer.getPosition().x, bodyPlayer.getPosition().y);
    }

    public ArrayList<Body> createCollisionBoxes(TiledMapTileLayer tileLayer, boolean interactable, Object object) {
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
                if(interactable && object instanceof Statue) {
                    bodyDefinition.position.set(column + 0.5F, row - 0.1F);
                }

                shape.setAsBox(0.48F, 0.48F);
                if(interactable && object instanceof Statue) {
                    shape.setAsBox(0.35F, 0.1F);
                }

                FixtureDef fixtureDefinition = new FixtureDef();
                fixtureDefinition.shape = shape;
                fixtureDefinition.friction = 0F;
                if(interactable) {
                    fixtureDefinition.isSensor = true;
                }

                bodies.add(world.createBody(bodyDefinition));
                bodies.get(index).createFixture(fixtureDefinition);
                bodies.get(index).getFixtureList().get(0).setUserData(-1);

                //Statue ID range -> 0 - 20
                //Default id -> -1
                if(interactable && object instanceof Statue) {
                    bodies.get(index).getFixtureList().get(0).setUserData(index);
                }
                //TODO ID hinzufügen für Kisten usw
                index++;

            }
        }
        return bodies;
    }

    //Wird ein einziges Mal aufgerufen, und zwar beim switchen zu diesem screen (ist wie die Methode create())
    @Override
    public void show() {
        mainMusic.play();
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);
        Gdx.input.setCursorCatched(true);
        Gdx.graphics.setTitle("Adventure");
        Gdx.input.setInputProcessor(stage);
        bodies = createCollisionBoxes(solidLayer, false, null);
        bodies.addAll(createCollisionBoxes(solidUnderLayer, false, null));

        //Statues
        Statue statue = new Statue(null, 0, 0, 0);
        bodies.addAll(createCollisionBoxes(interactableLayer, true, statue));
        bodies.addAll(createCollisionBoxes(interactableLayer, false, null));
    }

    //Wird aufgerufen, wenn zu einem anderen Screen geswitcht wird
    @Override
    public void hide() {
        //mainMusic.stop();
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        Gdx.input.setCursorCatched(false);
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose () {
        skin.dispose();
        font.dispose();
        stage.dispose();
    }
}
