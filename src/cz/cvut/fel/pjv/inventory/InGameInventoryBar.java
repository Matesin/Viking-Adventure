package cz.cvut.fel.pjv.inventory;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Optional;

import  static cz.cvut.fel.pjv.gameloop.Constants.Inventory.*;
@Slf4j
public class InGameInventoryBar {
    private Inventory inventory;
    private final int capacity = 5;
    private StackPane root;

    public InGameInventoryBar(Inventory inventory, StackPane root) {
        this.inventory = inventory;
        this.root = root;
//        initBase();
        initSlots();
    }
    private void initBase() {
        Rectangle inventoryBase = new Rectangle();
        inventoryBase.setWidth(SCREEN_INVENTORY_WIDTH);
        inventoryBase.setHeight(SCREEN_INVENTORY_HEIGHT);
        inventoryBase.setOpacity(0.5); // 50% opacity
        inventoryBase.setArcWidth(20); // Rounded corners
        inventoryBase.setArcHeight(20); // Rounded corner
        log.debug("Inventory position {}x{}", inventoryBase.getLayoutX(), inventoryBase.getLayoutY());
        log.debug("Desired inventory position {}x{}", SCREEN_INVENTORY_X, SCREEN_INVENTORY_Y);
        root.getChildren().add(inventoryBase);
    }
    private void initSlots(){
        ItemSlot itemSlot = new ItemSlot(Optional.ofNullable(inventory.getPickedItem()), 0, SCREEN_SLOT_SIZE, SCREEN_SLOT_PADDING, SCREEN_INVENTORY_X, SCREEN_INVENTORY_Y);
        root.getChildren().add(itemSlot.getInventorySlot());
    }

}