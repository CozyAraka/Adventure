package de.adventure.game.entities.statue;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class StatueList{
    private ArrayList<Statue> statues;

    private Statue statue;

    public StatueList() {
        addStatues();
    }

    public void addStatues() {
        statues = new ArrayList<>();

        statues.add(new Statue("\n Nothing found", 100, 20, -1));
        statues.add(new Statue("\n Daddy", 100, 20, 0));
        statues.add(new Statue("\n Working", 100, 20, 1));
        statues.add(new Statue("\n Working", 100, 20, 2));
    }

    public Statue getStatueByID(int id) {
        for (Statue statue : statues) {
            if(statue.getID() == id) {
                return statue;
            }
        }
        return statues.get(0);
    }

    public Statue handleStatues(int id, Stage stage) {
        switch(id) {
            case 0:
                statue = getStatueByID(0);
                statue.throwText(stage);
                break;

            case 1:
                statue = getStatueByID(1);
                statue.throwText(stage);
                break;

            case 2:
                statue = getStatueByID(2);
                statue.throwText(stage);
                break;

        }
        return statue;
    }
}

