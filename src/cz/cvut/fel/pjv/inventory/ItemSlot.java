package cz.cvut.fel.pjv.inventory;

import cz.cvut.fel.pjv.item.Item;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
@Getter
@Setter
public class ItemSlot {
    private Rectangle slot;
    private Optional<Item> item;

    public ItemSlot(Rectangle slot, Optional<Item> item) {
        this.slot = slot;
        this.item = item;
    }
}
