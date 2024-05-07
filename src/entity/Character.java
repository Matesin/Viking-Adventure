package entity;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static gameloop.Constants.Screen.*;
import static gameloop.Constants.Screen.SCREEN_HEIGHT;
import static gameloop.Constants.Tile.TILE_SIZE;
import static gameloop.Constants.Directions.*;
import static gameloop.Constants.GraphicsDefaults.*;

//DECLARE JSON SUBTYPES
@JsonTypeInfo(  use = JsonTypeInfo.Id.NAME,
        property = "type")

@JsonSubTypes({
        @JsonSubTypes.Type(value = TaskVillager.class, name = "task_villager"),
        @JsonSubTypes.Type(value = Mob.class, name = "mob"),
        @JsonSubTypes.Type(value = Warden.class, name = "warden"),
})
@Slf4j
public abstract class Character {
    @Getter
    @Setter
    int hp; // will differ by character type
    @Getter
    int worldCoordX;
    @Getter
    int worldCoordY;
    @Getter
    @Setter
    private int screenCoordX;
    @Getter
    @Setter
    private int screenCoordY;
    @Getter
    String direction = "down";
    @Getter
    @Setter
    int speed;
    int width;
    int height;
    public Hitbox hitbox;
    @Getter
    @Setter
    boolean collision = false;
    @Getter
    @Setter
    private boolean isMoving = false;
    Image up1;
    Image up2;
    Image down1;
    Image down2;
    Image left1;
    Image left2;
    Image right1;
    Image right2;
    Image currentSprite;
    String name;

    protected Character(int worldCoordX, int worldCoordY) {
        this.worldCoordX = worldCoordX * TILE_SIZE;
        this.worldCoordY = worldCoordY * TILE_SIZE;
        String type = this.getClass().getSimpleName().toLowerCase();
        getSprites(type);
        currentSprite = down1;
        if (!(this instanceof Player)){
            this.hitbox = new Hitbox(this);
            log.debug("Hitbox created with dimensions: {} x {}", hitbox.getWidth(), hitbox.getHeight());
            log.debug("Hitbox of {} covers area from x: {}, y: {} to x: {}, y: {}", this.getClass().getSimpleName(), hitbox.getCoordX(), hitbox.getCoordY(), hitbox.getCoordX() + hitbox.getWidth(), hitbox.getCoordY() + hitbox.getHeight());
        }
    }
     void getSprites(String type){
        // Load the character's sprites
         log.info("Loading sprites for: {}", type);
        try {
            up1 = setSprite(1, DIR_UP, type);
            up2 = setSprite(2, DIR_UP, type);
            down1 = setSprite(1, DIR_DOWN, type);
            down2 = setSprite(2, DIR_DOWN, type);
            left1 = setSprite(1, DIR_LEFT, type);
            left2 = setSprite(2, DIR_LEFT, type);
            right1 = setSprite(1, DIR_RIGHT, type);
            right2 = setSprite(2, DIR_RIGHT, type);
            log.info("Sprites loaded successfully");
        } catch (FileNotFoundException e) {
            log.error("Error loading entity sprites: {}", e.getMessage());
        }
    }
     Image setSprite(int spriteNum, String direction, String type) throws FileNotFoundException {
        // Load the sprite
        Image sprite;
        if (!direction.isEmpty()){
            // Capitalize the first letter of the direction
            direction = direction.substring(0, 1).toUpperCase() + direction.substring(1);
        }
         String filepath = "res" + File.separator + "entities" + File.separator + type + File.separator + type + direction + spriteNum + ".png";        try {
            FileInputStream fis = new FileInputStream(filepath);
            sprite = new Image(fis, TILE_SIZE, TILE_SIZE, false, false);
        } catch (FileNotFoundException e) {
            log.error("Error loading entity sprites: {}, loading default instead", e.getMessage());
            try{
                FileInputStream fis = new FileInputStream(DEFAULT_TILE_FILEPATH);
                sprite = new Image(fis);
            } catch (FileNotFoundException ex){
                throw new FileNotFoundException("Error loading default sprite");
            }
        }
        return sprite;
    }

    public void update(){
        // Update the character's position
        if (isMoving) move();
        hitbox.update();
    }
    public void render(GraphicsContext gc, GamePanel gamePanel){
        // Render the character
        this.currentSprite = down1;
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
            gc.drawImage(this.currentSprite, screenCoordX, screenCoordY);
            hitbox.display(gc);
        }
    }
    private void move(){
        // Move the character
        switch (direction){
            case DIR_UP:
                worldCoordY -= speed;
                break;
            case DIR_DOWN:
                worldCoordY += speed;
                break;
            case DIR_LEFT:
                worldCoordX -= speed;
                break;
            case DIR_RIGHT:
                worldCoordX += speed;
                break;
            default:
                break;
        }
    }
}
