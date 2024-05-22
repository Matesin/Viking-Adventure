package cz.cvut.fel.pjv.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a wood item.
 */
public class Wood extends Item{
    @JsonCreator
    public Wood(@JsonProperty("x")int worldCoordX,
                @JsonProperty("y") int worldCoordY,
                @JsonProperty("picture") String pictureID){
        super(worldCoordX, worldCoordY, pictureID);
        this.name = "Wood";
        this.description = "Wood is material used for crafting. It is obtained by chopping trees.";
    }

    public Wood(int worldCoordX, int worldCoordY, String itemID, String pictureID) {
        super(worldCoordX, worldCoordY, itemID, pictureID);
    }

    public Wood(String pictureID) {
        super(pictureID);
    }

    public Wood() {
    }
}
