package map;

import entity.Player;
import gameloop.Camera;
import gameloop.GamePanel;
import item.Item;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    public void loadMap(int mapIndex) {
        // Load the map
        log.info("Loading map {}", mapIndex);
        String mapIndexString = mapIndex < 10 ? "0" + mapIndex : "" + mapIndex;
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
