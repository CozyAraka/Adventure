package de.adventure.game.input;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public abstract class HoverListener extends ClickListener{
    public HoverListener() {

    }
    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

    }
}
