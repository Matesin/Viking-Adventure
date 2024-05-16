package cz.cvut.fel.pjv.map;

import cz.cvut.fel.pjv.entity.Character;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import cz.cvut.fel.pjv.handling.JsonEntityHandler;
import cz.cvut.fel.pjv.handling.JsonItemHandler;
import cz.cvut.fel.pjv.handling.JsonMapObjectHandler;
import cz.cvut.fel.pjv.item.Item;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import cz.cvut.fel.pjv.map_object.MapObject;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import static cz.cvut.fel.pjv.gameloop.Constants.Screen.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Tile.*;

@Slf4j
public class MapManager {
    private static final String MAP_PATH = "res/maps/map";
    @Getter
    private GameMap map;
    @Getter
    private int mapWidth;
    @Getter
    private int mapHeight;
    GamePanel gamePanel;
    Optional<List<Item>> items = Optional.empty();
    Optional<List<Character>> entities = Optional.empty();
    Optional<List<MapObject>> mapObjects = Optional.empty();
    JsonItemHandler itemHandler = new JsonItemHandler();
    JsonEntityHandler entityHandler = new JsonEntityHandler();
    JsonMapObjectHandler mapObjectHandler = new JsonMapObjectHandler();

    public MapManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
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

    public void loadMap() {
        // Load the map
        String mapIndexString = gamePanel.getMapIDString();
        log.info("Loading map {}", mapIndexString);
        String filepath = gamePanel.isLoadSaved() ? "res/save/map.txt" : MAP_PATH + mapIndexString + "/map.txt";
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
    public void saveMap(){
        log.info("Saving map");
        String filepath = "res/save/map.txt";
        this.map.save(filepath, gamePanel.player);
        log.info("Map saved");
    }

    public void loadItems() {
        log.info("Loading objects for map {}", gamePanel.getMapIDString());
        String filepath = gamePanel.isLoadSaved() ? "res/save/items.json" : MAP_PATH + gamePanel.getMapID() + "/items.json";
        items = itemHandler.deserializeItemsFromFile(filepath);
        log.info("Items loaded");
    }

    public void loadEntities() {
        // Load entities - feature to be implemented later
        log.info("Loading entities");
        String filepath = gamePanel.isLoadSaved() ? "res/save/ent.json" : MAP_PATH + gamePanel.getMapIDString() + "/ent.json";
        entities = entityHandler.deserializeCharactersFromFile(filepath);
        log.info("Entities loaded");
    }
    public void loadMapObjects(){
        // Load map objects - feature to be implemented later
        log.info("Loading map objects");
        String filepath = gamePanel.isLoadSaved() ? "res/save/map_obj.json" : MAP_PATH + gamePanel.getMapIDString() + "/map_objects.json";
        mapObjects = mapObjectHandler.deserializeObjectsFromFile(filepath);
        log.info("Map objects loaded");
    }
}