package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.entity.Character;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * Class responsible for setting entities in the game.
 * This class provides methods for setting entities based on a specified map ID and a flag indicating whether to load saved entities.
 */
@Slf4j
public class EntitySetter {
    private final JsonEntityHandler jsonEntityHandler = new JsonEntityHandler();
    private final String filepath;

    /**
     * Constructor for EntitySetter with specified map ID and loadSaved flag.
     *
     * @param mapID the map ID
     * @param loadSaved the flag indicating whether to load saved entities
     */
    public EntitySetter(String mapID, boolean loadSaved){
        this.filepath = loadSaved ? "res/save/ent.json" : "res/maps/map" + mapID + "/ent.json";
    }

    /**
     * Sets entities for the game.
     *
     * @return an Optional containing the list of set entities, or an empty Optional if an error occurs
     */
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