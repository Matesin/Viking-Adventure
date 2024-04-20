package map;

import com.sun.media.jfxmedia.events.PlayerEvent;
import entity.Player;
import gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

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
            int tileID = map.getMap()[worldRow][worldCol];
            worldX = worldCol * GamePanel.TILE_SIZE;
            worldY = worldRow * GamePanel.TILE_SIZE;
            screenX = worldX - gamePanel.player.getWorldCoordX();
            screenY = worldY - gamePanel.player.getWorldCoordY();
            //When I wrote this logic, only God and I understood what I was doing. Now, only God knows.
            if (screenX - 2 * GamePanel.TILE_SIZE < gamePanel.getMaxWorldCols() + gamePanel.player.getScreenCoordX()&&
                screenY - 2 * GamePanel.TILE_SIZE < gamePanel.getMaxWorldRows() + gamePanel.player.getScreenCoordY()&&
                screenX > - gamePanel.getMaxWorldCols() - gamePanel.player.getScreenCoordX() - GamePanel.TILE_SIZE &&
                screenY > - gamePanel.getMaxWorldRows() - gamePanel.player.getScreenCoordY() - GamePanel.TILE_SIZE){
                // Render all current tiles
                gc.drawImage(map.tiles[tileID].image, screenX, screenY);
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
        String filepath = "res/maps/map" + mapIndexString + ".txt";
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
    public void saveMap() {
        // Save the map - feature to be implemented later
    }
}
