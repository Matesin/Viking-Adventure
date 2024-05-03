package handling;

import entity.Player;
import gameloop.Camera;
import gameloop.GamePanel;
import item.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static gameloop.Constants.Screen.*;
import static gameloop.Constants.Tile.TILE_SIZE;

@Slf4j
public class ItemManager {
    private Optional<List<Item>> items;
    private final Camera camera;
    private final GamePanel gamePanel;
    private final Player player;
    public ItemManager(GamePanel gamePanel){
        // Set the items for the game
        ItemSetter itemSetter = new ItemSetter(gamePanel.getMapIDString(), gamePanel.loadSaved);
        this.items = itemSetter.setItems();
        this.gamePanel = gamePanel;
        this.camera = gamePanel.getCamera();
        this.player = gamePanel.player;
    }
    public boolean isOnScreen(Item item){
        int cameraX = camera.getCameraX();
        int cameraY = camera.getCameraY();
        int itemX = item.getWorldCoordX();
        int itemY = item.getWorldCoordY();
        return itemX >= cameraX - TILE_SIZE && itemX <= cameraX + SCREEN_WIDTH + TILE_SIZE &&
                itemY >= cameraY - TILE_SIZE && itemY <= cameraY + SCREEN_HEIGHT + TILE_SIZE;

    }
    public void renderItems(GraphicsContext gc){
        if (items.isPresent()) {
            for (Item item : items.orElseThrow()) {
                if (isOnScreen(item)) {
                    item.render(gc, this.gamePanel);
                    if(player.getHitbox().intersects(item.hitbox)){
                        gc.setFill(Color.BLACK);
                        Font statsFont = Font.font("Segoe Script", FontWeight.BOLD, 10);
                        gc.fillText("You picked up a " + item.getName(), SCREEN_MIDDLE_X, SCREEN_MIDDLE_Y);
                    }
//                    item.hitbox.display(gc);
                }
            }
        }
    }
}
