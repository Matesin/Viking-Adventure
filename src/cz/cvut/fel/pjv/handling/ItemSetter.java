package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.item.Item;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * Class responsible for setting items in the game.
 * This class provides methods for setting items based on a specified map ID and a flag indicating whether to load saved items.
 */
@Slf4j
public class ItemSetter {
    private final JsonItemHandler jsonItemHandler = new JsonItemHandler();
    private final String filepath;

    /**
     * Constructor for ItemSetter with specified map ID and loadSaved flag.
     *
     * @param mapID the map ID
     * @param loadSaved the flag indicating whether to load saved items
     */
    public ItemSetter(String mapID, boolean loadSaved){
        this.filepath = loadSaved ? "res/save/obj.json" : "res/maps/map" + mapID + "/obj.json";
    }

    /**
     * Sets items for the game.
     *
     * @return an Optional containing the list of set items, or an empty Optional if an error occurs
     */
    public Optional<List<Item>> setItems(){
        Optional<List<Item>> items = Optional.empty();
        log.info("Setting items");
        try {
            items = jsonItemHandler.deserializeItemsFromFile(filepath);
        } catch (Exception e) {
            log.error("Error setting entities", e);
        }
        log.info("{} items set", items.isPresent() ? items.get().size() : "No");
        return items;
    }

}
