package cz.cvut.fel.pjv.inventory;

import cz.cvut.fel.pjv.gameloop.Constants;
import cz.cvut.fel.pjv.item.Item;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import static cz.cvut.fel.pjv.gameloop.Constants.Inventory.*;

import java.util.Optional;
@Getter
@Setter
@Slf4j
public class ItemSlot {
    private final Rectangle slot;
    private Optional<Item> item;
    private Optional<Image> itemImage;

    public ItemSlot(Optional<Item> item, int offset) {
        this.slot = initSlot(offset);
        this.item = item;
        this.itemImage = item.map(Item::getInventoryImage);
        log.debug("Image {} loaded", itemImage);
    }
    private Rectangle initSlot(int offset){
        Rectangle slot = new Rectangle(SLOT_SIZE, SLOT_SIZE);
        slot.setFill(Color.WHITE);
        slot.setOpacity(0.5);
        slot.setArcWidth(20);
        slot.setArcHeight(20);
        slot.setLayoutX(FIRST_SLOT_X + (offset % 5) * (Constants.Inventory.SLOT_SIZE + Constants.Inventory.SLOT_PADDING));
        int row = (int) Math.floor((double) offset / 5);
        slot.setLayoutY(FIRST_SLOT_Y + row * (Constants.Inventory.SLOT_SIZE + Constants.Inventory.SLOT_PADDING));
        return slot;
    }
}
