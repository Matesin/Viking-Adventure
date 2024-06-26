package cz.cvut.fel.pjv.map_object;

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

import static cz.cvut.fel.pjv.gameloop.Constants.GraphicsDefaults.DEFAULT_TILE_FILEPATH;
import static cz.cvut.fel.pjv.gameloop.Constants.Screen.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Tile.TILE_SIZE;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Bush.class, name = "bush"),
        @JsonSubTypes.Type(value = Chest.class, name = "chest"),
        @JsonSubTypes.Type(value = Door.class, name = "door"),
        @JsonSubTypes.Type(value = Fire.class, name = "fire"),
})

@Slf4j
public abstract class MapObject {
    @JsonGetter("type")
    public String getType() {
        return this.getClass().getSimpleName()
                .replaceAll("(\\p{Ll})(\\p{Lu})", "$1_$2")
                .toLowerCase();
    }
    @JsonGetter("idle_picture")
    public String getPicture() {
            return this.idlePictureID;
    }
    @JsonGetter("x")
    public double getX() {
        return this.worldCoordX / TILE_SIZE;
    }
    @JsonGetter("y")
    public double getY() {
        return this.worldCoordY / TILE_SIZE;
    }
    @JsonGetter("current_image")
    public String getCurrentImageID() {
        return currentImageID;
    }
    String itemName;
    String idlePictureID;
    @Getter
    @Setter
    @JsonIgnore
    double worldCoordX;
    @Getter
    @Setter
    @JsonIgnore
    double worldCoordY;
    @JsonIgnore
    @Getter
    double screenCoordX;
    @JsonIgnore
    @Getter
    double screenCoordY;
    @JsonIgnore
    Image idleImage;
    @Setter
    Image currentImage;
    @JsonIgnore
    public Hitbox hitbox;
    String currentImageID;

    /**
     * Constructor for a map object.
     * @param worldCoordX world coordinate X
     * @param worldCoordY world coordinate Y
     * @param idlePictureID idle picture ID
     */
    @JsonCreator
    protected MapObject(@JsonProperty("x") int worldCoordX,
                        @JsonProperty("y") int worldCoordY,
                        @JsonProperty("idle_picture") String idlePictureID,
                        @JsonProperty("current_image") String currentImageID) {
        //default constructor - load the image of the respective item
        this.idlePictureID = idlePictureID;
        log.debug("Idle picture ID: {}", idlePictureID);
        log.info("Loading image for item: {}", this.getClass().getSimpleName());
        idleImage = currentImageID == null ? loadImage(idlePictureID) : loadImage(currentImageID);
        log.info("Image loaded for item: {}", this.getClass().getSimpleName());
        //set the world coordinates of the item, do not place it in the top left corner of the tile
        this.worldCoordX = worldCoordX * TILE_SIZE;
        this.worldCoordY = worldCoordY * TILE_SIZE;
        this.hitbox = new Hitbox(this, (int) this.idleImage.getWidth(), (int) this.idleImage.getHeight());
        this.setCurrentImage(idleImage);
        this.currentImageID = idlePictureID;
    }

    /**
     * Method to load an image.
     * @param pictureID The ID of the picture.
     * @return The image.
     */
    public Image loadImage(String pictureID) {
        String filepath = "res/map_objects/" + pictureID;
        Image image;
        try {
            FileInputStream fis = new FileInputStream(filepath);
            if (pictureID.equals("snowy_bush.png")){
                image = new Image(fis, 32, 32, false, false);
            } else image = new Image(fis);
        } catch (FileNotFoundException e) {
            log.error("Error loading the image {}, loading default tile", pictureID);
            try {
                FileInputStream fis = new FileInputStream(DEFAULT_TILE_FILEPATH);
                image = new Image(fis);
                log.info("Default tile loaded successfully");
            } catch (FileNotFoundException ex) {
                log.error("Error loading default tile", ex);
                image = null;
            }
        }
        return image;
    }

    /**
     * Method to render the map object.
     * @param gc The graphics context.
     * @param gamePanel The game panel.
     */
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
            gc.drawImage(currentImage, screenCoordX, screenCoordY);
        }
    }
}
