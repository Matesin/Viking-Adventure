package map_object;

import com.fasterxml.jackson.annotation.*;
import cz.cvut.fel.pjv.entity.Hitbox;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import cz.cvut.fel.pjv.item.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

import static cz.cvut.fel.pjv.gameloop.Constants.GraphicsDefaults.DEFAULT_TILE_FILEPATH;
import static cz.cvut.fel.pjv.gameloop.Constants.Inventory.SLOT_SIZE;
import static cz.cvut.fel.pjv.gameloop.Constants.Screen.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Tile.TILE_SIZE;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Bush.class, name = "bush"),
})

@Slf4j
public abstract class MapObject {
    String name;
    String pictureID;
    @Getter
    @Setter
    int worldCoordX;
    @Getter
    @Setter
    int worldCoordY;
    @Getter
    int screenCoordX;
    @Getter
    int screenCoordY;
    Image image;
    public final Hitbox hitbox;
    @JsonCreator
    protected MapObject(@JsonProperty("x") int worldCoordX,
                        @JsonProperty("y") int worldCoordY,
                        @JsonProperty("picture") String pictureID) {
        //default constructor - load the image of the respective item
        this.pictureID = pictureID;
        String itemName = this.getClass().getSimpleName();
        log.info("Loading image for item: {}", itemName);
        loadImage(pictureID);
        log.info("Image loaded for item: {}", itemName);
        //set the world coordinates of the item, do not place it in the top left corner of the tile
        this.worldCoordX = worldCoordX * TILE_SIZE;
        this.worldCoordY = worldCoordY * TILE_SIZE;
        this.hitbox = new Hitbox(this, (int) this.image.getWidth(), (int) this.image.getHeight());
    }
    public void loadImage(String pictureID) {
        String filepath = "res/map_objects/" + pictureID;
        try {
            FileInputStream fis = new FileInputStream(filepath);
            image = new Image(fis, TILE_SIZE/2, TILE_SIZE/2, false, false);
        } catch (FileNotFoundException e) {
            log.error("Error loading the image {}, loading default tile", pictureID);
            try {
                FileInputStream fis = new FileInputStream(DEFAULT_TILE_FILEPATH);
                image = new Image(fis);
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
            gc.drawImage(image, screenCoordX, screenCoordY);
        }
    }
}
