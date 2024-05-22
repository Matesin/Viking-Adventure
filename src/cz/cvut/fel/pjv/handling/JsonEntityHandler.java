package cz.cvut.fel.pjv.handling;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.pjv.entity.Character;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Class responsible for handling entities in the game.
 * This class provides methods for serializing and deserializing entities to and from a file.
 */
@Slf4j
public class JsonEntityHandler implements EntityHandler{
    private final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * Serializes a list of entities to a file.
     *
     * @param entities the list of entities to serialize
     * @param filepath the path of the file to serialize to
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void serializeCharactersToFile(List<Character> entities, String filepath) throws IOException {
        File entityFile = new File(filepath);
        objectMapper.writeValue(entityFile, entities);
    }

    /**
     * Deserializes a list of entities from a file.
     *
     * @param filepath the path of the file to deserialize from
     * @return an Optional containing the list of deserialized entities, or an empty Optional if the file does not exist
     */
    @Override
    public Optional<List<Character>> deserializeCharactersFromFile(String filepath) {
        File entityFile = new File(filepath);
        TypeReference<List<Character>> characterList = new TypeReference<>() {};
        try {
            return Optional.of(objectMapper.readValue(entityFile, characterList));
        }
        catch (IOException e) {
            log.error("Error reading entities from file", e);
        }
        return Optional.empty();
    }
}
