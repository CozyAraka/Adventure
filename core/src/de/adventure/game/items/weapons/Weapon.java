package de.adventure.game.items.weapons;

import de.adventure.game.items.Item;

import java.util.ArrayList;

public class Weapon extends Item {
    private static ArrayList<Weapon> weaponList = new ArrayList<>();
    private double weaponDamage;
    private String name;

    public enum WeaponCategory {
        SLASHING_WEAPON,
        BLUNT_WEAPON,
        STABBING_WEAPON,
        RANGED_WEAPON
    }
    private WeaponCategory weaponCategory;

    public Weapon(String name, String lore, double maxDurability, double curDurability, double weaponDamage, ItemState itemState, ItemCategory itemCategory, ItemRarity itemRarity, WeaponCategory weaponCategory) {
        super(name, lore, maxDurability, curDurability, itemState, ItemType.EQUIPPABLE_ITEM, itemCategory, itemRarity);

        this.weaponDamage = weaponDamage;
        this.weaponCategory = weaponCategory;
        this.name = name;
    }

    public double getWeaponDamage() {
        return weaponDamage;
    }

    public WeaponCategory getWeaponCategory() {
        return weaponCategory;
    }

    public static ArrayList<Weapon> getWeaponList() {
        return weaponList;
    }
}
