package entity;

import controller.InputHandler;
import gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static javafx.scene.paint.Color.RED;
import static gameloop.Constants.Screen.*;
import static gameloop.Constants.Tile.*;

@Slf4j
public class Player extends Character {
    InputHandler input;
    GamePanel gamePanel;
    private boolean isMoving = false;
    private boolean oddIteration = false;
    @Getter
    private Image currentSprite;

    public Player(int worldCoordX, int worldCoordY, GamePanel gamePanel, InputHandler input) {
        super(worldCoordX, worldCoordY);
        this.gamePanel = gamePanel;
        this.input = input;
        this.height = TILE_SIZE;
        this.width = TILE_SIZE;
        this.hitbox = new Hitbox(this, TILE_SIZE / 3, TILE_SIZE / 2, (this.width - TILE_SIZE / 2) / 2, this.height /3);
        setDefaultValues();
        getImage();
    }

    public void setDefaultValues(){
        this.setScreenCoordX(SCREEN_MIDDLE_X);
        this.setScreenCoordY(SCREEN_MIDDLE_Y);
        currentSprite = down1;
        this.speed = 5; // Adjust this value as needed
        direction = "down";
        hitbox.update();
    }

    @Override
    public void update(){
        if(input.isUpPressed()){
            direction = "up";
        }
        if(input.isDownPressed()){
            direction = "down";
        }
        if(input.isLeftPressed()){
            direction = "left";
        }
        if(input.isRightPressed()){
            direction = "right";
        }
        isMoving = input.isUpPressed() || input.isDownPressed() || input.isLeftPressed() || input.isRightPressed();

        if (isMoving) {
            hitbox.update();
            //check for collision
            collision = false; //reset collision
            gamePanel.collisionChecker.checkTile(this);
            //if there is no collision, move the player
            if (!collision) {
                switch (direction) {
                    case "up":
                        worldCoordY -= this.speed;
                        break;
                    case "down":
                        worldCoordY += this.speed;
                        break;
                    case "left":
                        worldCoordX -= this.speed;
                        break;
                    case "right":
                        worldCoordX += this.speed;
                        break;
                    default:
                        break;
                }
            }
        }
    }
    public void getImage() {
        // Load the sprites
        try {
            up1 = setSprite(1, "up");
            up2 = setSprite(2, "up");
            down1 = setSprite(1, "down");
            down2 = setSprite(2, "down");
            left1 = setSprite(1, "left");
            left2 = setSprite(2, "left");
            right1 = setSprite(1, "right");
            right2 = setSprite(2, "right");
        } catch (IOException e) {
            log.error("Error loading player sprites: {}", e.getMessage());
        }
    }
    private Image setSprite(int spriteNum, String direction) throws FileNotFoundException {
        // Load the sprite
        if (!direction.isEmpty()){
            // Capitalize the first letter of the direction
            direction = direction.substring(0, 1).toUpperCase() + direction.substring(1);
        }
        String filepath = "res/player/player" + direction + spriteNum + ".png";
        FileInputStream fis = new FileInputStream(filepath);
        return new Image(fis, TILE_SIZE, TILE_SIZE, false, false);
    }

    private long lastUpdate = 0;

    @Override
    public void render(GraphicsContext gc) {
        //create movement effect by switching between two sprites
        gc.setFill(RED);
        long now = System.currentTimeMillis();
        // update the sprite relatively to speed
        int speedConst = 1000;
        if (now - lastUpdate > speedConst / this.speed) {
            currentSprite = !(isMoving) ? currentSprite : switch (this.direction){
                case "up" -> oddIteration ? up1 : up2;
                case "down" -> oddIteration ? down1 : down2;
                case "left" -> oddIteration ? left1 : left2;
                case "right" -> oddIteration ? right1 : right2;
                default -> null;
            };
            oddIteration = !oddIteration;
            lastUpdate = now;
        }
        gc.drawImage(currentSprite, getScreenCoordX(), getScreenCoordY());
    }
}