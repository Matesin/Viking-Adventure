package handling;

import entity.Character;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class EntitySetter {
    private final JsonEntityHandler jsonEntityHandler = new JsonEntityHandler();
    private final String save;
    private final String mapID;

    public EntitySetter(String save, String mapID){
        this.save = save;
        this.mapID = mapID;
    }

    public Optional<List<Character>> setEntities(){
        Optional<List<Character>> characters = Optional.empty();
        try {
             characters = jsonEntityHandler.deserializeCharactersFromFile(save, mapID);
        } catch (Exception e) {
            log.error("Error setting entities", e);
        }
        return characters;
    }

}