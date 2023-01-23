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

        statues.add(new Statue("\n \n Nothing found", 100, 20, -1));
        statues.add(new Statue("\n \n Nutze STRG um langsamer zu laufen!", 100, 20, 0));
        statues.add(new Statue("\n \n Nutze Shift um zu rennen!", 100, 20, 1));
        statues.add(new Statue("\n \n W A S D ist dein Freund, Pfeiltasten koennten jedoch auch von nutzen sein...", 100, 20, 2));
        statues.add(new Statue("\n \n Halte dich von Monstern fern!", 100, 20, 3));
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
        for(Statue statue : statues) {
            if(statue.getID() == id) {
                statue.throwText(stage);
                return statue;
            }
        }
        return statue;
    }
}

