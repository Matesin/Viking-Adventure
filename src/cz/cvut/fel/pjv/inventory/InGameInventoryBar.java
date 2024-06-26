package cz.cvut.fel.pjv.inventory;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;

import  static cz.cvut.fel.pjv.gameloop.Constants.Inventory.*;

/**
 * Class responsible for managing the inventory bar in the game.
 * This class provides methods for rendering the inventory bar on the screen.
 */
@Slf4j
public class InGameInventoryBar {
    private final Inventory inventory;
    private final Pane inventoryBar;

    /**
     * Constructor for InGameInventoryBar with specified inventory and root.
     *
     * @param inventory the inventory
     * @param root the root
     */
    public InGameInventoryBar(Inventory inventory, Pane root) {
        this.inventoryBar = new Pane();
        this.inventory = inventory;
        initBase();
        initSlots();
        root.getChildren().add(inventoryBar);
    }

    /**
     * Initializes the base of the inventory bar.
     */
    private void initBase() {
        Rectangle inventoryBase = new Rectangle();
        inventoryBase.setWidth(SCREEN_INVENTORY_WIDTH);
        inventoryBase.setHeight(SCREEN_INVENTORY_HEIGHT);
        inventoryBase.setLayoutX(SCREEN_INVENTORY_X);
        inventoryBase.setLayoutY(SCREEN_INVENTORY_Y);
        inventoryBase.setOpacity(0.5); // 50% opacity
        inventoryBase.setArcWidth(20); // Rounded corners
        inventoryBase.setArcHeight(20); // Rounded corner
       inventoryBar.getChildren().add(inventoryBase);
    }

    /**
     * Initializes the slots of the inventory bar.
     */
    private void initSlots(){
        initBase();
        ItemSlot itemSlot = new ItemSlot(inventory.getPickedItem(), SCREEN_SLOT_SIZE, inventoryBar);
        itemSlot.getInventorySlot().setLayoutX(FIRST_SCREEN_SLOT_X);
        itemSlot.getInventorySlot().setLayoutY(FIRST_SCREEN_SLOT_Y);
        inventoryBar.getChildren().add(itemSlot.getInventorySlot());
    }

    /**
     * Updates the inventory bar.
     */
    public void update(){
        inventoryBar.getChildren().clear();
        initSlots();
    }

}