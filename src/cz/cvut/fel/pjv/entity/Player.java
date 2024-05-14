package cz.cvut.fel.pjv.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fel.pjv.controller.InputHandler;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import cz.cvut.fel.pjv.inventory.Inventory;
import cz.cvut.fel.pjv.item.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static cz.cvut.fel.pjv.gameloop.Constants.Inventory.INITIAL_INVENTORY_CAPACITY;
import static cz.cvut.fel.pjv.gameloop.Constants.Screen.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Tile.*;

@Slf4j
public class Player extends Character {
    InputHandler input;
    GamePanel gamePanel;
    private boolean isMoving = false;
    private boolean oddIteration = false;
    @Getter
    @Setter
    private Inventory inventory;
    private int health;

    public Player( int worldCoordX, int worldCoordY, GamePanel gamePanel, InputHandler input) {
        super(worldCoordX, worldCoordY);
        this.gamePanel = gamePanel;
        this.input = input;
        this.height = TILE_SIZE;
        this.width = TILE_SIZE;
        this.inventory = new Inventory(INITIAL_INVENTORY_CAPACITY);
        this.hitbox = new Hitbox(this, TILE_SIZE / 3, TILE_SIZE / 2, (this.width - TILE_SIZE / 2) / 2, this.height /3);
        setDefaultValues();
        update();
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

    private long lastUpdate = 0;
    public void render(GraphicsContext gc) {
        //create movement effect by switching between two sprites
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
    public boolean pickUpItem(Item item){
        boolean pickedUpItem = false;
        if (input.isPickUp()){
            for (int i = 0; i < inventory.getCapacity(); i++) {
                if (inventory.getItems()[i] == null) {
                    inventory.getItems()[i] = item;
                    log.info("Picked up {}", item.getName());
                    input.setPickUp(false);
                    pickedUpItem = true;
                    break;
                }
            }
            input.setPickUp(false);
            inventory.setFull(!pickedUpItem);
        }
        return pickedUpItem;
    }
}