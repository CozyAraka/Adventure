package de.adventure.game.items.weapons.slashing;

import de.adventure.game.items.Item;
import de.adventure.game.items.weapons.Weapon;

import java.util.ArrayList;

public class UncommonSlashing {
    private Weapon woodenSword, stoneSword, ironSword;
    private ArrayList<Weapon> list;

    public UncommonSlashing() {
        woodenSword = new Weapon(
                "Uncommon Holzschwert",
                "Nur ein Holzschwert, was habt ihr erwartet?",
                12.0D,
                12.0D,
                1.5D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.UNCOMMON,
                Weapon.WeaponCategory.SLASHING_WEAPON);

        stoneSword = new Weapon(
                "Uncommon Steinschwert",
                "Ein schweres Steinschwert, scheint ziemlich hart zu sein.",
                19.0D,
                19.0D,
                3.0D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.UNCOMMON,
                Weapon.WeaponCategory.SLASHING_WEAPON);

        ironSword = new Weapon(
                "Uncommon Eisenschwert",
                "Das erste wirkliche Schwert, endlich was scharfes!",
                25.0D,
                25.0D,
                7.5D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.UNCOMMON,
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
