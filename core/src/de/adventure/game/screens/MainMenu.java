package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.adventure.game.Main;

public class MainMenu extends ScreenBase implements Screen {
    protected final Game game;
    protected final Main main;
    private final Stage stage;
    private final TextButton playButton, quitButton;
    private TextButton.TextButtonStyle tbStyle;
    private final BitmapFont font;
    private final Skin skin;
    private final Table table;

    //Kreiert das MainMenu
    public MainMenu(final Game game, final Main main) {
        super(game, main, "MainMenu");
        this.game = game;
        this.main = main;

        stage = new Stage();
        font = new BitmapFont();
        skin = new Skin();

        //Table ist praktisch eine Grid zum Platzieren von Objekten, wie ein Regal
        table = new Table();
        table.setBounds(1, 1, Gdx.graphics.getWidth() - 1, Gdx.graphics.getHeight() - 1);

        tbStyle = new TextButton.TextButtonStyle();
        tbStyle.font = font;

        playButton = new TextButton("Play", tbStyle);
        //Fügt einen Listener zum Button hinzu (damit dieser benutzt werden kann)
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(main.getCharacterCreation());
            }
        });

        quitButton = new TextButton("Quit", tbStyle);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                System.exit(0);
            }
        });


        table.add(playButton);
        playButton.getLabel().setFontScale(5F);
        //Ist wie CSS (Erstellt praktisch eine Border um den Button wo nichts anderes hin kann (Objekte in Table))
        playButton.pad(50F, 50F, 50F, 50F);

        table.add(quitButton);
        quitButton.getLabel().setFontScale(5F);
        quitButton.pad(50F, 50F, 50F, 50F);

        //Debug codde
        if(main.isDebug()) {
            stage.setDebugAll(true);
        }

        stage.addActor(table);
        //Setzt den generellen Input Processor zum stage Objekt (wird benutzt damit man überhaupt was machen kann)
        Gdx.input.setInputProcessor(stage);

    }

    //Generelle render Methode die nach dem switchen zu diesem Screen benutzt wird
    @Override
    public void render(float delta) {
        clearColorBuffer();

        //"Malt" alles auf den screen
        stage.draw();
    }

    //Wird ein einziges Mal aufgerufen, und zwar beim switchen zu diesem screen (ist wie die Methode create())
    @Override
    public void show() {
        Gdx.graphics.setTitle("Main Menu");
        Gdx.input.setInputProcessor(stage);
    }

    //Wird aufgerufen, wenn zu einem anderen Screen geswitcht wird
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose () {
        skin.dispose();
        font.dispose();
        stage.dispose();
    }
}
