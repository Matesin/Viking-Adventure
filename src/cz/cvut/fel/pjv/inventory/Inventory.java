package cz.cvut.fel.pjv.inventory;

import cz.cvut.fel.pjv.item.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inventory {
    private Item[] items;
    private boolean isFull;
    private boolean isEmpty;
    private int capacity;
    private Item pickedItem;

    public Inventory(int capacity) {
        this.capacity = capacity;
        this.items = new Item[capacity];
        this.isEmpty = true;
        this.isFull = false;
        this.pickedItem = null;
    }
    public void removeItem(Item item){
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                items[i] = null;
                break;
            }
        }
    }
    public void addItem(Item item){
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                break;
            }
        }
    }
}
