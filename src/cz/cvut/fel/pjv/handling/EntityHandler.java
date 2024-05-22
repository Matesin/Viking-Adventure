package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.entity.Character;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Interface for handling entities in the game.
 * This interface provides methods for serializing and deserializing entities to and from a file.
 */
public interface EntityHandler {
    /**
     * Serializes a list of entities to a file.
     *
     * @param entities the list of entities to serialize
     * @param filepath the path of the file to serialize to
     * @throws IOException if an I/O error occurs
     */
    void serializeCharactersToFile(List<Character> entities, String filepath) throws IOException;

    /**
     * Deserializes a list of entities from a file.
     *
     * @param filepath the path of the file to deserialize from
     * @return an Optional containing the list of deserialized entities, or an empty Optional if the file does not exist
     */
    Optional<List<Character>> deserializeCharactersFromFile(String filepath);
}
