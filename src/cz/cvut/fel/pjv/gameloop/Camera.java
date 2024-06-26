package cz.cvut.fel.pjv.gameloop;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.map.GameMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
/**
 * Class representing the camera in the game.
 * This class is responsible for following the player around the game map.
 */
@Slf4j
public class Camera {
    private final Player player;
    private final GameMap gameMap;
    @Getter
    private double cameraX;
    @Getter
    private double cameraY;

    /**
     * Constructor for Camera with specified game panel.
     *
     * @param gamePanel the game panel
     */

    public Camera(GamePanel gamePanel) {
        this.player = gamePanel.player;
        this.gameMap = gamePanel.getChosenMap();
        update();
    }

    /**
     * Updates the camera's position based on the player's position.
     */

    public void update() {
        //place the camera in the center of the screen initially, if the player is not at the edge of the map
        cameraX = player.getWorldCoordX() - Constants.Screen.SCREEN_MIDDLE_X;
        cameraY = player.getWorldCoordY() - Constants.Screen.SCREEN_MIDDLE_Y;
        //if the player is at the edge of the map, adjust the camera accordingly
        if (cameraX <= 0) {
            player.setScreenCoordX(player.getWorldCoordX());
            cameraX = 0;
        }
        if (cameraY <= 0) {
            player.setScreenCoordY(player.getWorldCoordY());
            cameraY = 0;
        }
        if (cameraX >= gameMap.getMapWidth() * Constants.Tile.TILE_SIZE - 2 * Constants.Screen.SCREEN_MIDDLE_X) {
            cameraX = player.getWorldCoordX() - gameMap.getMapWidth() * Constants.Tile.TILE_SIZE + 2 * Constants.Screen.SCREEN_MIDDLE_X;
            player.setScreenCoordX(cameraX);
            cameraX = gameMap.getMapWidth() * Constants.Tile.TILE_SIZE - 2 * Constants.Screen.SCREEN_MIDDLE_X;
        }
        if (cameraY >= gameMap.getMapHeight() * Constants.Tile.TILE_SIZE - 2 * Constants.Screen.SCREEN_MIDDLE_Y) {
            cameraY = player.getWorldCoordY() - gameMap.getMapHeight() * Constants.Tile.TILE_SIZE + 2 * Constants.Screen.SCREEN_MIDDLE_Y;
            player.setScreenCoordY(cameraY);
            cameraY = gameMap.getMapHeight() * Constants.Tile.TILE_SIZE - 2 * Constants.Screen.SCREEN_MIDDLE_Y;
        }
    }
}
