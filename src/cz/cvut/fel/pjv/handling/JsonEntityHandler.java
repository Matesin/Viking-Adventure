package cz.cvut.fel.pjv.handling;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.pjv.entity.Character;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JsonEntityHandler implements EntityHandler{
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void serializeCharactersToFile(List<Character> entities, String filepath) throws IOException {
        File entityFile = new File(filepath);
        objectMapper.writeValue(entityFile, entities);
    }

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
