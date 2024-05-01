package entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Warden extends TaskAuthority{
    @JsonCreator
    protected Warden(@JsonProperty("x") int worldCoordX, @JsonProperty("y") int worldCoordY) {
        super(worldCoordX, worldCoordY);
    }
}
