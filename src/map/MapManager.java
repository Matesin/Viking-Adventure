package map;

import com.sun.media.jfxmedia.events.PlayerEvent;
import entity.Player;
import gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;

import java.io.FileNotFoundException;

public class MapManager {
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
        // Render current map based off player position
        int playerX = gamePanel.player.worldCoordX;
        int playerY = gamePanel.player.worldCoordY;
        int screenX = gamePanel.player.screenCoordX;
        int screenY = gamePanel.player.screenCoordY;
        int offsetX = screenX - playerX;
        int offsetY = screenY - playerY;
        int startCol = Math.max(playerX / GamePanel.TILE_SIZE, 0);
        int startRow = Math.max(playerY / GamePanel.TILE_SIZE, 0);
        int endCol = Math.min(startCol + GamePanel.SCREEN_COLS, mapWidth);
        int endRow = Math.min(startRow + GamePanel.SCREEN_ROWS, mapHeight);
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                int tile = this.map.getMap()[row][col];
                gc.drawImage(map.tiles[tile].image, (col - startCol) * GamePanel.TILE_SIZE + offsetX, (row - startRow) * GamePanel.TILE_SIZE + offsetY);
            }
        }
    }
    public void updateMap() {
        // Update the map
    }
    public void loadMap(int mapIndex) {
        // Load the map
        this.map = new GameMap();
        try {
            this.map.loadMapFromFile("res/maps/map" + mapIndex + ".txt");
            this.mapWidth = this.map.getMapWidth();
            this.mapHeight = this.map.getMapHeight();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void saveMap() {
        // Save the map - feature to be implemented later
    }
}
