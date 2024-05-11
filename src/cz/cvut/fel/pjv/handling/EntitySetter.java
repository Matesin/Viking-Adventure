package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.entity.Character;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class EntitySetter {
    private final JsonEntityHandler jsonEntityHandler = new JsonEntityHandler();
    private final String filepath;

    public EntitySetter(String mapID, boolean loadSaved){
    this.filepath = loadSaved ? "res/save/ent.json" : "res/maps/map" + mapID + "/ent.json";
    }

    public Optional<List<Character>> setEntities(){
        Optional<List<Character>> characters = Optional.empty();
        log.info("Setting entities");
        try {
             characters = jsonEntityHandler.deserializeCharactersFromFile(filepath);
        } catch (Exception e) {
            log.error("Error setting entities", e);
        }
        log.info("{} entities set", characters.isPresent() ? characters.get().size() : "No");
        return characters;
    }

}