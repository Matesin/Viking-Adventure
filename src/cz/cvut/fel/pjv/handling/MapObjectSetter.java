package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.entity.Character;
import lombok.extern.slf4j.Slf4j;
import map_object.MapObject;

import java.util.List;
import java.util.Optional;

@Slf4j
public class MapObjectSetter {
    private final JsonMapObjectHandler jsonEntityHandler = new JsonMapObjectHandler();
    private final String filepath;

    public MapObjectSetter(String mapID, boolean loadSaved){
        this.filepath = loadSaved ? "res/save/map_obj.json" : "res/maps/map" + mapID + "/map_obj.json";
    }

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
