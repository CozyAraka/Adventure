package de.adventure.game.items.weapons.slashing;

import de.adventure.game.items.Item;
import de.adventure.game.items.weapons.Weapon;

import java.util.ArrayList;

public class CommonSlashing {
    private Weapon woodenSword, stoneSword, ironSword;
    private ArrayList<Weapon> list;

    public CommonSlashing() {
        woodenSword = new Weapon(
                "Common Holzschwert",
                "Nur ein Holzschwert, was habt ihr erwartet?",
                10.0D,
                10.0D,
                1.0D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.COMMON,
                Weapon.WeaponCategory.SLASHING_WEAPON);

        stoneSword = new Weapon(
                "Common Steinschwert",
                "Ein schweres Steinschwert, scheint ziemlich hart zu sein.",
                15.0D,
                15.0D,
                2.0D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.COMMON,
                Weapon.WeaponCategory.SLASHING_WEAPON);

        ironSword = new Weapon(
                "Common Eisenschwert",
                "Das erste wirkliche Schwert, endlich was scharfes!",
                20.0D,
                20.0D,
                5.0D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.COMMON,
                Weapon.WeaponCategory.SLASHING_WEAPON);

        list = new ArrayList<>();
        list.add(woodenSword);
        list.add(stoneSword);
        list.add(ironSword);
    }

    public ArrayList<Weapon> getList() {
        return list;
    }
}
