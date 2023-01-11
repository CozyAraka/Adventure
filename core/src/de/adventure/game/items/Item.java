package de.adventure.game.items;

public class Item {
    private String name, lore, type;
    private double maxDurability, curDurability;

    public enum ItemState {
        BROKEN,
        DAMAGED,
        NEW
    }
    private static ItemState itemState;

    //TODO Sprites
    public Item(String name, String lore, double maxDurability, double curDurability, String type) {
        this.name = name;
        this.lore = lore;
        this.maxDurability = maxDurability;
        this.curDurability = curDurability;
        this.type = type;

        setItemState(ItemState.NEW);
    }

    public void useItem(double damage) {
        if(getItemState() == ItemState.NEW || getItemState() == ItemState.DAMAGED) {
            this.curDurability -= damage;
            checkItemState();
        }else {
            //Item kann nicht mehr benutzt werden
        }
    }

    public void repairItem(double repairBy) {
        this.curDurability += repairBy;
    }

    //TODO Sprite zu "broken" & "damaged" setzen
    public void checkItemState() {
        if(curDurability == 0) {
            setItemState(ItemState.BROKEN);
        }else if(curDurability <= maxDurability) {
            setItemState(ItemState.DAMAGED);
        }
    }

    public static ItemState getItemState() {
        return itemState;
    }

    public static void setItemState(ItemState state) {
        switch(state) {
            case NEW:
                break;

            case BROKEN:
                break;

            case DAMAGED:
                break;

            default:
                itemState = ItemState.NEW;
                break;
        }
    }
}
