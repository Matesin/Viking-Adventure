package cz.cvut.fel.pjv.handling;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.pjv.item.Item;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Class responsible for handling items in the game.
 * This class provides methods for serializing and deserializing items to and from a file.
 */
@Slf4j
public class JsonItemHandler implements ItemHandler{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void serializeItemsToFile(List<Item> items, String filepath) throws IOException {
        File file = new File(filepath);
        objectMapper.writeValue(file, items);
    }

    @Override
    public Optional<List<Item>> deserializeItemsFromFile(String filepath) {
        try {
            File file = new File(filepath);
            return Optional.of(objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Item.class)));
        } catch (IOException e) {
            log.error("Error reading items from file", e);
        }
        return Optional.empty();
    }
}
