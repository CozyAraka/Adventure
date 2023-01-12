package de.adventure.game.items;

import de.adventure.game.items.weapons.Weapon;
import de.adventure.game.items.weapons.blunt.*;
import de.adventure.game.items.weapons.slashing.*;

import java.util.ArrayList;

public class ItemCollector {
    //Item Liste
    private ArrayList<Item> itemList;
    //private ArrayList<Weapon> itemListUsables;
    //private ArrayList<Weapon> itemListHealing;

    //Alle Items
    private CommonSlashing commonSlashing;
    private UncommonSlashing uncommonSlashing;
    private RareSlashing rareSlashing;
    private MysticSlashing mysticSlashing;
    private LegendarySlashing legendarySlashing;

    private CommonBlunt commonBlunt;
    private UncommonBlunt uncommonBlunt;
    private RareBlunt rareBlunt;
    private MysticBlunt mysticBlunt;
    private LegendaryBlunt legendaryBlunt;

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

    public void registerItems() {
        int count = 0;
        for(Item item : itemList) {
            item.setId(count);
            System.out.println(item.getName() + " registered with ID: " + count);
            count++;
        }
        System.out.println("\n" + count + " Items loaded! \n");
    }
}
