package cz.cvut.fel.pjv.entity;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static cz.cvut.fel.pjv.gameloop.Constants.Screen.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Screen.SCREEN_HEIGHT;
import static cz.cvut.fel.pjv.gameloop.Constants.Tile.TILE_SIZE;
import static cz.cvut.fel.pjv.gameloop.Constants.Directions.*;
import static cz.cvut.fel.pjv.gameloop.Constants.GraphicsDefaults.*;

//DECLARE JSON SUBTYPES
@JsonTypeInfo(  use = JsonTypeInfo.Id.NAME,
        property = "type")

@JsonSubTypes({
        @JsonSubTypes.Type(value = TaskVillager.class, name = "task_villager"),
        @JsonSubTypes.Type(value = Mob.class, name = "mob"),
        @JsonSubTypes.Type(value = Warden.class, name = "warden"),
})
/**
 * Abstract class representing a character in the game.
 * This class is extended by specific types of characters.
 */
@Slf4j
public abstract class Character {
    @Getter
    String type;
    @Getter
    @Setter
    int hp; // will differ by character type
    @Getter
    @Setter
    double worldCoordX;
    @Getter
    @Setter
    double worldCoordY;
    @Getter
    @Setter
    @JsonIgnore
    private double screenCoordX;
    @Getter
    @Setter
    @JsonIgnore
    private double screenCoordY;
    @Getter
    String direction = "down";
    @Getter
    @Setter
    @JsonIgnore
    int speed;
    @JsonIgnore
    int width;
    @JsonIgnore
    int height;
    @JsonIgnore
    public Hitbox hitbox;
    @Getter
    @Setter
    @JsonIgnore
    boolean collision = false;
    @Getter
    @Setter
    @JsonIgnore
    private boolean isMoving = false;
    @Getter
    @Setter
    private int health;
    Image upIdle;
    Image up1;
    Image up2;
    Image downIdle;
    Image down1;
    Image down2;
    Image leftIdle;
    Image left1;
    Image left2;
    Image rightIdle;
    Image right1;
    Image right2;
    Image currentSprite;
    /**
     * Constructor for Character.
     *
     * @param worldCoordX the x-coordinate of the character in the world
     * @param worldCoordY the y-coordinate of the character in the world
     */
    protected Character(double worldCoordX, double worldCoordY) {
        this.worldCoordX = worldCoordX * TILE_SIZE;
        this.worldCoordY = worldCoordY * TILE_SIZE;
        type = this.getClass().getSimpleName().toLowerCase();
        getSprites(type);
        currentSprite = down1;
        if (!(this instanceof Player)){
            this.hitbox = new Hitbox(this);
            log.debug("Hitbox created with dimensions: {} x {}", hitbox.getWidth(), hitbox.getHeight());
            log.debug("Hitbox of {} covers area from x: {}, y: {} to x: {}, y: {}", this.getClass().getSimpleName(), hitbox.getCoordX(), hitbox.getCoordY(), hitbox.getCoordX() + hitbox.getWidth(), hitbox.getCoordY() + hitbox.getHeight());
        }
    }
    /**
     * Loads the sprites for the character.
     *
     * @param type the type of the character
     */
     void getSprites(String type){
        // Load the character's sprites
         log.info("Loading sprites for: {}", type);
        try {
            upIdle = setIdleSprite(DIR_UP, type);
            downIdle = setIdleSprite(DIR_DOWN, type);
            leftIdle = setIdleSprite(DIR_LEFT, type);
            rightIdle = setIdleSprite(DIR_RIGHT, type);
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
    /**
     * Loads a specific sprite for the character.
     *
     * @param spriteNum the number of the sprite
     * @param direction the direction of the sprite
     * @param type the type of the character
     * @return the loaded sprite
     * @throws FileNotFoundException if the sprite file is not found
     */
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
    /**
     * Loads the idle sprite for the character.
     *
     * @param direction the direction of the sprite
     * @param type the type of the character
     * @return the loaded sprite
     * @throws FileNotFoundException if the sprite file is not found
     */
    Image setIdleSprite(String direction, String type) throws FileNotFoundException {
        // Load the sprite
        Image sprite;
        if (!direction.isEmpty()){
            // Capitalize the first letter of the direction
            direction = direction.substring(0, 1).toUpperCase() + direction.substring(1);
        }
        String filepath = "res" + File.separator + "entities" + File.separator + type + File.separator + type + direction + "Idle" + ".png";        try {
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
    /**
     * Updates the state of the character.
     */
    public void update(){
        // Update the character's position
        if (isMoving) move();
        hitbox.update();
    }
    /**
     * Renders the character on the screen.
     *
     * @param gc the graphics context to draw on
     * @param gamePanel the game panel to draw on
     */
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
//            hitbox.display(gc);
        }
    }
    /**
     * Moves the character in the current direction.
     */
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
