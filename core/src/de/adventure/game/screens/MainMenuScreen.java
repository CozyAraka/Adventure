package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.adventure.game.input.HoverListener;
import de.adventure.game.Main;
import de.adventure.game.audio.Audio;

public class MainMenuScreen extends ScreenBase implements Screen {
    protected final Game game;
    protected final Main main;
    private final Stage stage;
    private final Button startButton, quitButton;
    private TextButton.TextButtonStyle tbStyle;
    private final BitmapFont font;
    private final Skin skinButtonStart, skinButtonQuit;
    private final Table tableButtonStart, tableButtonQuit;

    private Audio mainMusic;

    //Kreiert das MainMenu
    public MainMenuScreen(final Game game, final Main main) {
        super(game, main, "MainMenu");
        this.game = game;
        this.main = main;

        mainMusic = new Audio("audio/mainMenuMusic.wav", 0.15F, true);

        stage = new Stage();
        font = new BitmapFont();

        skinButtonStart = new Skin(Gdx.files.internal("textures/Buttons/Start/Start.json"));
        skinButtonQuit = new Skin(Gdx.files.internal("textures/Buttons/Quit/Quit.json"));

        //Table ist praktisch eine Grid zum Platzieren von Objekten, wie ein Regal
        tableButtonStart = new Table();
        tableButtonStart.setBounds(0, 0, 100, 50);
        tableButtonStart.setX((float) (Gdx.graphics.getWidth() / 2) - (tableButtonStart.getWidth() / 2));
        tableButtonStart.setY(600F);

        tableButtonQuit = new Table();
        tableButtonQuit.setBounds(0, 0, 100, 50);
        tableButtonQuit.setX((float) (Gdx.graphics.getWidth() / 2) - (tableButtonQuit.getWidth() / 2));
        tableButtonQuit.setY(400F);

        tbStyle = new TextButton.TextButtonStyle();
        tbStyle.font = font;

        startButton = new Button(skinButtonStart);
        //Fügt einen Listener zum Button hinzu (damit dieser benutzt werden kann)
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(main.getCharacterCreation());
            }
        });
        tableButtonStart.add(startButton).pad(0F, 0F, 0F, 0F);

        quitButton = new Button(skinButtonQuit);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                System.exit(0);
            }
        });
        quitButton.addListener(new HoverListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
            }
        });
        quitButton.addListener(new HoverListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.exit(event, x, y, pointer, fromActor);
            }
        });
        tableButtonQuit.add(quitButton).pad(0F, 0F, 0F, 0F);

        //Debug code
        if(main.isDebug()) {
            stage.setDebugAll(true);
        }

        stage.addActor(tableButtonStart);
        stage.addActor(tableButtonQuit);

        //Setzt den generellen Input Processor zum stage Objekt (wird benutzt damit man überhaupt was machen kann)
        Gdx.input.setInputProcessor(stage);

    }

    //Generelle render Methode die nach dem switchen zu diesem Screen benutzt wird
    @Override
    public void render(float delta) {
        clearColorBuffer();

        //"Malt" alles auf den screen
        stage.draw();
        stage.act();
        //stage.act(delta);
    }

    //Wird ein einziges Mal aufgerufen, und zwar beim switchen zu diesem screen (ist wie die Methode create())
    @Override
    public void show() {
        mainMusic.play();
        Gdx.graphics.setTitle("Main Menu");
        Gdx.input.setInputProcessor(stage);
    }

    //Wird aufgerufen, wenn zu einem anderen Screen geswitcht wird
    @Override
    public void hide() {
        mainMusic.stop();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose () {
        skinButtonStart.dispose();
        font.dispose();
        stage.dispose();
    }
}
