package de.adventure.game.items.weapons.slashing;

import de.adventure.game.items.Item;
import de.adventure.game.items.weapons.Weapon;

import java.util.ArrayList;

public class UncommonSlashing {
    private Weapon woodenSword, stoneSword, ironSword;
    private ArrayList<Weapon> list;

    public UncommonSlashing() {
        woodenSword = new Weapon(
                "Holzschwert",
                "Nur ein Holzschwert, was habt ihr erwartet?",
                10.0D,
                10.0D,
                1.0D,
                Item.ItemState.NEW,
                Item.ItemCategory.DAMAGE_ITEM,
                Item.ItemRarity.COMMON,
                Weapon.WeaponCategory.SLASHING_WEAPON);

        list = new ArrayList<>();
        list.add(woodenSword);
    }

    public ArrayList<Weapon> getList() {
        return list;
    }
}
