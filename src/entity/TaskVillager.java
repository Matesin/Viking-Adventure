package entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskVillager extends TaskAuthority{
    @JsonCreator
    protected TaskVillager(@JsonProperty("x") int worldCoordX,@JsonProperty("y") int worldCoordY) {
        super(worldCoordX, worldCoordY);
    }
}
