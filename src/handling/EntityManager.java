package handling;

import entity.Character;
import entity.Player;
import gameloop.Camera;
import gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static gameloop.Constants.Screen.*;
import static gameloop.Constants.Screen.SCREEN_MIDDLE_Y;
import static gameloop.Constants.Tile.TILE_SIZE;

@Slf4j
public class EntityManager {
    Optional<List<Character>> entities;
    private final Camera camera;
    private final GamePanel gamePanel;
    public EntityManager(GamePanel gamePanel){
        // Set the entities for the game
        this.gamePanel = gamePanel;
        EntitySetter entitySetter = new EntitySetter(gamePanel.getMapIDString(), gamePanel.loadSaved);
        this.entities = entitySetter.setEntities();
        this.camera = gamePanel.getCamera();
    }

    public boolean isOnScreen(Character entity){
        int cameraX = camera.getCameraX();
        int cameraY = camera.getCameraY();
        int entityX = entity.getWorldCoordX();
        int entityY = entity.getWorldCoordY();
        return entityX >= cameraX - TILE_SIZE && entityX <= cameraX + SCREEN_WIDTH + TILE_SIZE &&
                entityY >= cameraY - TILE_SIZE && entityY <= cameraY + SCREEN_HEIGHT + TILE_SIZE;
    }
    public void renderEntities(GraphicsContext gc){
        if (entities.isPresent()) {
            for (Character entity : entities.orElseThrow()) {
                if (isOnScreen(entity)) {
                    log.info("Rendering entity {}", entity.getClass().getSimpleName());
                    entity.render(gc, gamePanel);
                }
            }
        }
    }
    public void updateEntities(){
        if (entities.isPresent()) {
            for (Character entity : entities.orElseThrow()) {
                entity.update();
            }
        }
    }
}
