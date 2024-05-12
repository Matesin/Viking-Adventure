package map_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Bush extends MapObject{
    @JsonCreator
    protected Bush(@JsonProperty("x") int worldCoordX,@JsonProperty("y") int worldCoordY,@JsonProperty("picture") String pictureID) {
        super(worldCoordX, worldCoordY, pictureID);
    }
}
