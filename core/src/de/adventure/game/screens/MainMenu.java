package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenu extends ScreenBase implements Screen {
    protected final Game game;
    private Stage stage;
    private TextButton playButton, quitButton;
    private TextButton.TextButtonStyle tbStyle;
    private BitmapFont font;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;

    public MainMenu(final Game game) {
        super(game, "MainMenu");
        this.game = game;

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
                stage.clear();
                game.setScreen(new MainScreen(game));
            }
        });
        quitButton = new TextButton("Quit", tbStyle);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void dispose () {
        skin.dispose();
        font.dispose();
        stage.dispose();
        System.exit(0);
    }
}
