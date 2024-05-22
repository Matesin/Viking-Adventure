package cz.cvut.fel.pjv.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fel.pjv.item.Item;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representing the inventory of the player.
 * The inventory contains items and has a capacity.
 */
@Getter
@Setter
public class Inventory {
    private Item[] items;
    private boolean isFull;
    private boolean isEmpty;
    private int capacity;
    private Item pickedItem;

    /**
     * Constructor for Inventory with specified capacity.
     *
     * @param capacity the capacity of the inventory
     */
    public Inventory(int capacity) {
        this.capacity = capacity;
        this.items = new Item[capacity];
        this.isEmpty = true;
        this.isFull = false;
        this.pickedItem = null;
    }

    /**
     * Constructor for Inventory with specified items, capacity, and picked item.
     *
     * @param items the items in the inventory
     * @param isFull true if the inventory is full, false otherwise
     * @param isEmpty true if the inventory is empty, false otherwise
     * @param capacity the capacity of the inventory
     * @param pickedItem the picked item
     */
    @JsonCreator
    public Inventory(@JsonProperty("items") Item[] items, @JsonProperty("isFull") boolean isFull, @JsonProperty("isEmpty") boolean isEmpty, @JsonProperty("capacity") int capacity, @JsonProperty("pickedItem") Item pickedItem) {
        this.items = items;
        this.isFull = isFull;
        this.isEmpty = isEmpty;
        this.capacity = capacity;
        this.pickedItem = pickedItem;
    }

    /**
     * Removes an item from the inventory.
     */
    public void removeItem(Item item){
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                items[i] = null;
                break;
            }
        }
    }

    /**
     * Adds an item to the inventory.
     */
    public void addItem(Item item){
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                break;
            }
        }
    }
}
