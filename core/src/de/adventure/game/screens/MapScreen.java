package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import de.adventure.game.Main;
import de.adventure.game.audio.Audio;

public class MapScreen extends ScreenBase implements Screen {
    protected final Game game;
    protected final Main main;
    private final Stage stage;
    private TextButton.TextButtonStyle tbStyle;
    private final BitmapFont font;
    private final Skin skin;
    private final Table table, tableMap;

    private Audio mainMusic;

    //Kreiert das MainMenu
    public MapScreen(final Game game, final Main main) {
        super(game, main, "MainPlayingScreen");
        this.game = game;
        this.main = main;

        mainMusic = new Audio("audio/mainMenuMusic.wav", 0.15F, true, main);

        stage = new Stage();
        font = new BitmapFont();
        skin = new Skin();

        //Table ist praktisch eine Grid zum Platzieren von Objekten, wie ein Regal
        table = new Table();
        table.setBounds(1, 1, Gdx.graphics.getWidth() - 1, Gdx.graphics.getHeight() - 1);

        tableMap = new Table();
        tableMap.setBounds(50, 50, Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 100);
        tableMap.setY(50);
        tableMap.setX(50);

        //Debug code
        if(main.isDebug()) {
            stage.setDebugAll(true);
        }

        stage.addActor(table);
        stage.addActor(tableMap);

        //Setzt den generellen Input Processor zum stage Objekt (wird benutzt damit man überhaupt was machen kann)
        Gdx.input.setInputProcessor(stage);

    }

    //Generelle render Methode die nach dem switchen zu diesem Screen benutzt wird
    @Override
    public void render(float delta) {
        clearColorBuffer();

        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            game.setScreen(main.getMainPlayingScreen());

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(main.getMainPlayingScreen());
        }
        //"Malt" alles auf den screen
        stage.draw();
    }

    //Wird ein einziges Mal aufgerufen, und zwar beim switchen zu diesem screen (ist wie die Methode create())
    @Override
    public void show() {
        //mainMusic.play();
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
