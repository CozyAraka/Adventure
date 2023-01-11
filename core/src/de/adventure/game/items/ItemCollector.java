package de.adventure.game.items;

import de.adventure.game.items.weapons.blunt.*;
import de.adventure.game.items.weapons.slashing.*;

import java.util.ArrayList;

public class ItemCollector {
    private ArrayList<Item> itemList;
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

    public void addToList() {
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

    public void loadedItems() {
        int count = 0;
        for(Item item : itemList) {
            System.out.println(item.getName() + " registered!");

            count++;
        }
        System.out.println("\n" + count + " Items loaded! \n");
    }
}
