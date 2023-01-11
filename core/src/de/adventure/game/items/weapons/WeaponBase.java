package de.adventure.game.items.weapons;

import de.adventure.game.items.Item;

public class WeaponBase extends Item {
    private double damage;

    public WeaponBase(String name, String lore, double maxDurability, double curDurability, double damage, String type) {
        super(name, lore, maxDurability, curDurability, type);
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }
}
