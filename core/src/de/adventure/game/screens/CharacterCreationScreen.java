package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import de.adventure.game.Main;
import de.adventure.game.entities.player.Player;
import de.adventure.game.input.HoverListener;

public class CharacterCreationScreen extends ScreenBase implements Screen {
    protected final Game game;
    protected final Main main;

    private SpriteBatch batch;

    private Stage stage;
    private BitmapFont font;
    private Skin skinButtonPlay;
    private Table tableButtonStart, tableTextName, tableTextLooks, tablePictureChara, tableButtonArrow, tableTextLookNumber;

    private TextButton buttonStart, buttonArrowRight, buttonArrowLeft;
    private TextButton.TextButtonStyle tbStyle;

    private Button buttonPlay;

    private TextField textFieldCharName, textFieldNameHint, textFieldCharAussehen, textFieldLookNumber;
    private TextField.TextFieldStyle tfStyle;

    private TextureAtlas taButtonPlay;

    private String input;
    private int lookCount, lookAvailable;

    //Siehe Klasse "MainMenu" für Erklärungen
    public CharacterCreationScreen(final Game game, final Main main) {
        super(game, main, "Character Creation");
        this.game = game;
        this.main = main;

        stage = new Stage();

        //Testing purposes
        lookCount = 1;
        lookAvailable = 5;

        batch = new SpriteBatch();

        //Placeholder
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        //TODO Eigene Skins für Buttons und Textfelder
        skinButtonPlay = new Skin(Gdx.files.internal("textures/Buttons/Play/Play.json"));

        tableButtonStart = new Table();
        tableButtonStart.setBounds(0, 0, 100, 50);
        tableButtonStart.setX((float) (Gdx.graphics.getWidth() / 2) - (tableButtonStart.getWidth() / 2));
        tableButtonStart.setY(150F);

        tableButtonArrow = new Table();
        tableButtonArrow.setBounds(0, 0, 400, 50);
        tableButtonArrow.setX((float) (Gdx.graphics.getWidth() / 2) - (tableButtonArrow.getWidth() / 2));
        tableButtonArrow.setY(575F);

        tableTextLookNumber = new Table();
        tableTextLookNumber.setBounds(0, 0, 50, 50);
        tableTextLookNumber.setX((float) (Gdx.graphics.getWidth() / 2) - (tableTextLookNumber.getWidth() / 2));
        tableTextLookNumber.setY(400F);

        tableTextName = new Table();
        tableTextName.setBounds(0, 0, 500, 50);
        tableTextName.setX((float) (Gdx.graphics.getWidth() / 2) - (tableTextName.getWidth() / 2));
        tableTextName.setY(850F);

        tableTextLooks = new Table();
        tableTextLooks.setBounds(0, 0, 500, 50);
        tableTextLooks.setX((float) (Gdx.graphics.getWidth() / 2) - (tableTextLooks.getWidth() / 2));
        tableTextLooks.setY(800F);

        tablePictureChara = new Table();
        tablePictureChara.setBounds(0, 0, 400, 400);
        tablePictureChara.setX((float) (Gdx.graphics.getWidth() / 2) - (tablePictureChara.getWidth() / 2));
        tablePictureChara.setY(400F);

        tbStyle = new TextButton.TextButtonStyle();
        tbStyle.font = font;

        tfStyle = new TextField.TextFieldStyle();
        tfStyle.font = font;
        tfStyle.font.getData().setScale(1.5F);
        tfStyle.fontColor = Color.WHITE;

        textFieldNameHint = new TextField("Wähle deinen Namen:", tfStyle);
        textFieldNameHint.setDisabled(true);
        textFieldNameHint.setAlignment(Align.center);
        tableTextName.add(textFieldNameHint).width(250F).height(30F);

        tableTextName.row();

        textFieldCharName = new TextField("", tfStyle);
        textFieldCharName.setMaxLength(20);
        textFieldCharName.setAlignment(Align.center);
        tableTextName.add(textFieldCharName).width(300F).height(30F);

        textFieldCharAussehen = new TextField("Wähle dein Aussehen:", tfStyle);
        textFieldCharAussehen.setDisabled(true);
        textFieldCharAussehen.setAlignment(Align.center);
        tableTextLooks.add(textFieldCharAussehen).width(250F).height(30F);

        textFieldLookNumber = new TextField(lookCount + "/" + lookAvailable, tfStyle);
        textFieldLookNumber.setDisabled(true);
        textFieldLookNumber.setAlignment(Align.center);
        tableTextLookNumber.add(textFieldLookNumber).width(50F).height(50F);

        buttonPlay = new Button(skinButtonPlay);
        buttonPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                input = textFieldCharName.getText();
                System.out.println(input);
                main.setPlayer(new Player(input, 0, 0, lookCount));
                game.setScreen(main.getMainPlayingScreen());
            }
        });
        buttonPlay.addListener(new HoverListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
            }
        });
        buttonPlay.addListener(new HoverListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.exit(event, x, y, pointer, fromActor);
            }
        });
        tableButtonStart.add(buttonPlay);

        buttonArrowLeft = new TextButton("<", tbStyle);
        buttonArrowLeft.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                //Testing
                lookCount--;
                if(lookCount < 1) {
                    lookCount = lookAvailable;
                }
                updateText(textFieldLookNumber, lookCount + "/" + lookAvailable);
            }
        });
        buttonArrowLeft.getLabel().setFontScale(5F);
        buttonArrowLeft.pad(0F, 0F, 0F, 120F);
        tableButtonArrow.add(buttonArrowLeft);

        buttonArrowRight = new TextButton(">", tbStyle);
        buttonArrowRight.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                //Testing
                lookCount++;
                if(lookCount > lookAvailable) {
                    lookCount = 1;
                }
                updateText(textFieldLookNumber, lookCount + "/" + lookAvailable);
            }
        });
        buttonArrowRight.getLabel().setFontScale(5F);
        buttonArrowRight.pad(0F, 120F, 0F, 0F);
        tableButtonArrow.add(buttonArrowRight);

        //Debug code
        if(main.isDebug()) {
            stage.setDebugAll(true);
        }

        stage.addActor(tableButtonStart);
        stage.addActor(tableButtonArrow);
        stage.addActor(tableTextName);
        stage.addActor(tableTextLooks);
        stage.addActor(tablePictureChara);
        stage.addActor(tableTextLookNumber);
    }

    @Override
    public void render(float delta) {
        clearColorBuffer();

        stage.draw();
        stage.act();
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
        skinButtonPlay.dispose();
        font.dispose();
        stage.dispose();
    }

    public void updateText(TextField textField, String text) {
        textField.setText(text);
    }
}
