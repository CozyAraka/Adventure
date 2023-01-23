package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import de.adventure.game.Main;
import de.adventure.game.audio.Audio;
import de.adventure.game.input.HoverListener;

public class PauseScreen extends ScreenBase implements Screen {
    protected final Game game;
    protected final Main main;
    private final Stage stage;
    private final Button resumeButton, quitButton;
    private TextButton.TextButtonStyle tbStyle;
    private final BitmapFont font;
    private final Skin skinButtonResume, skinButtonQuit;
    private final Table tableButtonResume, tableButtonQuit;

    private Audio mainMusic;

    //Kreiert das MainMenu
    public PauseScreen(final Game game, final Main main) {
        super(game, main, "MainMenu");
        this.game = game;
        this.main = main;

        mainMusic = new Audio("audio/mainMenuMusic.wav", 0.15F, true, main);

        stage = new Stage();
        font = new BitmapFont();

        skinButtonResume = new Skin(Gdx.files.internal("textures/Buttons/Resume/Resume.json"));
        skinButtonQuit = new Skin(Gdx.files.internal("textures/Buttons/Quit/Quit.json"));

        //Table ist praktisch eine Grid zum Platzieren von Objekten, wie ein Regal
        tableButtonResume = createTable(100, 50, true, 0, 600);
        tableButtonQuit = createTable(100, 50, true, 0, 400);

        tbStyle = new TextButton.TextButtonStyle();
        tbStyle.font = font;

        resumeButton = new Button(skinButtonResume);
        //Fügt einen Listener zum Button hinzu (damit dieser benutzt werden kann)
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(main.getMainPlayingScreen());
            }
        });
        tableButtonResume.add(resumeButton).pad(0F, 0F, 0F, 0F);

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

        stage.addActor(tableButtonResume);
        stage.addActor(tableButtonQuit);

        //Setzt den generellen Input Processor zum stage Objekt (wird benutzt damit man überhaupt was machen kann)
        Gdx.input.setInputProcessor(stage);

    }

    //Generelle render Methode die nach dem switchen zu diesem Screen benutzt wird
    @Override
    public void render(float delta) {
        clearColorBuffer();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(main.getMainPlayingScreen());
        }
        //"Malt" alles auf den screen
        stage.draw();
        stage.act();
        //stage.act(delta);
    }

    //Wird ein einziges Mal aufgerufen, und zwar beim switchen zu diesem screen (ist wie die Methode create())
    @Override
    public void show() {
        Gdx.graphics.setTitle("Pause Screen");
        Gdx.input.setInputProcessor(stage);
    }

    //Wird aufgerufen, wenn zu einem anderen Screen geswitcht wird
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose () {
        skinButtonResume.dispose();
        font.dispose();
        stage.dispose();
    }
}
