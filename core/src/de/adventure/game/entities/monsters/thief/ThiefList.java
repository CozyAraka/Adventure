package de.adventure.game.entities.monsters.thief;

import java.util.ArrayList;

public class ThiefList {
    private ThiefMonster thiefMonsterVariation1, thiefMonsterVariation2, thiefMonsterVariation3, thiefMonsterVariation4;
    private ArrayList<ThiefMonster> list;

    public ThiefList() {
        thiefMonsterVariation1 = new ThiefMonster();

        thiefMonsterVariation2 = new ThiefMonster();

        thiefMonsterVariation3 = new ThiefMonster();

        thiefMonsterVariation4 = new ThiefMonster();

        list = new ArrayList<>();
        list.add(thiefMonsterVariation1);
        list.add(thiefMonsterVariation2);
        list.add(thiefMonsterVariation3);
        list.add(thiefMonsterVariation4);
    }

    public ArrayList<ThiefMonster> getList() {
        return list;
    }
}
