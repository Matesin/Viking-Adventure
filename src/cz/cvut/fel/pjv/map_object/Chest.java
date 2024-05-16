package cz.cvut.fel.pjv.map_object;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fel.pjv.item.Item;

public class Chest extends ActiveMapObject{
    protected Chest(@JsonProperty("x")int worldCoordX,
                    @JsonProperty("y") int worldCoordY,
                    @JsonProperty("idle_picture") String idlePictureID,
                    @JsonProperty("active_picture") String activePictureID,
                    @JsonProperty("activation_item") Item activationItem) {
        super(worldCoordX, worldCoordY, idlePictureID, activePictureID, activationItem);
    }

    @Override
    void activate() {
        // Open the chest
        //TODO: Add the logic for opening the chest and getting items
    }
}
