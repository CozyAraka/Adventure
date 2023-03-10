package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
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
import de.adventure.game.input.HitboxListener;
import de.adventure.game.inventory.Inventory;
import de.adventure.game.inventory.InventoryActor;
import de.adventure.game.savemanager.SaveManager;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
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

    //Savestate
    private float playerXPos;
    private float playerYPos;
    private float playerMoney;
    private float playerHealth;
    private float playerManaPoints;

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

        //Staring pos (for save)
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

        //bodyPlayer = player.createBody(world, circleShape, 0.2F, 56.5F, 20.5F);
        //Player states
        setSavedOrientation();
        playerXPos = main.getSaveManager().savedXPos();
        playerYPos = main.getSaveManager().savedYPos();
        playerMoney = main.getSaveManager().savedMoney();
        playerHealth = main.getSaveManager().savedHP();
        playerManaPoints = main.getSaveManager().savedMP();
        bodyPlayer = player.createBody(world, circleShape, 0.2F, playerXPos, playerYPos);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        //Orthographic Cam
        orthoCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Cam zoom (f??r das spiel sp??ter Gdx.graphics.getWidth() & getHeight() / 80!!!)
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

        //Setzt den generellen Input Processor zum stage Objekt (wird benutzt damit man ??berhaupt was machen kann)
        stage.addActor(inventoryActor);
        world.setContactListener(hitboxListener);
        Gdx.input.setInputProcessor(stage);

    }

    public void updateCameraCorrespondingToMap() {
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

    public void velocityLogic(float xForce, float yForce) {
        if(bodyPlayer.getPosition().x > mapWidth && orientation == Orientation.RIGHT && (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))) {
            bodyPlayer.setLinearVelocity(yForce / 3F, bodyPlayer.getLinearVelocity().y);
            bodyPlayer.setLinearVelocity(0, bodyPlayer.getLinearVelocity().x);
            return;

        }else if(bodyPlayer.getPosition().x < 0 && orientation == Orientation.LEFT && (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))) {
            bodyPlayer.setLinearVelocity(yForce / 3F, bodyPlayer.getLinearVelocity().y);
            bodyPlayer.setLinearVelocity(0 / 3F, bodyPlayer.getLinearVelocity().x);
            return;

        }

        if(bodyPlayer.getPosition().y > mapHeight && orientation == Orientation.UP && (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))) {
            bodyPlayer.setLinearVelocity(0 / 3F, bodyPlayer.getLinearVelocity().y);
            bodyPlayer.setLinearVelocity(xForce / 3F, bodyPlayer.getLinearVelocity().x);
            return;

        }else if(bodyPlayer.getPosition().y < 0 && orientation == Orientation.DOWN && (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))) {
            bodyPlayer.setLinearVelocity(0 / 3F, bodyPlayer.getLinearVelocity().y);
            bodyPlayer.setLinearVelocity(xForce / 3F, bodyPlayer.getLinearVelocity().x);
            return;

        }
        bodyPlayer.setLinearVelocity(yForce / 3F, bodyPlayer.getLinearVelocity().y);
        bodyPlayer.setLinearVelocity(xForce / 3F, bodyPlayer.getLinearVelocity().x);
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

        //Clipping des Spielers (Man kann hinter bl??cken stehen, je nachdem was als Erstes gerendert wird)
        //15 = Top_Solid
        //14 = Solid

        int[] groundTopGround = {0, 1};
        int[] behindPlayer = {10, 11, 12, 13, 14, 16, 17};
        int[] overPlayer = {15, 16, 18, 19};

        processInput();

        playerSprite = new Sprite(currentFrame);
        playerSprite.setOrigin(0, 0);
        playerSprite.setSize(3F, 3F);
        playerSprite.setPosition(bodyPlayer.getPosition().x - 1.5F, bodyPlayer.getPosition().y - 1.20F);
        orthoCam.position.set(new Vector3(bodyPlayer.getPosition().x, bodyPlayer.getPosition().y, 0));

        updateCameraCorrespondingToMap();

        orthoCam.update();

        mapRenderer.setView(orthoCam);
        mapRenderer.render(groundTopGround);
        waterAnimation();

        mapRenderer.render(behindPlayer);

        spriteBatch.setProjectionMatrix(orthoCam.combined);
        spriteBatch.begin();
        playerSprite.draw(spriteBatch);
        spriteBatch.end();

        mapRenderer.render(overPlayer);

        debugRender(main.isDebug());

        interactLogic();

        stage.draw();
        world.step(1/60f, 6, 2);
    }

    private static boolean pressedE = false;
    public static void setPressedE() {
        pressedE = !pressedE;
    }
    public static boolean getPressedE() {
        return pressedE;
    }

    //TODO Kisten interact logic usw.
    public void interactLogic() {
        if(HitboxListener.isTouching()) {
            if(orientation == Orientation.UP) {
                if((Gdx.input.isKeyJustPressed(Input.Keys.E) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) && pressedE) {
                    HitboxListener.getStatue().endText(stage);
                    pressedE = !pressedE;

                }else if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                    HitboxListener.getStatue().throwText(stage);
                    pressedE = !pressedE;

                }
            }else if(pressedE) {
                HitboxListener.getStatue().endText(stage);
                pressedE = !pressedE;

            }
        }
        if(HitboxListener.getTouched() == 1) {
            HitboxListener.getStatue().endText(stage);
            HitboxListener.setTouched(0);
        }
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
    private static Orientation orientation = Orientation.RIGHT;
    public void setSavedOrientation() {

        switch(main.getSaveManager().savedOrientation()) {
            case 'u':
                setOrientation(Orientation.UP);
                break;
            case 'd':
                setOrientation(Orientation.DOWN);
                break;
            case 'l':
                setOrientation(Orientation.LEFT);
                break;
            case 'r':
                setOrientation(Orientation.RIGHT);
                break;
        }
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public static Orientation getOrientation() {
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
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);
                Gdx.input.setCursorCatched(true);

            }else {
                inventoryActor.setVisible(true);
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                Gdx.input.setCursorCatched(false);
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

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            xForce = -2;
            yForce = 0;
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
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            xForce = 2;
            yForce = 0;
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
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            yForce = -2;
            xForce = 0;
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
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            yForce = 2;
            xForce = 0;
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

        velocityLogic(xForce, yForce);
        main.getPlayer().updatePosition(bodyPlayer.getPosition().x, bodyPlayer.getPosition().y);
    }

    public ArrayList<Body> createCollisionBoxes(@NotNull TiledMapTileLayer tileLayer, boolean interactable) {
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
                //Statue Tile ID = 143
                if(interactable && cell.getTile().getId() == 143) {
                    bodyDefinition.position.set(column + 0.5F, row - 0.1F);
                }

                shape.setAsBox(0.48F, 0.48F);
                //Statue Tile ID = 143
                if(interactable && cell.getTile().getId() == 143) {
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

                //Statue Tile ID = 143
                if(interactable && cell.getTile().getId() == 143) {
                    bodies.get(index).getFixtureList().get(0).setUserData(index);
                }
                //TODO ID hinzuf??gen f??r Kisten usw
                index++;

            }
        }
        return bodies;
    }

    //Wird ein einziges Mal aufgerufen, und zwar beim switchen zu diesem screen (ist wie die Methode create())
    @Override
    public void show() {
        Gdx.app.debug(LocalTime.now() + "", "Resumed Game");

        mainMusic.play();
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);
        Gdx.input.setCursorCatched(true);
        Gdx.graphics.setTitle("Adventure");
        Gdx.input.setInputProcessor(stage);
        bodies = createCollisionBoxes(solidLayer, false);
        bodies.addAll(createCollisionBoxes(solidUnderLayer, false));
        bodies.addAll(createCollisionBoxes(solidOverPlayerLayer, false));

        //Statues
        bodies.addAll(createCollisionBoxes(interactableLayer, true));
        bodies.addAll(createCollisionBoxes(interactableLayer, false));
    }

    public String orientationToString(@NotNull Orientation orientation) {
        switch(orientation) {
            case UP:
                return "up";

            case DOWN:
                return "down";

            case LEFT:
                return "left";

            case RIGHT:
                return "right";
        }
        return null;
    }

    //Wird aufgerufen, wenn zu einem anderen Screen geswitcht wird
    @Override
    public void hide() {
        //mainMusic.stop();
        Gdx.app.debug(LocalTime.now() + "", "Saved Game and paused");
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        Gdx.input.setCursorCatched(false);
        Gdx.input.setInputProcessor(null);

        ArrayList<String> str = new ArrayList<>();
        str.add("cordsX:" + bodyPlayer.getPosition().x);
        str.add("cordsY:" + bodyPlayer.getPosition().y);
        str.add("facing:" + orientationToString(getOrientation()));
        str.add("hp:100.0");
        str.add("mp:100.0");
        str.add("money:20.45");

        SaveManager saveManager = new SaveManager("save1.adv", main);
        saveManager.saveGame(str);
    }

    @Override
    public void dispose () {
        skin.dispose();
        font.dispose();
        stage.dispose();
    }

    public float getPlayerXPos() {
        return playerXPos;
    }

    public void setPlayerXPos(float playerXPos) {
        this.playerXPos = playerXPos;
    }

    public float getPlayerYPos() {
        return playerYPos;
    }

    public void setPlayerYPos(float playerYPos) {
        this.playerYPos = playerYPos;
    }

    public float getPlayerMoney() {
        return playerMoney;
    }

    public void setPlayerMoney(float playerMoney) {
        this.playerMoney = playerMoney;
    }
}
