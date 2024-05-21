package cz.cvut.fel.pjv.map_object;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Chest extends ActiveMapObject{
    protected Chest(@JsonProperty("x")int worldCoordX,
                    @JsonProperty("y") int worldCoordY,
                    @JsonProperty("idle_picture") String idlePictureID,
                    @JsonProperty("active_picture") String activePictureID,
                    @JsonProperty("activation_item") String activationItem) {
        super(worldCoordX, worldCoordY, idlePictureID, activePictureID, activationItem);
    }

}
