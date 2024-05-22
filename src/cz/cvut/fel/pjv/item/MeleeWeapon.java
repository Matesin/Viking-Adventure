package cz.cvut.fel.pjv.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Class representing a melee weapon item.
 */
@Slf4j
public class MeleeWeapon extends Weapon{
    @JsonCreator
    public MeleeWeapon(@JsonProperty("name") String name,
                       @JsonProperty("damage") int damage,
                       @JsonProperty("description") String description,
                       @JsonProperty("x")int worldCoordX,
                       @JsonProperty("y")int worldCoordY,
                       @JsonProperty("picture") String pictureID) {
        super(worldCoordX, worldCoordY, pictureID);
        this.name = name;
        this.damage = damage;
        this.description = description;
    }
    public MeleeWeapon(@JsonProperty("name") String name,
                       @JsonProperty("description") String description,
                       @JsonProperty("picture") String pictureID,
                       @JsonProperty("damage") int damage){
        super(pictureID);
        this.damage = damage;
        this.name = name;
        this.description = description;
        this.craftingMaterials = switch(name){
            case "Swordus minimus", "Axeus maximus" -> List.of(IRON_ORE, IRON_ORE, WOOD);
            case "Swordus maximus" -> List.of(IRON_ORE, IRON_ORE, IRON_ORE, WOOD);
            case "Axeus" -> List.of(IRON_ORE, WOOD);
            case "Axeus ultimus" -> List.of(IRON_ORE, IRON_ORE, IRON_ORE, IRON_ORE, WOOD);
            default -> List.of(IRON_ORE, IRON_ORE, IRON_ORE, IRON_ORE);
        };
    }

}
