package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainScreen extends ScreenBase implements Screen {
    protected final Game game;
    private Stage stage;
    private BitmapFont font;
    private Skin skin;
    private Table table;
    private TextButton button;
    private TextButton.TextButtonStyle tbStyle;

    public MainScreen(final Game game) {
        super(game, "MainScreen");
        this.game = game;

        stage = new Stage();
        font = new BitmapFont();
        skin = new Skin();

        table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        tbStyle = new TextButton.TextButtonStyle();
        tbStyle.font = font;
        button = new TextButton("Play", tbStyle);
        button.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });


        table.add(button);
        button.getLabel().setFontScale(5F);
        button.pad(50F, 50F, 50F, 50F);

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
    }
}
