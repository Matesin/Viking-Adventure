package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.map_object.MapObject;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Interface for handling map objects in the game.
 * This interface provides methods for serializing and deserializing map objects to and from a file.
 */
public interface MapObjectHandler {
    /**
     * Serializes a list of map objects to a file.
     *
     * @param mapObjects the list of map objects to serialize
     * @param filepath the path of the file to serialize to
     * @throws IOException if an I/O error occurs
     */
    void serializeObjectsToFile(List<MapObject> mapObjects, String filepath) throws IOException;

    /**
     * Deserializes a list of map objects from a file.
     *
     * @param filepath the path of the file to deserialize from
     * @return an Optional containing the list of deserialized map objects, or an empty Optional if the file does not exist
     */
    Optional<List<MapObject>> deserializeObjectsFromFile(String filepath);
}
