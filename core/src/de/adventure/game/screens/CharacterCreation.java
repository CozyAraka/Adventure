package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.adventure.game.Main;

public class CharacterCreation extends ScreenBase implements Screen {
    protected final Game game;
    protected final Main main;
    private Stage stage;
    private BitmapFont font;
    private Skin skin;
    private Table tableButton, tableText;
    private TextButton button;
    private TextButton.TextButtonStyle tbStyle;
    private TextField textFieldCharName, textFieldNameHint;
    private TextField.TextFieldStyle tfStyle;

    //Siehe Klasse "MainMenu" für Erklärungen
    public CharacterCreation(final Game game, final Main main) {
        super(game, main, "Character Creation");
        this.game = game;
        this.main = main;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();
        font.setColor(Color.WHITE);

        //TODO Eigene Skins für Buttons und Textfeldder
        skin = new Skin();

        tableButton = new Table();
        tableButton.setBounds(0, 0, 100, 50);
        tableButton.setX((float) (Gdx.graphics.getWidth() / 2) - (tableButton.getWidth() / 2));
        tableButton.setY(250F);

        tableText = new Table();
        tableText.setBounds(0, 0, 500, 50);
        tableText.setX((float) (Gdx.graphics.getWidth() / 2) - (tableText.getWidth() / 2));
        tableText.setY(500F);

        tbStyle = new TextButton.TextButtonStyle();
        tbStyle.font = font;

        tfStyle = new TextField.TextFieldStyle();
        tfStyle.font = font;
        tfStyle.font.getData().setScale(2.5F);
        tfStyle.fontColor = Color.WHITE;

        textFieldNameHint = new TextField("Wähle deinen Namen:", tfStyle);
        textFieldNameHint.setDisabled(true);
        tableText.add(textFieldNameHint);

        textFieldCharName = new TextField("Hellu", tfStyle);
        textFieldCharName.setBounds(textFieldCharName.getX(), textFieldCharName.getY(), 20F, 5F);
        textFieldCharName.setMaxLength(16);
        tableText.add(textFieldCharName);

        button = new TextButton("Start", tbStyle);
        button.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(main.getMainMenu());
            }
        });
        button.getLabel().setFontScale(5F);
        button.pad(50F, 50F, 50F, 50F);
        tableButton.add(button);

        //Debug code
        if(main.isDebug()) {
            stage.setDebugAll(true);
        }

        stage.addActor(tableButton);
        stage.addActor(tableText);
    }

    @Override
    public void render(float delta) {
        clearColorBuffer();

        stage.draw();
    }

    @Override
    public void show() {
        Gdx.graphics.setTitle(getName());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.graphics.setTitle("Switching..");
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose () {
        skin.dispose();
        font.dispose();
        stage.dispose();
    }
}
