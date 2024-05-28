package cz.cvut.fel.pjv.map_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a bush map object.
 */
public class Bush extends MapObject{

    /**
     * @param worldCoordX x-coordinate of the bush
     * @param worldCoordY y-coordinate of the bush
     * @param pictureID picture ID of the bush
     */
    @JsonCreator
    protected Bush(@JsonProperty("x") int worldCoordX,@JsonProperty("y") int worldCoordY,@JsonProperty("idle_picture") String pictureID, @JsonProperty("current_image") String currentImage) {
        super(worldCoordX, worldCoordY, pictureID, currentImage);
    }
}
