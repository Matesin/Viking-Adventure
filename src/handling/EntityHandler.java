package handling;

import entity.Character;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EntityHandler {
    void serializeCharactersToFile(List<Character> entities, String filepath) throws IOException;
    Optional<List<Character>> deserializeCharactersFromFile(String filepath);

}
