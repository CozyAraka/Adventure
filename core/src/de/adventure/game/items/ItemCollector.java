package de.adventure.game.items;

import de.adventure.game.items.weapons.slashing.CommonSlashing;
import de.adventure.game.items.weapons.slashing.UncommonSlashing;

import java.util.ArrayList;

public class ItemCollector {
    private ArrayList<Item> itemList;
    private CommonSlashing commonSlashing;
    private UncommonSlashing uncommonSlashing;

    public ItemCollector() {
        itemList = new ArrayList<>();
        commonSlashing = new CommonSlashing();
        uncommonSlashing = new UncommonSlashing();

        itemList.addAll(commonSlashing.getList());
        itemList.addAll(uncommonSlashing.getList());
    }

    public void test() {
        for(Item item : itemList) {
            System.out.println(item.getName() + " registered!");
        }
        System.out.println("");
    }
}
