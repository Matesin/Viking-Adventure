package item;

import com.fasterxml.jackson.annotation.*;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static gameloop.Constants.Tile.TILE_SIZE;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LevelKey.class, name = "level_key"),
        @JsonSubTypes.Type(value = MeleeWeapon.class, name = "melee_weapon"),
        @JsonSubTypes.Type(value = RangedWeapon.class, name = "ranged_weapon"),
        @JsonSubTypes.Type(value = Potion.class, name = "potion"),
        @JsonSubTypes.Type(value = WeaponUpgrade.class, name = "weapon_upgrade")
})

@Slf4j
public abstract class Item {
    /*
     * This class is the parent class for all items in the game. It contains the basic attributes that all items have.
     */
    @Getter
    String name;
    @Getter
    String description;
    public boolean collision = false;
    @Getter
    @Setter
    int worldCoordX;
    @Getter
    @Setter
    int worldCoordY;
    String pictureID;
    @Getter
    static int imagesLoaded = 0;
    @JsonCreator
    protected Item(@JsonProperty("x") int worldCoordX,
                   @JsonProperty("y") int worldCoordY,
                   @JsonProperty("picture") String pictureID) {
        //default constructor - load the image of the respective item
        this.pictureID = pictureID;
        String itemName = this.getClass().getSimpleName();
        log.info("Loading image for item: {}", itemName);
        loadImage(pictureID);
        log.info("Image loaded for item: {}", itemName);
        this.worldCoordX = worldCoordX * TILE_SIZE;
        this.worldCoordY = worldCoordY * TILE_SIZE;
    }

    Image placementImage; //Image used when the object is placed on the map
    Image inventoryImage; //Image used when the object is in the player's inventory
    public void loadImage(String pictureID) {
        String filepath = "res/items/" + pictureID;
        String defaultFilepath = "res/defaults/default_tile.png";
        try {
            FileInputStream fis = new FileInputStream(filepath);
            placementImage = new Image(fis);
            inventoryImage = new Image(fis, 50, 50, false, false);
            imagesLoaded++;
        } catch (FileNotFoundException e) {
            log.error("Error loading the image {}, loading default tile", pictureID);
            try {
                FileInputStream fis = new FileInputStream(defaultFilepath);
                placementImage = new Image(fis);
                inventoryImage = new Image(fis, 50, 50, false, false);
                log.info("Default tile loaded successfully");
            } catch (FileNotFoundException ex) {
                log.error("Error loading default tile", ex);
            }
        }
    }
    public void render(GraphicsContext gc){
        int screenX = this.worldCoordX;
        int screenY = this.worldCoordY;
        gc.drawImage(placementImage, screenX, screenY);
    }
}
