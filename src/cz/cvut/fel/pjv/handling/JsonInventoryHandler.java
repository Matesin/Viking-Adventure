package cz.cvut.fel.pjv.handling;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.pjv.inventory.Inventory;
import cz.cvut.fel.pjv.item.Item;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

import static cz.cvut.fel.pjv.gameloop.Constants.Inventory.INITIAL_INVENTORY_CAPACITY;

/**
 * Class responsible for handling inventory in the game.
 * This class provides methods for serializing and deserializing inventory to and from a file.
 * It implements the InventoryHandler interface.
 */
@Slf4j
public class JsonInventoryHandler implements InventoryHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Serializes an inventory to a file.
     *
     * @param inventory the inventory to serialize
     * @param filepath the path of the file to serialize to
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void serializeInventoryToFile(Inventory inventory, String filepath) throws IOException {
        File file = new File(filepath);
        objectMapper.writeValue(file, inventory);
    }

    /**
     * Deserializes an inventory from a file.
     *
     * @param filepath the path of the file to deserialize from
     * @return the deserialized inventory, or a new inventory with initial capacity if an error occurs
     */
    @Override
    public Inventory deserializeInventoryFromFile(String filepath) {
        try {
            File file = new File(filepath);
            return objectMapper.readValue(file, Inventory.class);
        } catch (IOException e) {
            log.error("Error reading inventory from file", e);
            return new Inventory(INITIAL_INVENTORY_CAPACITY);
        }
    }
}