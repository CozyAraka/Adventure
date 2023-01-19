package de.adventure.game.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity extends Actor {
    private String name, description;

    private int Id;

    private float xCord, yCord;

    public enum EntityType {
        HERO,
        MONSTER,
        NPC
    }
    private EntityType entityType;

    public enum EntityStatus {
        DEAD,
        IDLE,
        ATTACK
    }
    private EntityStatus entityStatus;

    public Entity(String name, String description, EntityType entityType, EntityStatus entityStatus, float xCord, float yCord) {
        this.name = name;
        this.description = description;
        this.entityType = entityType;
        this.entityStatus = entityStatus;
        this.xCord = xCord;
        this.yCord = yCord;

        Id = 0;

    }

    public EntityType getEntityType() {
        return entityType;
    }

    public EntityStatus getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(EntityStatus status) {
        entityStatus = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int number) {
        Id = number;
    }

    public int getId() {
        return Id;
    }

    public float getXCord() {
        return xCord;
    }

    public float getYCord() {
        return yCord;
    }

    public void updatePosition(float x, float y) {
        xCord = x;
        yCord = y;

    }
}
