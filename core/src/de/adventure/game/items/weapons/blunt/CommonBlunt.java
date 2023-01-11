package de.adventure.game.items.weapons.blunt;

import de.adventure.game.items.Item;
import de.adventure.game.items.weapons.Weapon;

import java.util.ArrayList;

public class CommonBlunt {
    private Weapon woodenHammer, stoneHammer, ironHammer;
    private ArrayList<Weapon> list;

    public CommonBlunt() {
        woodenHammer = new Weapon(
                "Common Holzhammer",
                "Nur ein Holzhammer, was habt ihr erwartet?",
                10.0D,
                10.0D,
                1.0D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.COMMON,
                Weapon.WeaponCategory.BLUNT_WEAPON);

        stoneHammer = new Weapon(
                "Common Steinhammer",
                "Ein schwerer Steinhammer, scheint ziemlich hart zu sein.",
                15.0D,
                15.0D,
                2.0D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.COMMON,
                Weapon.WeaponCategory.BLUNT_WEAPON);

        ironHammer = new Weapon(
                "Common Eisenhammer",
                "Der erste wirkliche Hammer, endlich was stabiles!",
                20.0D,
                20.0D,
                5.0D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.COMMON,
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
