package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.item.Item;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Interface for handling items in the game.
 * This interface provides methods for serializing and deserializing items to and from a file.
 */
public interface ItemHandler {
    /**
     * Serializes a list of items to a file.
     *
     * @param items the list of items to serialize
     * @param filepath the path of the file to serialize to
     * @throws IOException if an I/O error occurs
     */
    void serializeItemsToFile(List<Item> items, String filepath) throws IOException;

    /**
     * Deserializes a list of items from a file.
     *
     * @param filepath the path of the file to deserialize from
     * @return an Optional containing the list of deserialized items, or an empty Optional if the file does not exist
     */
    Optional<List<Item>> deserializeItemsFromFile(String filepath);
}
