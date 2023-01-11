package de.adventure.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextThings {
    public TextThings() {

    }
    public void onRender(SpriteBatch batch, BitmapFont font) {
        batch.begin();
        //font.draw(batch, "" + Gdx.graphics.getFramesPerSecond(), 400, 100);
        batch.end();

    }
}
