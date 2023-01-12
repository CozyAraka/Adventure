package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
    private Stage stage;
    private TextButton playButton, quitButton;
    private TextButton.TextButtonStyle tbStyle;
    private BitmapFont font;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;

    public MainMenu(final Game game, final Main main) {
        super(game, main, "MainMenu");
        this.game = game;
        this.main = main;

        stage = new Stage();
        font = new BitmapFont();
        skin = new Skin();

        table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        tbStyle = new TextButton.TextButtonStyle();
        tbStyle.font = font;

        playButton = new TextButton("Play", tbStyle);
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
        playButton.pad(50F, 50F, 50F, 50F);

        table.add(quitButton);
        quitButton.getLabel().setFontScale(5F);
        quitButton.pad(50F, 50F, 50F, 50F);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        clearColorBuffer();

        stage.draw();
    }

    @Override
    public void show() {
        Gdx.graphics.setTitle("Main Menu");
        Gdx.input.setInputProcessor(stage);
    }

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
