package map;

import entity.Character;
import gameloop.Camera;
import gameloop.GamePanel;
import handling.JsonEntityHandler;
import handling.JsonItemHandler;
import item.Item;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import static gameloop.Constants.Screen.*;
import static gameloop.Constants.Tile.*;

@Slf4j
public class MapManager {
    public GameMap map;
    @Getter
    private int mapWidth;
    @Getter
    private int mapHeight;
    GamePanel gamePanel;
    private final Camera camera;
    Optional<List<Item>> items = Optional.empty();
    Optional<List<Character>> entities = Optional.empty();
    JsonItemHandler itemHandler = new JsonItemHandler();
    JsonEntityHandler entityHandler = new JsonEntityHandler();

    public MapManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.camera = gamePanel.camera;
    }

    public void renderMap(GraphicsContext gc) {
        int worldCol = 0;
        int worldRow = 0;
        int worldX;
        int worldY;
        int screenX;
        int screenY;
        while (worldRow < mapHeight && worldCol < mapWidth){
            int tileID = map.getMap()[worldCol][worldRow];
            worldX = worldCol * TILE_SIZE;
            worldY = worldRow * TILE_SIZE;
            screenX = worldX - gamePanel.player.getWorldCoordX() + SCREEN_MIDDLE_X;
            screenY = worldY - gamePanel.player.getWorldCoordY() + SCREEN_MIDDLE_Y;
            if(gamePanel.player.getWorldCoordX() < SCREEN_MIDDLE_X) {
                screenX = worldX;
            }
            if (gamePanel.player.getWorldCoordY() < SCREEN_MIDDLE_Y) {
                screenY = worldY;
            }
            if (gamePanel.player.getWorldCoordX() > mapWidth * TILE_SIZE - SCREEN_MIDDLE_X) {
                screenX = worldX - (mapWidth * TILE_SIZE - SCREEN_WIDTH);
            }
            if (gamePanel.player.getWorldCoordY() > mapHeight * TILE_SIZE - SCREEN_MIDDLE_Y) {
                screenY = worldY - (mapHeight * TILE_SIZE - SCREEN_HEIGHT);
            }
            if (screenX >= - TILE_SIZE &&
                screenX <= SCREEN_WIDTH &&
                screenY >= - TILE_SIZE &&
                screenY <= SCREEN_HEIGHT) {
                gc.drawImage(map.getTiles()[tileID].getImage(), screenX, screenY);
            }
            worldCol++;
            if (worldCol == mapWidth) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
    public void updateMap() {
        // Update the map
    }
    public void loadMap() {
        // Load the map
        String mapIndexString = gamePanel.getMapIDString();
        log.info("Loading map {}", mapIndexString);
        String filepath = "res/maps/map" + mapIndexString + "/map.txt";
        this.map = new GameMap(mapIndexString);
        try {
            this.map.loadMapFromFile(filepath);
            this.mapWidth = this.map.getMapWidth();
            this.mapHeight = this.map.getMapHeight();
        } catch (FileNotFoundException e) {
            log.error("Map file not found {}", e.getMessage());
        }
        log.info("Map loaded");
    }
    public void loadObjects(int save) {
        log.info("Loading objects for map {}", gamePanel.getMapIDString());
        String filepath = "res/maps/map" + gamePanel.getMapID() + "/items.json";
        items = itemHandler.deserializeItemsFromFile(filepath);
        log.info("Items loaded");
    }
    public void loadEntities() {
        // Load entities - feature to be implemented later
        log.info("Loading entities");
        String filepath = "res/maps/map" + gamePanel.getMapIDString() + "/ent.json";
        entities = entityHandler.deserializeCharactersFromFile(filepath);
        log.info("Entities loaded");
    }
    void setType(String type) {

    }
    public void saveMap() {
        // Save the map - feature to be implemented later
    }
}
