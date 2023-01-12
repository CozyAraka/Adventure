package de.adventure.game.entities;

public class Entity {
    private String name, description;
    private int Id;

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

    public Entity(String name, String description, EntityType entityType, EntityStatus entityStatus) {
        this.name = name;
        this.description = description;
        this.entityType = entityType;
        this.entityStatus = entityStatus;
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
}
