package item;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

public class Chest extends Item{
    Optional<List<Item>> items = Optional.empty();
    public Chest(@JsonProperty("x")int worldCoordX, @JsonProperty("y")int worldCoordY) {
        this.worldCoordX = worldCoordX;
        this.worldCoordY = worldCoordY;
    }
    public Chest(@JsonProperty("x")int worldCoordX, @JsonProperty("y")int worldCoordY, @JsonProperty("items")List<Item> items) {
        this.worldCoordX = worldCoordX;
        this.worldCoordY = worldCoordY;
        this.items = Optional.of(items);
    }
    public Optional<List<Item>> getItems() {
        return items;
    }
    public void addItem(Item item) {
        items.ifPresent(itemList -> itemList.add(item));
    }


}
