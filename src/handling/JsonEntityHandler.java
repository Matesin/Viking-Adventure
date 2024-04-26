package handling;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Character;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JsonEntityHandler implements EntityHandler{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String OBJ_FILENAME = "/ent.json";
    @Override
    public void serializeCharactersToFile(List<Character> entities, String save) throws IOException {
        String filepath = "saves/" + save + OBJ_FILENAME;
        File entityFile = new File(filepath);
        objectMapper.writeValue(entityFile, entities);
    }

    @Override
    public Optional<List<Character>> deserializeCharactersFromFile(String save, String mapID) {
        String mapFilepath = "res/maps/map" + mapID + OBJ_FILENAME;
        String filepath = save.equals("default") ? mapFilepath : "saves/" + save + OBJ_FILENAME;
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
