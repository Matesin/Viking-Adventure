package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.gameloop.GamePanel;
import cz.cvut.fel.pjv.inventory.Inventory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * Class responsible for managing the inventory in the game.
 * This class provides methods for loading and saving the inventory.
 */
@Slf4j
public class InventoryManager {
    private final Inventory inventory;
    /**
     * Constructor for InventoryManager with specified game panel.
     *
     * @param gamePanel the game panel
     */
    public InventoryManager(GamePanel gamePanel) {
        this.inventory = gamePanel.player.getInventory();
        if (gamePanel.isLoadSaved()) {
            loadInventory();
        }
    }
    /**
     * Loads the inventory from a file.
     */
    private void loadInventory() {
        log.info("Loading inventory...");
        String filepath = "res/save/inventory.json";
        Path path = Paths.get(filepath);
        if (Files.exists(path)) {
            JsonInventoryHandler jsonInventoryHandler = new JsonInventoryHandler();
            Inventory loadedInventory = jsonInventoryHandler.deserializeInventoryFromFile(filepath);
            inventory.setItems(loadedInventory.getItems());
        }
    }
    /**
     * Saves the inventory to a file.
     */
    public void saveInventory() {
        log.info("Saving inventory...");
        String filepath = "res/save/inventory.json";
        Path path = Paths.get("res", "save", "inventory.json");
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                log.error("Error deleting the file", e);
            }
        }
        JsonInventoryHandler jsonInventoryHandler = new JsonInventoryHandler();
        try {
            jsonInventoryHandler.serializeInventoryToFile(inventory, filepath);
        } catch (Exception e) {
            log.error("Error saving entities", e);
        }
        log.info("Items saved successfully");
    }
}
