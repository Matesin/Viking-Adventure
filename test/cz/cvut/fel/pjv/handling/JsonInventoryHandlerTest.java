package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.inventory.Inventory;
import cz.cvut.fel.pjv.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class JsonInventoryHandlerTest {
    private JsonInventoryHandler jsonInventoryHandler;
    private String testFilePath = "testInventory.json";

    @BeforeEach
    void setUp() {
        jsonInventoryHandler = new JsonInventoryHandler();
    }

    @Test
    void testJsonInventoryHandler() {
        // Create a new inventory
        Inventory inventory = new Inventory(5);
        inventory.addItem(Item.createItem("Wood", "Wooden log", "Wood", "wood.png"));

        // Serialize the inventory to a file
        try {
            jsonInventoryHandler.serializeInventoryToFile(inventory, testFilePath);
        } catch (IOException e) {
            fail("Failed to serialize inventory to file");
        }

        // Deserialize the inventory from the file
        Inventory deserializedInventory = jsonInventoryHandler.deserializeInventoryFromFile(testFilePath);

        // Compare the deserialized inventory with the original one
        assertEquals(inventory.getItems().getClass().getSimpleName(), deserializedInventory.getItems().getClass().getSimpleName());

        // Clean up the test file
        try {
            Files.deleteIfExists(Paths.get(testFilePath));
        } catch (IOException e) {
            fail("Failed to delete test file");
        }
    }
}