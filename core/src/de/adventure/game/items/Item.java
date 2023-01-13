package de.adventure.game.items;

public abstract class Item {
    private String name, lore;
    private double maxDurability, curDurability;
    private int id;

    public enum ItemState {
        BROKEN,
        DAMAGED,
        NEW
    }
    private ItemState itemState;

    public enum ItemType {
        EQUIPPABLE_ITEM,
        USABLE_ITEM,
        QUEST_ITEM,
        SPECIAL_ITEM,
        TROPHY_ITEM
    }
    private ItemType itemType;

    public enum ItemCategory {
        HEALING_ITEM,
        DAMAGE_ITEM,
        SHIELDING_ITEM,
        VALUABLE_ITEM
    }
    private ItemCategory itemCategory;

    public enum ItemRarity {
        COMMON,
        UNCOMMON,
        RARE,
        MYSTIC,
        LEGENDARY,
        UNKNOWN

    }
    private ItemRarity itemRarity;

    //TODO Sprites
    //"Base" Item was alle weiteren Item Klassen wie auch Weapon benutzen
    public Item(String name, String lore, double maxDurability, double curDurability, ItemState itemState, ItemType itemType, ItemCategory itemCategory, ItemRarity itemRarity) {
        this.name = name;
        this.lore = lore;
        this.maxDurability = maxDurability;
        this.curDurability = curDurability;
        this.itemState = itemState;
        this.itemType = itemType;
        this.itemCategory = itemCategory;
        this.itemRarity = itemRarity;
        id = 0;
    }

    public void useItem(double damage) {
        if((getItemState() == ItemState.NEW || getItemState() == ItemState.DAMAGED)) {
            this.curDurability -= damage;
            if(curDurability < 0D) {
                curDurability = 0D;
            }
            checkItemState();
        }else {
            //Item kann nicht mehr benutzt werden
        }
    }

    public void repairItem(double repairBy) {
        if(curDurability < maxDurability) {
            this.curDurability += repairBy;
            if(curDurability > maxDurability) {
                curDurability = maxDurability;
            }
        }else {
            System.out.println("Item already at maximum durability!");
        }
    }

    //TODO Sprite zu "broken" & "damaged" setzen
    public void checkItemState() {
        if(curDurability == 0) {
            setItemState(ItemState.BROKEN);
        }else if(curDurability <= maxDurability) {
            setItemState(ItemState.DAMAGED);
        }
    }

    public String getName() {
        return name;
    }

    public String getLore() {
        return lore;
    }

    public double getMaxDurability() {
        return maxDurability;
    }

    public double getCurDurability() {
        return curDurability;
    }

    public void setId(int number) {
        id = number;
    }

    public int getId() {
        return id;
    }

    public ItemState getItemState() {
        return itemState;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public ItemRarity getItemRarity() {
        return itemRarity;
    }

    public void setItemState(ItemState state) {
        switch(state) {
            case NEW:
                itemState = ItemState.NEW;
                break;

            case BROKEN:
                itemState = ItemState.BROKEN;
                break;

            case DAMAGED:
                itemState = ItemState.DAMAGED;
                break;

            default:
                itemState = ItemState.NEW;
                break;
        }
    }
}
