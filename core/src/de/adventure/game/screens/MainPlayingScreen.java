package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import de.adventure.game.Main;
import de.adventure.game.audio.Audio;
import de.adventure.game.entities.player.Player;
import de.adventure.game.entities.statue.Statue;
import de.adventure.game.input.HitboxListener;
import de.adventure.game.inventory.Inventory;
import de.adventure.game.inventory.InventoryActor;

import java.util.ArrayList;

public class MainPlayingScreen extends ScreenBase {
    protected final Game game;
    protected final Main main;
    private final Stage stage;
    private final BitmapFont font;
    private final Skin skin;

    private int pixelTileSize;

    private final float unitScale, mapWidth, mapHeight;
    private float elapsed = 0F;
    private float accumulatedTime = 0F;

    //Map Rendering
    private final OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap tiledMap;
    private TiledMapTileLayer solidLayer, solidUnderLayer, solidOverPlayerLayer;
    private TiledMapTileLayer interactableLayer;

    //Physics
    private final Player player;
    private final World world;
    private Body bodyPlayer;
    private PolygonShape shape;
    private CircleShape circleShape;
    private ArrayList<Body> bodies;
    private HitboxListener hitboxListener;

    private Audio mainMusic;

    //Rendering
    private final OrthographicCamera orthoCam;
    private Box2DDebugRenderer debugRenderer;
    private ShapeRenderer shapeRenderer;

    //Player Animation
    private final Animation<TextureRegion> walkAnimationUp, walkAnimationDown, walkAnimationLeft, walkAnimationRight;
    private final Animation<TextureRegion> idleAnimationUp, idleAnimationDown, idleAnimationLeft, idleAnimationRight;
    private SpriteBatch spriteBatch;
    private Sprite playerSprite;
    private TextureRegion currentFrame;

    //Inventory
    private InventoryActor inventoryActor;
    private Skin inventorySkin;

    //Kreiert den Spielscreen
    public MainPlayingScreen(final Game game, final Main main) {
        super(game, main, "MainPlayingScreen");
        this.game = game;
        this.main = main;

        //Inventory
        inventorySkin = Main.assets.get("skins/uiskin.json", Skin.class);
        DragAndDrop dragAndDrop = new DragAndDrop();
        inventoryActor = new InventoryActor(new Inventory(), dragAndDrop, inventorySkin);
        stage = Main.getStage();

        //Player Animation
        walkAnimationUp = createAnimation(0.025F, 6, 1, "player/WalkUp.png");
        idleAnimationUp = createAnimation(0.025F, 6, 1, "player/IdleUp.png");
        walkAnimationDown = createAnimation(0.025F, 6, 1, "player/WalkDown.png");
        idleAnimationDown = createAnimation(0.025F, 6, 1, "player/IdleDown.png");
        walkAnimationLeft = createAnimation(0.025F, 6, 1, "player/WalkLeft.png");
        idleAnimationLeft = createAnimation(0.025F, 6, 1, "player/IdleLeft.png");
        walkAnimationRight = createAnimation(0.025F, 6, 1, "player/WalkRight.png");
        idleAnimationRight = createAnimation(0.025F, 6, 1, "player/IdleRight.png");

        spriteBatch = new SpriteBatch();

        //Music
        mainMusic = new Audio("audio/forestBirds.wav", 0.15F, true, main);
        mainMusic.setAsLoop();

        //Map width & height (tiles)
        mapWidth = 80F;
        mapHeight = 60F;

        //TileSize
        unitScale = 1/16F;
        pixelTileSize = 16;

        debugRenderer = new Box2DDebugRenderer();

        //Tiled Map
        tiledMap = new TmxMapLoader().load("map/mapFinal.tmx");

        //Collision Layer
        solidLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Solid");
        solidUnderLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Solid_Under");
        solidOverPlayerLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Solid_over_Player");
        interactableLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Interactable");

        world = new World(new Vector2(0f, 0f), true);
        shape = new PolygonShape();
        circleShape = new CircleShape();
        player = main.getPlayer();

        bodyPlayer = player.createBody(world, circleShape, 0.2F, 56.5F, 20.5F);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        //Orthographic Cam
        orthoCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Cam zoom (für das spiel später Gdx.graphics.getWidth() & getHeight() / 80!!!)
        orthoCam.setToOrtho(false, (float) Gdx.graphics.getWidth() / 80, (float) Gdx.graphics.getHeight() / 80);
        //orthoCam.setToOrtho(false, (float) Gdx.graphics.getWidth() / 40, (float) Gdx.graphics.getHeight() / 40);

        //Map Renderer
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
        hitboxListener = new HitboxListener(stage);

        font = new BitmapFont();
        skin = new Skin();

        //Debug code
        if(main.isDebug()) {
            stage.setDebugAll(true);
        }

        //Setzt den generellen Input Processor zum stage Objekt (wird benutzt damit man überhaupt was machen kann)
        stage.addActor(inventoryActor);
        world.setContactListener(hitboxListener);
        Gdx.input.setInputProcessor(stage);

    }

    public void updateCameraWithPlayer() {
        if(orthoCam.viewportWidth > mapWidth) {
            orthoCam.position.x = mapWidth;

        }else if(orthoCam.position.x - orthoCam.viewportWidth / 2 <= 0) {
            orthoCam.position.x = orthoCam.viewportWidth / 2;

        }else if(orthoCam.position.x + orthoCam.viewportWidth / 2 >= mapWidth) {
            orthoCam.position.x = mapWidth - orthoCam.viewportWidth / 2;

        }

        if(orthoCam.viewportHeight > mapHeight) {
            orthoCam.position.y = mapHeight / 2;

        }else if(orthoCam.position.y - orthoCam.viewportHeight / 2 <= 0) {
            orthoCam.position.y = orthoCam.viewportHeight / 2;

        }else if(orthoCam.position.y + orthoCam.viewportHeight / 2 >= mapHeight) {
            orthoCam.position.y = mapHeight - orthoCam.viewportHeight / 2;

        }
    }

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
    @Override
    public void render(float delta) {
        clearColorBuffer();
        mainMusic.playOnCords(56.5F, 31.5F);

        accumulatedTime += Gdx.graphics.getDeltaTime();

        //Clipping des Spielers (Man kann hinter blöcken stehen, je nachdem was als Erstes gerendert wird)
        //15 = Top_Solid
        //14 = Solid

        int[] groundTopGround = {0, 1};
        int[] behindPlayer = {10, 11, 12, 13, 14, 16, 17};
        int[] overPlayer = {15, 16, 18, 19};

        processInput();

        playerSprite = new Sprite(currentFrame);
        playerSprite.setOrigin(0, 0);
        playerSprite.setSize(3F, 3F);
        //playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2 - 125F, (float) Gdx.graphics.getHeight() / 2 - 100F);
        //playerSprite.setPosition((float) Gdx.graphics.getWidth() * 0.8F, (float) Gdx.graphics.getHeight() * 0.8F);
        playerSprite.setPosition(bodyPlayer.getPosition().x - 1.5F, bodyPlayer.getPosition().y - 1.25F);


        orthoCam.position.set(new Vector3(main.getPlayer().getXCord(), main.getPlayer().getYCord(), 0));

        //Not working
        updateCameraWithPlayer();
        orthoCam.update();

        mapRenderer.setView(orthoCam);
        mapRenderer.render(groundTopGround);
        waterAnimation();

        mapRenderer.render(behindPlayer);

        //spriteBatch.setTransformMatrix(orthoCam.combined);
        spriteBatch.setProjectionMatrix(orthoCam.combined);
        spriteBatch.begin();
        playerSprite.draw(spriteBatch);
        spriteBatch.end();

        mapRenderer.render(overPlayer);

        debugRender(main.isDebug());

        //"Malt" alles auf den screen
        //statue.throwText(stage);
        stage.draw();
        world.step(1/60f, 6, 2);
    }

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
        if(Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            if(inventoryActor.isVisible()) {
                inventoryActor.setVisible(false);
            }else {
                inventoryActor.setVisible(true);
            }
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

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xForce = -2;
            currentFrame = walkAnimationLeft.getKeyFrame(accumulatedTime / 5, true);
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                xForce = -3;
                currentFrame = walkAnimationLeft.getKeyFrame(accumulatedTime / 4, true);
            }else if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                xForce = -1;
                currentFrame = walkAnimationLeft.getKeyFrame(accumulatedTime / 7, true);
            }
            setOrientation(Orientation.LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xForce = 2;
            currentFrame = walkAnimationRight.getKeyFrame(accumulatedTime / 5, true);
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                xForce = 3;
                currentFrame = walkAnimationRight.getKeyFrame(accumulatedTime / 4, true);
            }else if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                xForce = 1;
                currentFrame = walkAnimationRight.getKeyFrame(accumulatedTime / 7, true);
            }
            setOrientation(Orientation.RIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            yForce = -2;
            currentFrame = walkAnimationDown.getKeyFrame(accumulatedTime / 5, true);
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                yForce = -3;
                currentFrame = walkAnimationDown.getKeyFrame(accumulatedTime / 4, true);
            }else if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                yForce = -1;
                currentFrame = walkAnimationDown.getKeyFrame(accumulatedTime / 7, true);
            }
            setOrientation(Orientation.DOWN);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            yForce = 2;
            currentFrame = walkAnimationUp.getKeyFrame(accumulatedTime / 5, true);
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                yForce = 3;
                currentFrame = walkAnimationUp.getKeyFrame(accumulatedTime / 4, true);
            }else if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                yForce = 1;
                currentFrame = walkAnimationUp.getKeyFrame(accumulatedTime / 7, true);
            }

            setOrientation(Orientation.UP);
        }

        bodyPlayer.setLinearVelocity(yForce, bodyPlayer.getLinearVelocity().y);
        bodyPlayer.setLinearVelocity(xForce, bodyPlayer.getLinearVelocity().x);
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
        //Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);
        //Gdx.input.setCursorCatched(true);
        Gdx.graphics.setTitle("Adventure");
        Gdx.input.setInputProcessor(stage);
        bodies = createCollisionBoxes(solidLayer, false, null);
        bodies.addAll(createCollisionBoxes(solidUnderLayer, false, null));
        bodies.addAll(createCollisionBoxes(solidOverPlayerLayer, false, null));

        //Statues
        Statue statue = new Statue(null, 0, 0, 0);
        bodies.addAll(createCollisionBoxes(interactableLayer, true, statue));
        bodies.addAll(createCollisionBoxes(interactableLayer, false, null));
    }

    //Wird aufgerufen, wenn zu einem anderen Screen geswitcht wird
    @Override
    public void hide() {
        //mainMusic.stop();
        //Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        //Gdx.input.setCursorCatched(false);
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose () {
        skin.dispose();
        font.dispose();
        stage.dispose();
    }
}
