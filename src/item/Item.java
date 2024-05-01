package item;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LevelKey.class, name = "level_key"),
        @JsonSubTypes.Type(value = MeleeWeapon.class, name = "melee_weapon"),
        @JsonSubTypes.Type(value = RangedWeapon.class, name = "ranged_weapon"),
        @JsonSubTypes.Type(value = Potion.class, name = "potion"),
        @JsonSubTypes.Type(value = WeaponUpgrade.class, name = "weapon_upgrade")
})

public abstract class Item {
    /*
     * This class is the parent class for all items in the game. It contains the basic attributes that all items have.
     */
    String name;
    String description;
    public boolean collision = false;
    @Getter
    @Setter
    int worldCoordX;
    @Getter
    @Setter
    int worldCoordY;
    Image placementImage = new Image("res/defaults/default_tile.png"); //Image used when the object is placed on the map
    Image inventoryImage; //Image used when the object is in the player's inventory
    public void loadImage(String id) throws FileNotFoundException {
        String filepath = "res/items/" + this.getClass().getName().toLowerCase() + "_"+ id + ".png";
        FileInputStream fis = new FileInputStream(filepath);
        placementImage = new Image(fis);
        inventoryImage = new Image(fis, 50, 50, false, false); // TODO: Resize the image to fit in the inventory
    }
}
