package cz.cvut.fel.pjv.inventory;

import cz.cvut.fel.pjv.item.Item;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Class representing an item slot in the inventory.
 * The item slot contains an item and an image of the item.
 */
@Slf4j
public class ItemSlot extends Pane{
    @Getter
    private Pane inventorySlot;
    @Getter
    @Setter
    private Item item;
    private Image itemImage;
    private final double slotSize;
    @Getter
    private Rectangle base;
    Parent root;

    /**
     * Constructor for ItemSlot with specified item, slot size, and root.
     *
     * @param item the item
     * @param slotSize the size of the slot
     * @param root the root
     */
    public ItemSlot(Item item, double slotSize, Pane root) {
        this.inventorySlot = new Pane();
        this.slotSize = slotSize;
        this.root = root;
        this.item = item;
        initBase();
        if (item != null){
            this.itemImage = item.getInventoryImage();
            initImage();
        }
        if (itemImage == null) {
            log.debug("No item present, no image loaded");
        } else {
            log.debug("Item present, image {} loaded", itemImage);
        }
    }


    private void initBase(){
        Rectangle slot = new Rectangle(slotSize, slotSize);
        slot.setFill(Color.WHITE);
        slot.setOpacity(0.5);
        slot.setArcWidth(20);
        slot.setArcHeight(20);
        slot.setLayoutX(this.root.getLayoutX());
        slot.setLayoutY(this.root.getLayoutY());
        this.base = slot;
        this.inventorySlot.getChildren().add(slot);
    }


    private void initImage() {
        Image image = itemImage;
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(slotSize);
        imageView.setFitHeight(slotSize);
        imageView.setLayoutX(this.root.getLayoutX());
        imageView.setLayoutY(this.root.getLayoutY());
        inventorySlot.getChildren().add(imageView);
    }

    public void setOnAction(Runnable action){
        inventorySlot.setOnMouseClicked(e -> action.run());
    }

}