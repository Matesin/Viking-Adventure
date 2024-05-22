package cz.cvut.fel.pjv.handling;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import cz.cvut.fel.pjv.map_object.MapObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Class responsible for handling map objects in the game.
 * This class provides methods for serializing and deserializing map objects to and from a file.
 * It implements the MapObjectHandler interface.
 */
@Slf4j
public class JsonMapObjectHandler implements MapObjectHandler{
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Serializes a list of map objects to a file.
     *
     * @param mapObjects the list of map objects to serialize
     * @param filepath the path of the file to serialize to
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void serializeObjectsToFile(List<MapObject> mapObjects, String filepath) throws IOException {
        objectMapper.writeValue(new File(filepath), mapObjects);
    }

    /**
     * Deserializes a list of map objects from a file.
     *
     * @param filepath the path of the file to deserialize from
     * @return an Optional containing the list of deserialized map objects, or an empty Optional if the file does not exist
     */
    @Override
    public Optional<List<MapObject>> deserializeObjectsFromFile(String filepath) {
        try {
            File file = new File(filepath);
            return Optional.of(objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, MapObject.class)));
        } catch (IOException e) {
            log.error("Error reading map objects from file", e);
        }
        return Optional.empty();
    }
}