package map;

import com.sun.media.jfxmedia.events.PlayerEvent;
import entity.Player;
import gameloop.GamePanel;
import item.Item;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapManager {
    private static final Logger log = LoggerFactory.getLogger(MapManager.class);
    /*
    * The map manager class is responsible for managing the game map.
    * */
    public GameMap map;
    @Getter
    private int mapWidth;
    @Getter
    private int mapHeight;
    GamePanel gamePanel;
    public MapManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void generateMap() {
        // Generate the map - feature to be implemented later
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
            worldX = worldCol * GamePanel.TILE_SIZE;
            worldY = worldRow * GamePanel.TILE_SIZE;
            screenX = worldX - gamePanel.player.getWorldCoordX() + GamePanel.SCREEN_MIDDLE_X;
            screenY = worldY - gamePanel.player.getWorldCoordY() + GamePanel.SCREEN_MIDDLE_Y;
            if (screenX >= - GamePanel.TILE_SIZE &&
                screenX <= GamePanel.SCREEN_WIDTH &&
                screenY >= - GamePanel.TILE_SIZE &&
                screenY <= GamePanel.SCREEN_HEIGHT) {
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
    public void loadMap(int mapIndex) {
        // Load the map
        log.info("Loading map {}", mapIndex);
        String mapIndexString = mapIndex < 10 ? "0" + mapIndex : "" + mapIndex;
        String filepath = "res/maps/map" + mapIndexString + "/map.txt";
        this.map = new GameMap();
        try {
            this.map.loadMapFromFile(filepath);
            this.mapWidth = this.map.getMapWidth();
            this.mapHeight = this.map.getMapHeight();
        } catch (FileNotFoundException e) {
            log.error("Map file not found {}", e.getMessage());
        }
        log.info("Map loaded");
    }
    public void loadObjects(String mapIndex) {
//        // Load objects - feature to be implemented later
//        log.info("Loading objects for map {}", mapIndex);
//        String filepath = "res/maps/map" + mapIndex + "/obj.json";
//        try{
//
//        } catch (FileNotFoundException e) {
//            log.error("Object file not found {}", e.getMessage());
//        }
    }
    void setType(String type) {

    }
    public void saveMap() {
        // Save the map - feature to be implemented later
    }
}
