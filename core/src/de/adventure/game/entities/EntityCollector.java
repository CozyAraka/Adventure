package de.adventure.game.entities;

import de.adventure.game.entities.monsters.thief.ThiefList;

import java.util.ArrayList;

public class EntityCollector {
    private ArrayList<Entity> entityList;

    private ThiefList thiefList;

    public EntityCollector() {
        entityList = new ArrayList<>();

        thiefList = new ThiefList();

        addToList();

    }

    public void addToList() {
        entityList.add(new Entity("null_entity", "null", Entity.EntityType.NPC, Entity.EntityStatus.DEAD));

        entityList.addAll(thiefList.getList());
    }

    public void registerEntities() {
        int count = 0;
        for(Entity entity : entityList) {
            entity.setId(count);
            System.out.println(entity.getName() + " registered with ID: " + count);
            count++;
        }
        System.out.println("\n" + count + " Entities loaded! \n");
    }
}
