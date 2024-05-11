package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.item.Item;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class ItemSetter {
    private final JsonItemHandler jsonItemHandler = new JsonItemHandler();
    private final String filepath;

    public ItemSetter(String mapID, boolean loadSaved){
        this.filepath = loadSaved ? "res/save/obj.json" : "res/maps/map" + mapID + "/obj.json";
    }

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
