package de.adventure.game.entities.monsters.thief;

import de.adventure.game.entities.monsters.MonsterBase;

public class ThiefMonster extends MonsterBase {
    //Erstellt einen neuen Dieb
    public ThiefMonster(String name, double damage, double hitpoints, double agility, double defense, double mana, double luck, double accuracy) {
        super(name, "Nur ein kleiner Dieb", damage, hitpoints, agility, defense, mana, luck, accuracy);
    }

    //Fallback Thief
    public ThiefMonster() {
        super("Thief", "Nur ein kleiner Dieb", 1, 10, 30, 5, 5, 5, 10);
    }
}
