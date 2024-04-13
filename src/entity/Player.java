package entity;

import controller.InputHandler;
import gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;

public class Player extends Character {
    InputHandler input;
    GamePanel game;
    public int worldCoordX;
    public int worldCoordY;
    private int screenCoordX;
    private int screenCoordY;
    private int playerSpeed;
    private String direction;
    boolean isMoving = false;
    private Image currentSprite;

    public Player(GamePanel game, InputHandler input) {
        this.game = game;
        this.input = input;
        getPlayerImage();
    }

    public void setDefaultValues(int beginX, int beginY){
        this.screenCoordX = GamePanel.SCREEN_MIDDLE_X;
        this.screenCoordY = GamePanel.SCREEN_MIDDLE_Y;
        this.worldCoordX = beginX;
        this.worldCoordY = beginY;
        currentSprite = down1;
        playerSpeed = 5; // Adjust this value as needed
        direction = "down";
    }

    public void update(){
        if(input.upPressed){
            worldCoordY -= playerSpeed;
            direction = "up";
        }
        if(input.downPressed){
            worldCoordY += playerSpeed;
            direction = "down";
        }
        if(input.leftPressed){
            worldCoordX -= playerSpeed;
            direction = "left";
        }
        if(input.rightPressed){
            worldCoordX += playerSpeed;
            direction = "right";
        }

        isMoving = input.upPressed || input.downPressed || input.leftPressed || input.rightPressed;
    }
    public void getPlayerImage() {
        // Load the sprites
        try {
            up1 = new Image(new FileInputStream("res/player/playerUp1.png"));
            up2 = new Image(new FileInputStream("res/player/playerUp2.png"));
            down1 = new Image(new FileInputStream("res/player/playerDown1.png"));
            down2 = new Image(new FileInputStream("res/player/playerDown2.png"));
            left1 = new Image(new FileInputStream("res/player/playerLeft1.png"));
            left2 = new Image(new FileInputStream("res/player/playerLeft2.png"));
            right1 = new Image(new FileInputStream("res/player/playerRight1.png"));
            right2 = new Image(new FileInputStream("res/player/playerRight2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(GraphicsContext gc) {
        //create movement effect by switching between two sprites
        currentSprite = switch (this.direction){
            case "up" -> (isMoving) ? up1 : up2;
            case "down" -> (isMoving) ? down1 : down2;
            case "left" -> (isMoving) ? left1 : left2;
            case "right" -> (isMoving) ? right1 : right2;
            default -> null;
        };
        assert currentSprite != null;

    /*
    TODO: set current player position - if player's world position is less than half of the screen width
     away from the map border set the position to worldCoordX - screenCoordX
     */
        //preventing different sprite dimensions by scaling the sprite to the size of the tile
        gc.drawImage(currentSprite, worldCoordX, worldCoordY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
    }
}