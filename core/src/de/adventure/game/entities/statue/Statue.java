package de.adventure.game.entities.statue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import de.adventure.game.entities.Entity;

import java.time.LocalTime;

public class Statue extends Entity {
    private String textPrompt;
    private int id;

    private Skin skin;

    public Statue(String textPrompt, float x, float y, int id) {
        super("Wise Statue", "Tells useful tips", EntityType.NPC, EntityStatus.IDLE, x, y);

        this.textPrompt = textPrompt;
        this.id = id;
        skin = new Skin(Gdx.files.internal("textures/TextField/textField.json"));
    }

    public TextArea throwText(Stage stage) {
        TextArea textArea = new TextArea(null, skin);
        textArea.setPosition(getXCord(), getYCord());
        textArea.setTouchable(Touchable.disabled);
        textArea.setAlignment(Align.center);
        textArea.setMessageText(textPrompt);
        textArea.setWidth(Gdx.graphics.getWidth() - getXCord() * 2);
        textArea.setHeight(200F);

        stage.addActor(textArea);
        Gdx.app.debug(LocalTime.now() + "", "Thrown Statue Text");
        return textArea;

    }

    public void endText(Stage stage) {
        Array<Actor> actors = stage.getActors();
        for(Actor actor : actors) {
            if(actor instanceof TextArea) {
                actor.remove();
            }

        }
        Gdx.app.debug(LocalTime.now() + "", "Ended Statue Text");
    }

    public int getID() {
        return id;
    }

}
