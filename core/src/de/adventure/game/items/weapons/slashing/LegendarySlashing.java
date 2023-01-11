package de.adventure.game.items.weapons.slashing;

import de.adventure.game.items.Item;
import de.adventure.game.items.weapons.Weapon;

import java.util.ArrayList;

public class LegendarySlashing {
    private Weapon woodenSword, stoneSword, ironSword;
    private ArrayList<Weapon> list;

    public LegendarySlashing() {
        woodenSword = new Weapon(
                "Legendary Holzschwert",
                "Nur ein Holzschwert, was habt ihr erwartet?",
                20.0D,
                20.0D,
                3.0D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.LEGENDARY,
                Weapon.WeaponCategory.SLASHING_WEAPON);

        stoneSword = new Weapon(
                "Legendary Steinschwert",
                "Ein schweres Steinschwert, scheint ziemlich hart zu sein.",
                25.0D,
                25.0D,
                4.5D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.LEGENDARY,
                Weapon.WeaponCategory.SLASHING_WEAPON);

        ironSword = new Weapon(
                "Legendary Eisenschwert",
                "Das erste wirkliche Schwert, endlich was scharfes!",
                30.0D,
                30.0D,
                9.0D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.LEGENDARY,
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
