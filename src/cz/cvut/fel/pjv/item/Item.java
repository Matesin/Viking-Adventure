package cz.cvut.fel.pjv.item;

import com.fasterxml.jackson.annotation.*;
import cz.cvut.fel.pjv.entity.Hitbox;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

import static cz.cvut.fel.pjv.gameloop.Constants.GraphicsDefaults.DEFAULT_TILE_FILEPATH;
import static cz.cvut.fel.pjv.gameloop.Constants.Screen.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Tile.TILE_SIZE;
import static cz.cvut.fel.pjv.gameloop.Constants.Inventory.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
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
    String type;
    @JsonGetter("type")
    public String getType() {
        return this.getClass().getSimpleName()
                .replaceAll("(\\p{Ll})(\\p{Lu})", "$1_$2")
                .toLowerCase();
    }
    @Getter
    String name;
    @Getter
    String description;
    public boolean collision = false;
    @Getter
    @Setter
    @JsonIgnore
    int worldCoordX;
    @JsonGetter("x")
    public int getJsonX() {
        return this.worldCoordX / TILE_SIZE;
    }
    @Getter
    @Setter
    @JsonIgnore
    int worldCoordY;
    @JsonGetter("y")
    public int getJsonY() {
        return this.worldCoordY / TILE_SIZE;
    }
    @JsonIgnore
    @Getter
    int screenCoordX;
    @JsonIgnore
    @Getter
    int screenCoordY;
    String pictureID;
    @JsonGetter ("picture")
    public String getPictureID() {
        return this.pictureID;
    }
    @JsonIgnore
    public final Hitbox hitbox;
    @Getter
    @JsonIgnore
    Image placementImage; //Image used when the object is placed on the map
    @Getter
    @JsonIgnore
    Image inventoryImage; //Image used when the object is in the player's inventory

    @JsonCreator
    protected Item(@JsonProperty("x") int worldCoordX,
                   @JsonProperty("y") int worldCoordY,
                   @JsonProperty("picture") String pictureID) {
        //default constructor - load the image of the respective item
        this.pictureID = pictureID;
        type = this.getClass().getSimpleName();
        log.info("Loading image for item: {}", type);
        loadImage(pictureID);
        log.info("Image loaded for item: {}", type);
        //set the world coordinates of the item, do not place it in the top left corner of the tile
        this.worldCoordX = worldCoordX * TILE_SIZE + ThreadLocalRandom.current().nextInt(0, TILE_SIZE - (int) this.placementImage.getWidth());
        this.worldCoordY = worldCoordY * TILE_SIZE + ThreadLocalRandom.current().nextInt(0, TILE_SIZE - (int) this.placementImage.getHeight());
        this.hitbox = new Hitbox(this, (int) this.placementImage.getWidth(), (int) this.placementImage.getHeight(), 0, 0);

    }
    public void loadImage(String pictureID) {
        String filepath = "res/items/" + pictureID;
        try {
            FileInputStream fis1 = new FileInputStream(filepath);
            placementImage = new Image(fis1);
            FileInputStream fis2 = new FileInputStream(filepath);
            inventoryImage = new Image(fis2, SLOT_SIZE, SLOT_SIZE, false, false);
            log.debug("Placement Image {} loaded with dimensions {}x{}", pictureID, placementImage.getWidth(), placementImage.getHeight());
            log.debug("Inventory Image {} loaded with dimensions {}x{}", pictureID, inventoryImage.getWidth(), inventoryImage.getHeight());
        } catch (FileNotFoundException e) {
            log.error("Error loading the image {}, loading default tile", pictureID);
            try {
                FileInputStream fis = new FileInputStream(DEFAULT_TILE_FILEPATH);
                placementImage = new Image(fis);
                inventoryImage = new Image(fis, SLOT_SIZE, SLOT_SIZE, false, false);
                log.info("Default tile loaded successfully");
            } catch (FileNotFoundException ex) {
                log.error("Error loading default tile", ex);
            }
        }
    }
    public void render(GraphicsContext gc, GamePanel gamePanel){
        screenCoordX = this.worldCoordX - gamePanel.player.getWorldCoordX() + SCREEN_MIDDLE_X;
        screenCoordY = this.worldCoordY - gamePanel.player.getWorldCoordY() + SCREEN_MIDDLE_Y;
        int mapWidth = gamePanel.getChosenMap().getMapWidth();
        int mapHeight = gamePanel.getChosenMap().getMapHeight();
        if(gamePanel.player.getWorldCoordX() < SCREEN_MIDDLE_X) {
            screenCoordX = this.worldCoordX;
        }
        if (gamePanel.player.getWorldCoordY() < SCREEN_MIDDLE_Y) {
            screenCoordY = this.worldCoordY;
        }
        if (gamePanel.player.getWorldCoordX() > mapWidth * TILE_SIZE - SCREEN_MIDDLE_X) {
            screenCoordX = this.worldCoordX - (mapWidth * TILE_SIZE - SCREEN_WIDTH);
        }
        if (gamePanel.player.getWorldCoordY() > mapHeight * TILE_SIZE - SCREEN_MIDDLE_Y) {
            screenCoordY = this.worldCoordY - (mapHeight * TILE_SIZE - SCREEN_HEIGHT);
        }
        if (screenCoordX >= - TILE_SIZE &&
            screenCoordX <= SCREEN_WIDTH &&
            screenCoordY >= - TILE_SIZE &&
            screenCoordY <= SCREEN_HEIGHT) {
            gc.drawImage(placementImage, screenCoordX, screenCoordY);
        }

    }

}
