package handling;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import item.Item;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
