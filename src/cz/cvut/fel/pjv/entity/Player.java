package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.controller.InputHandler;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import cz.cvut.fel.pjv.inventory.Inventory;
import cz.cvut.fel.pjv.item.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static cz.cvut.fel.pjv.gameloop.Constants.Directions.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Inventory.INITIAL_INVENTORY_CAPACITY;
import static cz.cvut.fel.pjv.gameloop.Constants.Player.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Screen.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Tile.*;

/**
 * Class representing a player in the game.
 * This class extends the Character class and handles player-specific behavior.
 */
@Slf4j
public class Player extends Character {
    public final Hitbox reactionRange;
    InputHandler input;
    GamePanel gamePanel;
    private boolean isMoving = false;
    private int spriteCounter = 1;
    @Getter
    @Setter
    private Inventory inventory;
    private long lastUpdate = 0;
    double lastCoordX;
    double lastCoordY;
    /**
     * Constructor for Player with specified world coordinates, game panel, and input handler.
     *
     * @param worldCoordX the x-coordinate of the player in the world
     * @param worldCoordY the y-coordinate of the player in the world
     * @param gamePanel the game panel
     * @param input the input handler
     */
    public Player(double worldCoordX, double worldCoordY, GamePanel gamePanel, InputHandler input) {
        super(worldCoordX, worldCoordY);
        this.gamePanel = gamePanel;
        this.input = input;
        this.height = TILE_SIZE;
        this.width = TILE_SIZE;
        this.inventory = new Inventory(INITIAL_INVENTORY_CAPACITY);
        int hitboxOffsetX = (this.width - TILE_SIZE / 2) / 2;
        int hitboxOffsetY = this.height / 3;
        this.hitbox = new Hitbox(this, TILE_SIZE / 3, TILE_SIZE / 2, hitboxOffsetX, hitboxOffsetY);
        double reactionRangeWidth = this.hitbox.getWidth() * 3;
        double reactionRangeHeight = this.hitbox.getHeight() * 3;
        this.reactionRange = new Hitbox(this, reactionRangeWidth, reactionRangeHeight, -hitboxOffsetX/4, -hitboxOffsetY /2);
        setDefaultValues();
    }
    /**
     * Sets the default values for the player.
     */
    public void setDefaultValues(){
        this.setScreenCoordX(SCREEN_MIDDLE_X);
        this.setScreenCoordY(SCREEN_MIDDLE_Y);
        currentSprite = down1;
        this.speed = INITIAL_SPEED; // Adjust this value as needed
        direction = DIR_DOWN;
        hitbox.update();
    }
    /**
     * Updates the player's state and position
     */
    @Override
    public void update(){

        if(input.isUpPressed()){
            direction = DIR_UP;
        }
        if(input.isDownPressed()){
            direction = DIR_DOWN;
        }
        if(input.isLeftPressed()){
            direction = DIR_LEFT;
        }
        if(input.isRightPressed()){
            direction = DIR_RIGHT;
        }
        isMoving = input.isUpPressed()
                || input.isDownPressed()
                || input.isLeftPressed()
                || input.isRightPressed()
                || (this.lastCoordX != this.worldCoordX)
                || (this.lastCoordY != this.worldCoordY);

        if (isMoving) {
            hitbox.update();
            reactionRange.update();
            //check for collision
            collision = false; //reset collision
            gamePanel.collisionChecker.checkTile(this);
            //if there is no collision, move the player
            if (!collision) {
                switch (direction) {
                    case DIR_UP:
                        worldCoordY -= this.speed;
                        break;
                    case DIR_DOWN:
                        worldCoordY += this.speed;
                        break;
                    case DIR_LEFT:
                        worldCoordX -= this.speed;
                        break;
                    case DIR_RIGHT:
                        worldCoordX += this.speed;
                        break;
                    default:
                        break;
                }
            }
        }
        lastCoordX = worldCoordX;
        lastCoordY = worldCoordY;
    }
    /**
     * Renders the player on the screen.
     *
     * @param gc the graphics context to draw on
     */
    public void render(GraphicsContext gc) {
        //create movement effect by switching between two sprites
        long now = System.currentTimeMillis();
        // update the sprite relatively to speed
        if (now - lastUpdate > SPEED_CONSTANT / this.speed) {
            //set the current sprite based on spriteCounter and direction
            currentSprite = isMoving ? setMovingSprite(spriteCounter) : setSpriteToIdle();
            spriteCounter = spriteCounter == 4 ? 1 : spriteCounter + 1; //reset spriteCounter if equal to 4
            lastUpdate = now;
        }
        gc.drawImage(currentSprite, getScreenCoordX(), getScreenCoordY());
//        this.hitbox.display(gc);
//        this.reactionRange.display(gc);
    }

    private Image setSpriteToIdle(){
        return switch (this.direction){
            case DIR_UP -> upIdle;
            case DIR_DOWN -> downIdle;
            case DIR_LEFT -> leftIdle;
            case DIR_RIGHT -> rightIdle;
            default -> null;
        };
    }

    private Image setMovingSprite(int counter){
        return switch (counter){
            case 1 -> switch (this.direction){
                case DIR_UP -> up1;
                case DIR_DOWN -> down1;
                case DIR_LEFT -> left1;
                case DIR_RIGHT -> right1;
                default -> null;
            };
            case 2, 4 -> switch (this.direction){
                case DIR_UP -> upIdle;
                case DIR_DOWN -> downIdle;
                case DIR_LEFT -> leftIdle;
                case DIR_RIGHT -> rightIdle;
                default -> null;
            };
            case 3 -> switch (this.direction){
                case DIR_UP -> up2;
                case DIR_DOWN -> down2;
                case DIR_LEFT -> left2;
                case DIR_RIGHT -> right2;
                default -> null;
            };

            default -> null;
        };
    }
    /**
     * Picks up an item.
     *
     * @param item the item to pick up
     * @return true if the item was picked up, false otherwise
     */
    public boolean pickUpItem(Item item){
        boolean pickedUpItem = false;
        if (input.isPickUp()){
            if (inventory.isEmpty() || inventory.getPickedItem() == null) {
                inventory.setPickedItem(item);
                inventory.setEmpty(false);
                gamePanel.getInGameInventoryBar().update();
            }

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

    /**
     * Reacts to a map object.
     *
     * @return true if the player reacted to the map object, false otherwise
     */
    public boolean reactToMapObject(){
        if (input.isUseItem()){
            input.setUseItem(false);
            return true;
        } else return false;
    }
    /**
     * Drops the currently picked item.
     */
    public void dropPickedItem(){
       inventory.getPickedItem().setWorldCoordX(this.worldCoordX);
       inventory.getPickedItem().setWorldCoordY(this.worldCoordY);
       gamePanel.getItemManager().getItems().ifPresent(items -> items.add(inventory.getPickedItem()));
       inventory.setPickedItem(null);
       inventory.removeItem(inventory.getPickedItem());
    }
    /**
     * Drops an item.
     *
     * @param item the item to drop
     */
    public void dropItem(Item item){
        item.setWorldCoordX(this.worldCoordX);
        item.setWorldCoordY(this.worldCoordY);
        gamePanel.getItemManager().getItems().ifPresent(items -> items.add(item));
        if (inventory.getPickedItem() == item){
            inventory.setPickedItem(null);
        }
        item.hitbox.update();
        inventory.removeItem(item);
    }

}