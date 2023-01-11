package de.adventure.game.entities;

public class Entity {
    private String name, description;

    public enum EntityType {
        HERO,
        MONSTER,
        NPC
    }
    private EntityType entityType;

    public Entity(String name, String description, EntityType entityType) {
        this.name = name;
        this.description = description;
        this.entityType = entityType;

    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
