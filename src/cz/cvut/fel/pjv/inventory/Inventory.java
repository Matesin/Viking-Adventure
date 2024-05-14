package cz.cvut.fel.pjv.inventory;

import cz.cvut.fel.pjv.item.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inventory {
    private Item[] items;
    private boolean isFull;
    private int capacity;
    private Item pickedItem;

    public Inventory(int capacity) {
        this.capacity = capacity;
        this.items = new Item[capacity];
        this.pickedItem = null;
    }

}
