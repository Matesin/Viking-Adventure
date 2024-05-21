package cz.cvut.fel.pjv.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IronOre extends Item{
    @JsonCreator
    public IronOre(@JsonProperty("x")int worldCoordX,
                   @JsonProperty("y") int worldCoordY,
                   @JsonProperty("picture") String pictureID) {
        super(worldCoordX, worldCoordY, pictureID);
        this.name = "Iron Ore";
        this.description = "Iron ore is a mineral block found underground. It is the most common mineral that can be used to make tools and armor.";
    }

    public IronOre(int worldCoordX, int worldCoordY, String itemID, String pictureID) {
        super(worldCoordX, worldCoordY, itemID, pictureID);
    }

    public IronOre(String pictureID) {
        super(pictureID);
    }

    public IronOre() {
    }
}
