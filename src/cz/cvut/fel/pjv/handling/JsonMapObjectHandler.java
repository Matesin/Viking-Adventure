package cz.cvut.fel.pjv.handling;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import cz.cvut.fel.pjv.map_object.MapObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Slf4j
public class JsonMapObjectHandler implements MapObjectHandler{
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void serializeObjectsToFile(List<MapObject> mapObjects, String filepath) throws IOException {
        objectMapper.writeValue(new File(filepath), mapObjects);
    }

    @Override
    public Optional<List<MapObject>> deserializeObjectsFromFile(String filepath) {
        try {
            File file = new File(filepath);
            return Optional.of(objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, MapObject.class)));
        } catch (IOException e) {
            log.error("Error reading map objects from file", e);
        }
        return Optional.empty();
    }
}
