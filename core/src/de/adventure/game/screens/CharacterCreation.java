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
    private Table tableButtonStart, tableTextName, tableTextLooks, tablePictureChara, tableButtonArrow, tableTextLookNumber;
    private TextButton buttonStart, buttonArrowRight, buttonArrowLeft;
    private TextButton.TextButtonStyle tbStyle;
    private TextField textFieldCharName, textFieldNameHint, textFieldCharAussehen, textFieldLookNumber;
    private TextField.TextFieldStyle tfStyle;

    private String input;
    private int lookCount, lookAvailable;

    //Siehe Klasse "MainMenu" für Erklärungen
    public CharacterCreation(final Game game, final Main main) {
        super(game, main, "Character Creation");
        this.game = game;
        this.main = main;

        stage = new Stage();

        //Testing purposes
        lookCount = 1;
        lookAvailable = 5;

        //Placeholder
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        //TODO Eigene Skins für Buttons und Textfelder
        skin = new Skin();

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
        tableTextName.add(textFieldNameHint).width(250F).height(30F);

        textFieldCharName = new TextField("", tfStyle);
        textFieldCharName.setMaxLength(20);
        tableTextName.add(textFieldCharName).width(300F).height(30F);

        textFieldCharAussehen = new TextField("Wähle dein Aussehen:", tfStyle);
        textFieldCharAussehen.setDisabled(true);
        tableTextLooks.add(textFieldCharAussehen).width(250F).height(30F);

        textFieldLookNumber = new TextField(" " + lookCount + "/" + lookAvailable, tfStyle);
        textFieldLookNumber.setDisabled(true);
        tableTextLookNumber.add(textFieldLookNumber).width(50F).height(50F);

        buttonStart = new TextButton("Start", tbStyle);
        buttonStart.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                input = textFieldCharName.getText();
                System.out.println(input);
                game.setScreen(main.getMainMenu());
            }
        });
        buttonStart.getLabel().setFontScale(5F);
        buttonStart.pad(50F, 50F, 50F, 50F);
        tableButtonStart.add(buttonStart);

        buttonArrowLeft = new TextButton("<", tbStyle);
        buttonArrowLeft.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                //Testing
                lookCount--;
                if(lookCount < 1) {
                    lookCount = lookAvailable;
                }
                updateText(textFieldLookNumber, " " + lookCount + "/" + lookAvailable);
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
                updateText(textFieldLookNumber, " " + lookCount + "/" + lookAvailable);
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

    public void updateText(TextField textField, String text) {
        textField.setText(text);
    }
}
