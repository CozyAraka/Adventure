package de.adventure.game.entities;

import de.adventure.game.entities.monsters.thief.ThiefList;
import de.adventure.game.entities.monsters.thief.ThiefMonster;

import java.util.ArrayList;

public class EntityCollector {
    private ArrayList<Entity> entityList;

    private ThiefList thiefList;

    public EntityCollector() {
        entityList = new ArrayList<>();

        thiefList = new ThiefList();

        addToList();

    }

    //Fügt alle gesammelten Arrays dem Hauptarray hinzu
    public void addToList() {
        entityList.add(new Entity("null_entity", "null", Entity.EntityType.NPC, Entity.EntityStatus.DEAD, 0F, 0F));

        entityList.addAll(thiefList.getList());
    }

    //Registriert alle Entities
    public void registerEntities() {
        int count = 0;
        for(Entity entity : entityList) {
            //Schaut, ob die Instanz im array die benutzt wird, ein Objekt der Klasse ist die hinter instanceof steht
            if(entity instanceof ThiefMonster) {
                ThiefMonster thiefMonster = (ThiefMonster) entityList.get(count);
                System.out.println(thiefMonster.getAgility());
            }
            entity.setId(count);
            System.out.println(entity.getName() + " registered with ID: " + count);
            count++;
        }
        System.out.println("\n" + count + " Entities loaded! \n");
    }
}
