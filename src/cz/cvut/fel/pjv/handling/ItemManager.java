package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.gameloop.Camera;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import cz.cvut.fel.pjv.item.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static cz.cvut.fel.pjv.gameloop.Constants.Screen.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Tile.TILE_SIZE;

/**
 * Class responsible for managing items in the game.
 * This class provides methods for rendering items on the screen and saving items.
 */
@Slf4j
public class ItemManager {
    @Getter
    private Optional<List<Item>> items;
    private final Camera camera;
    private final GamePanel gamePanel;
    private final Player player;

    /**
     * Constructor for ItemManager with specified game panel.
     *
     * @param gamePanel the game panel
     */
    public ItemManager(GamePanel gamePanel){
        // Set the items for the game
        ItemSetter itemSetter = new ItemSetter(gamePanel.getMapIDString(), gamePanel.isLoadSaved());
        this.items = itemSetter.setItems();
        this.gamePanel = gamePanel;
        this.camera = gamePanel.getCamera();
        this.player = gamePanel.player;
    }

    /**
     * Checks if an item is on the screen.
     *
     * @param item the item to check
     * @return true if the item is on the screen, false otherwise
     */
    public boolean isOnScreen(Item item){
        double cameraX = camera.getCameraX();
        double cameraY = camera.getCameraY();
        double itemX = item.getWorldCoordX();
        double itemY = item.getWorldCoordY();
        return itemX >= cameraX - TILE_SIZE && itemX <= cameraX + SCREEN_WIDTH + TILE_SIZE &&
                itemY >= cameraY - TILE_SIZE && itemY <= cameraY + SCREEN_HEIGHT + TILE_SIZE;

    }

    /**
     * Renders items if they are on the screen and shows a pickup prompt if the player is close to an item.
     *
     * @param gc the graphics context
     */
    public void renderItems(GraphicsContext gc){
        if (items.isPresent()) {
            Iterator<Item> iterator = items.get().iterator();
            while (iterator.hasNext()) {
                Item item = iterator.next();
                if (isOnScreen(item)) {
                    renderOnScreenItem(gc, iterator, item);
                }
            }
        }
    }

    private void renderOnScreenItem(GraphicsContext gc, Iterator<Item> iterator, Item item) {
        item.render(gc, this.gamePanel);
        if(player.hitbox.intersects(item.hitbox)){
            renderPickupPrompt(gc, item);
            if(player.pickUpItem(item)){
                iterator.remove();
            }
        }
    }

    private void renderPickupPrompt(GraphicsContext gc, Item item) {
        gc.setFill(Color.BLACK);
        Font statsFont = Font.font("Segoe Script", FontWeight.BOLD, 15);
        gc.setFont(statsFont);
        if(!player.getInventory().isFull()){
            gc.fillText("Press 'E' to pick up this " + item.getName(), SCREEN_MIDDLE_X, SCREEN_MIDDLE_Y);
        } else {
            gc.fillText("Inventory is full!", SCREEN_MIDDLE_X, SCREEN_MIDDLE_Y);
        }
    }

    /**
     * Saves the items to a file.
     */
    public void saveItems(){
        // Save the items
        log.info("Saving items...");
        Path path = Paths.get("res" , "save","obj.json");
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                log.error("Error deleting the file", e);
            }
        }
        JsonItemHandler jsonItemHandler = new JsonItemHandler();
        if (items.isPresent()){
            try {
                jsonItemHandler.serializeItemsToFile(items.get(), "res/save/obj.json");
            } catch (Exception e) {
                log.error("Error saving items", e);
            }
            log.info("Items saved successfully");
        } else {
            //create an empty file
            try {
                if (!Files.createFile(path).toFile().createNewFile()){
                    log.error("Error creating empty file");
                }
                log.info("Empty items file created");
            } catch (Exception e) {
                log.error("Error creating empty file", e);
            }
        }
    }
}
