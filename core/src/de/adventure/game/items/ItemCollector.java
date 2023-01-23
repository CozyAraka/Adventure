package de.adventure.game.items;

import de.adventure.game.items.weapons.Weapon;
import de.adventure.game.items.weapons.blunt.*;
import de.adventure.game.items.weapons.slashing.*;

import java.util.ArrayList;

public class ItemCollector {
    //Item Liste
    private final ArrayList<Item> itemList;
    //private ArrayList<Weapon> itemListUsables;
    //private ArrayList<Weapon> itemListHealing;

    //Alle Items
    private final CommonSlashing commonSlashing;
    private final UncommonSlashing uncommonSlashing;
    private final RareSlashing rareSlashing;
    private final MysticSlashing mysticSlashing;
    private final LegendarySlashing legendarySlashing;

    private final CommonBlunt commonBlunt;
    private final UncommonBlunt uncommonBlunt;
    private final RareBlunt rareBlunt;
    private final MysticBlunt mysticBlunt;
    private final LegendaryBlunt legendaryBlunt;

    public ItemCollector() {
        itemList = new ArrayList<>();
        //itemListUsables = new ArrayList<>();
        //itemListHealing = new ArrayList<>();

        //Slashing
        commonSlashing = new CommonSlashing();
        uncommonSlashing = new UncommonSlashing();
        rareSlashing = new RareSlashing();
        mysticSlashing = new MysticSlashing();
        legendarySlashing = new LegendarySlashing();

        //Blunt
        commonBlunt = new CommonBlunt();
        uncommonBlunt = new UncommonBlunt();
        rareBlunt = new RareBlunt();
        mysticBlunt = new MysticBlunt();
        legendaryBlunt = new LegendaryBlunt();

        addToList();
    }

    public Item getItem(int id) {
        for(Item item : itemList) {
            if(item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    //Fügt alle gesammelten Arrays dem Hauptarray hinzu
    public void addToList() {
        //Dummy Item
        itemList.add(new Weapon("null_item", "null", 0, 0 , 0, Item.ItemState.BROKEN, Item.ItemCategory.VALUABLE_ITEM, Item.ItemRarity.UNKNOWN, Weapon.WeaponCategory.SLASHING_WEAPON));

        //Slashing
        itemList.addAll(commonSlashing.getList());
        itemList.addAll(uncommonSlashing.getList());
        itemList.addAll(rareSlashing.getList());
        itemList.addAll(mysticSlashing.getList());
        itemList.addAll(legendarySlashing.getList());

        //Slashing
        itemList.addAll(commonBlunt.getList());
        itemList.addAll(uncommonBlunt.getList());
        itemList.addAll(rareBlunt.getList());
        itemList.addAll(mysticBlunt.getList());
        itemList.addAll(legendaryBlunt.getList());
    }

    //Registriert alle Items
    public void registerItems() {
        int count = 0;
        for(Item item : itemList) {
            //Schaut, ob die Instanz im array die benutzt wird, ein Objekt der Klasse ist die hinter instanceof steht
            if(item instanceof Weapon) {
                Weapon weapon = (Weapon) itemList.get(count);
                System.out.println(weapon.getWeaponDamage());
            }
            item.setId(count);
            System.out.println(item.getName() + " registered with ID: " + count);
            count++;
        }
        System.out.println("\n" + count + " Items loaded! \n");
    }
}
