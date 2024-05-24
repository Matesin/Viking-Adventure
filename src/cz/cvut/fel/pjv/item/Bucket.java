package cz.cvut.fel.pjv.item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Class representing a bucket item.
 */
public class Bucket extends Item {
    @JsonCreator
    public Bucket(@JsonProperty("name") String name,
                   @JsonProperty("description") String description,
                   @JsonProperty("x")int worldCoordX,
                   @JsonProperty("y")int worldCoordY,
                   @JsonProperty("picture") String pictureID) {
        super(worldCoordX, worldCoordY, pictureID);
        this.name = name;
        this.description = description;
    }
    public Bucket(@JsonProperty("name") String name,
                  @JsonProperty("description") String description,
                  @JsonProperty("picture") String pictureID) {
        super(pictureID);
        this.name = name;
        this.description = description;
        this.craftingMaterials = List.of(IRON_ORE, WOOD);
    }
    public Bucket() {
        super();
    }
}

