package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.adventure.game.Main;

public class ScreenBase implements Screen {
    protected final Game game;
    protected final Main main;
    private final String name;

    //Neuer Screen mit einem Namen und der Game instanz
    public ScreenBase(Game game, Main main, String name){
        this.game = game;
        this.main = main;
        this.name = name;

    }

    //Leert den Pixel buffer des Programms, dies muss in jeder Render Methode vorhanden sein, damit sich nichts dupliziert
    public void clearColorBuffer() {
        if(Gdx.graphics.isGL30Available()) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
            Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        }else {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }
    }

    public Table createTable(float boundsWidth, float boundsHeight, boolean center, float x, float y) {
        Table table = new Table();
        table.setBounds(0, 0, boundsWidth, boundsHeight);
        if(center) {
            table.setX((float) (Gdx.graphics.getWidth() / 2) - (boundsWidth / 2));
        }else {
            table.setX(x);
        }
        table.setY(y);

        return table;
    }

    public Animation<TextureRegion> createAnimation(float frameDuration, int columns, int rows, String path) {
        return new Animation<>(frameDuration, createTextureRegion(columns, rows, new Texture(path)));
    }

    public TextureRegion[] createTextureRegion(int columns, int rows, Texture texture) {
        TextureRegion[][] textureRegion = TextureRegion.split(texture, texture.getWidth() / columns, texture.getHeight() / rows);
        TextureRegion[] walkFrames = new TextureRegion[columns * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                walkFrames[index++] = textureRegion[i][j];
            }
        }
        return walkFrames;
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public String getName() {
        return name;
    }
}
