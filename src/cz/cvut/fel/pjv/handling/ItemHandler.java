package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.item.Item;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ItemHandler {
    void serializeItemsToFile(List<Item> items, String filepath) throws IOException;
    Optional<List<Item>> deserializeItemsFromFile(String filepath);
}
