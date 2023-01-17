package de.adventure.game.entities.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.adventure.game.entities.Entity;

public class Player extends Entity {
    private int playerSkin;

    public Player(String name, float xCord, float yCord, int playerSkin) {
        super(name, "", EntityType.HERO, EntityStatus.IDLE, xCord, yCord);

        this.playerSkin = playerSkin;
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(Color.RED);
        renderer.rect(getXCord(), getYCord(), 12*4, 12*4);
    }
}
