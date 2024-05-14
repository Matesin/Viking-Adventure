package cz.cvut.fel.pjv.inventory;

import cz.cvut.fel.pjv.gameloop.Constants;
import cz.cvut.fel.pjv.item.Item;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import static cz.cvut.fel.pjv.gameloop.Constants.Inventory.*;

import java.util.Optional;

@Getter
@Setter
@Slf4j
public class ItemSlot {
    private Pane inventorySlot;
    private Optional<Item> item;
    private Optional<Image> itemImage;
    private final double slotSize;
    private final double padding;
    private final double firstSlotX;
    private final double firstSlotY;

    public ItemSlot(Optional<Item> item, int offset, double slotSize, double padding, double firstSlotX, double firstSlotY) {
        this.slotSize = slotSize;
        this.padding = padding;
        this.firstSlotX = firstSlotX;
        this.firstSlotY = firstSlotY;
        this.inventorySlot = initSlot(offset);
        this.item = item;
        this.itemImage = item.map(Item::getInventoryImage);
        log.debug("Image {} loaded", itemImage);
        initImage();
    }

    private Pane initSlot(int offset){
        Pane slot = new Pane();
        slot.setPrefSize(slotSize, slotSize);
        slot.setStyle("-fx-background-color: white; -fx-opacity: 0.5; -fx-background-radius: 20;");
        slot.setLayoutX(firstSlotX + (offset % INITIAL_INVENTORY_CAPACITY) * (slotSize + padding));
        int row = (int) Math.floor((double) offset / INITIAL_INVENTORY_CAPACITY);
        slot.setLayoutY(firstSlotY + row * (slotSize + padding));
        return slot;
    }

    private void initImage() {
        itemImage.ifPresent(image -> {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(slotSize);
            imageView.setFitHeight(slotSize);
            inventorySlot.getChildren().add(imageView);
        });
    }
}