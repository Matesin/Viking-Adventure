package cz.cvut.fel.pjv.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Warden extends TaskAuthority{
    @JsonCreator
    protected Warden(@JsonProperty("x") int worldCoordX, @JsonProperty("y") int worldCoordY) {
        super(worldCoordX, worldCoordY);
    }
}
