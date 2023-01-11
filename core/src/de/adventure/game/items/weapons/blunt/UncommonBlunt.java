package de.adventure.game.items.weapons.blunt;

import de.adventure.game.items.Item;
import de.adventure.game.items.weapons.Weapon;

import java.util.ArrayList;

public class UncommonBlunt {
    private Weapon woodenHammer, stoneHammer, ironHammer;
    private ArrayList<Weapon> list;

    public UncommonBlunt() {
        woodenHammer = new Weapon(
                "Uncommon Holzhammer",
                "Nur ein Holzhammer, was habt ihr erwartet?",
                12.0D,
                12.0D,
                1.5D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.UNCOMMON,
                Weapon.WeaponCategory.BLUNT_WEAPON);

        stoneHammer = new Weapon(
                "Uncommon Steinhammer",
                "Ein schwerer Steinhammer, scheint ziemlich hart zu sein.",
                19.0D,
                19.0D,
                3.0D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.UNCOMMON,
                Weapon.WeaponCategory.BLUNT_WEAPON);

        ironHammer = new Weapon(
                "Uncommon Eisenhammer",
                "Der erste wirkliche Hammer, endlich was stabiles!",
                25.0D,
                25.0D,
                7.5D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.UNCOMMON,
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
