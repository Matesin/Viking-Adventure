package entity;

import com.sun.tools.javac.Main;
import controller.InputHandler;
import gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
public class Player extends Character {
    InputHandler input;
    GamePanel game;
    @Getter
    @Setter
    private int screenCoordX;
    @Getter
    @Setter
    private int screenCoordY;
    private int playerSpeed;
    private String direction;
    private boolean isMoving = false;
    private boolean oddIteration = false;
    @Getter
    private Image currentSprite;


    public Player(GamePanel game, InputHandler input) {
        this.game = game;
        this.input = input;
        getPlayerImage();
    }

    public void setDefaultValues(int beginX, int beginY){
        this.worldCoordX = beginX;
        this.worldCoordY = beginY;
        this.screenCoordX = GamePanel.SCREEN_MIDDLE_X;
        this.screenCoordY = GamePanel.SCREEN_MIDDLE_Y;
        currentSprite = down1;
        playerSpeed = 5; // Adjust this value as needed
        direction = "down";
    }

    public void update(){
        if(input.isUpPressed()){
            worldCoordY -= playerSpeed;
            direction = "up";
        }
        if(input.isDownPressed()){
            worldCoordY += playerSpeed;
            direction = "down";
        }
        if(input.isLeftPressed()){
            worldCoordX -= playerSpeed;
            direction = "left";
        }
        if(input.isRightPressed()){
            worldCoordX += playerSpeed;
            direction = "right";
        }


        isMoving = input.isUpPressed() || input.isDownPressed() || input.isLeftPressed() || input.isRightPressed();
    }
    public void getPlayerImage() {
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
        return new Image(fis, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, false, false);
    }

    public void render(GraphicsContext gc) {
        //create movement effect by switching between two sprites
        currentSprite = !(isMoving) ? currentSprite : switch (this.direction){
            case "up" -> oddIteration ? up1 : up2;
            case "down" -> oddIteration ? down1 : down2;
            case "left" -> oddIteration ? left1 : left2;
            case "right" -> oddIteration ? right1 : right2;
            default -> null;
        };
        oddIteration = !oddIteration;
        assert currentSprite != null;

        //preventing different sprite dimensions by scaling the sprite to the size of the tile
        gc.drawImage(currentSprite, GamePanel.SCREEN_MIDDLE_X, GamePanel.SCREEN_MIDDLE_Y);
    }
}