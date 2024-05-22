package cz.cvut.fel.pjv.handling;

import lombok.extern.slf4j.Slf4j;
import cz.cvut.fel.pjv.map_object.MapObject;

import java.util.List;
import java.util.Optional;

/**
 * Class responsible for setting map objects in the game.
 * This class provides a method for setting map objects from a file.
 */
@Slf4j
public class MapObjectSetter {
    private final JsonMapObjectHandler jsonEntityHandler = new JsonMapObjectHandler();
    private final String filepath;

    /**
     * Constructor for setting map objects.
     *
     * @param mapID the ID of the map
     * @param loadSaved whether to load saved map objects
     */
    public MapObjectSetter(String mapID, boolean loadSaved){
        this.filepath = loadSaved ? "res/save/map_obj.json" : "res/maps/map" + mapID + "/map_obj.json";
    }

    /**
     * Sets map objects from a file.
     *
     * @return an Optional containing the list of map objects set, or an empty Optional if an error occurs
     */
    public Optional<List<MapObject>> setEntities(){
        Optional<List<MapObject>> mapObjects = Optional.empty();
        log.info("Setting map objects");
        try {
            mapObjects = jsonEntityHandler.deserializeObjectsFromFile(filepath);
        } catch (Exception e) {
            log.error("Error setting map objects", e);
        }
        log.info("{} map objects set", mapObjects.isPresent() ? mapObjects.get().size() : "No");
        return mapObjects;
    }

}
