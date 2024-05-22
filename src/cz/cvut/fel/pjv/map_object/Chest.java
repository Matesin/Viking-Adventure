package cz.cvut.fel.pjv.map_object;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a chest map object.
 */
public class Chest extends ActiveMapObject{

    /**
     * @param worldCoordX x-coordinate of the chest
     * @param worldCoordY y-coordinate of the chest
     * @param idlePictureID picture ID of the chest
     * @param activePictureID picture ID of the chest when activated
     * @param activationItem item needed to activate the chest
     */
    protected Chest(@JsonProperty("x")int worldCoordX,
                    @JsonProperty("y") int worldCoordY,
                    @JsonProperty("idle_picture") String idlePictureID,
                    @JsonProperty("active_picture") String activePictureID,
                    @JsonProperty("activation_item") String activationItem) {
        super(worldCoordX, worldCoordY, idlePictureID, activePictureID, activationItem);
    }

}
