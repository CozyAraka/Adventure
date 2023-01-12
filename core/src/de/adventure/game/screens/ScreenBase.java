package de.adventure.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import de.adventure.game.Main;

public class ScreenBase implements Screen {
    protected final Game game;
    protected final Main main;
    private String name;

    public ScreenBase(Game game, Main main, String name){
        this.game = game;
        this.main = main;
        this.name = name;

    }

    public void clearColorBuffer() {
        if(Gdx.graphics.isGL30Available()) {
            Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        }else {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }
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
