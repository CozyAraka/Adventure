package de.adventure.game.entities.monsters;

import de.adventure.game.entities.Entity;

public class MonsterBase extends Entity {
    private double damage, hitpoints, agility, defense, mana, luck, accuracy;

    public MonsterBase(String name, String description, double damage, double hitpoints, double agility, double defense, double mana, double luck, double accuracy) {
        super(name, description, EntityType.MONSTER, EntityStatus.IDLE);

        this.damage = damage;
        this.hitpoints = hitpoints;
        this.agility = agility;
        this.defense = defense;
        this.mana = mana;
        this.luck = luck;
        this.accuracy = accuracy;
    }

    public double getDamage() {
        return damage;
    }

    public double getHitpoints() {
        return hitpoints;
    }

    public double getAgility() {
        return agility;
    }

    public double getDefense() {
        return defense;
    }

    public double getMana() {
        return mana;
    }

    public double getLuck() {
        return luck;
    }
}
