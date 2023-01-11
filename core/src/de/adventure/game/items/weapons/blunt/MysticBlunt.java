package de.adventure.game.items.weapons.blunt;

import de.adventure.game.items.Item;
import de.adventure.game.items.weapons.Weapon;

import java.util.ArrayList;

public class MysticBlunt {
    private Weapon woodenHammer, stoneHammer, ironHammer;
    private ArrayList<Weapon> list;

    public MysticBlunt() {
        woodenHammer = new Weapon(
                "Mystic Holzhammer",
                "Nur ein Holzhammer, was habt ihr erwartet?",
                20.0D,
                20.0D,
                3.0D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.MYSTIC,
                Weapon.WeaponCategory.BLUNT_WEAPON);

        stoneHammer = new Weapon(
                "Mystic Steinhammer",
                "Ein schwerer Steinhammer, scheint ziemlich hart zu sein.",
                25.0D,
                25.0D,
                4.5D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.MYSTIC,
                Weapon.WeaponCategory.BLUNT_WEAPON);

        ironHammer = new Weapon(
                "Mystic Eisenhammer",
                "Der erste wirkliche Hammer, endlich was stabiles!",
                30.0D,
                30.0D,
                9.0D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.MYSTIC,
                Weapon.WeaponCategory.BLUNT_WEAPON);

        list = new ArrayList<>();
        list.add(woodenHammer);
        list.add(stoneHammer);
        list.add(ironHammer);
    }

    public ArrayList<Weapon> getList() {
        return list;
    }
}
