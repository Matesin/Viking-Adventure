package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.entity.Character;
import map_object.MapObject;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MapObjectHandler {
    void serializeObjectsToFile(List<MapObject> mapObjects, String filepath) throws IOException;
    Optional<List<MapObject>> deserializeObjectsFromFile(String filepath);
}
