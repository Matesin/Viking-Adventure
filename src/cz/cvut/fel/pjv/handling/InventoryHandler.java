package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.inventory.Inventory;

import java.io.IOException;

/**
 * Interface for handling inventory in the game.
 * This interface provides methods for serializing and deserializing inventory to and from a file.
 */
public interface InventoryHandler {
    /**
     * Serializes an inventory to a file.
     *
     * @param inventory the inventory to serialize
     * @param filepath the path of the file to serialize to
     * @throws IOException if an I/O error occurs
     */
    void serializeInventoryToFile(Inventory inventory, String filepath) throws IOException;

    /**
     * Deserializes an inventory from a file.
     *
     * @param filepath the path of the file to deserialize from
     * @return the deserialized inventory
     */
    Inventory deserializeInventoryFromFile(String filepath);
}