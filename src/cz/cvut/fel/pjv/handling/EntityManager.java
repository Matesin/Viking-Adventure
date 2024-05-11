package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.entity.Character;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.gameloop.Camera;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static cz.cvut.fel.pjv.gameloop.Constants.Screen.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Tile.TILE_SIZE;

@Slf4j
public class EntityManager {
    @Getter
    Optional<List<Character>> entities;
    private final Camera camera;
    private final GamePanel gamePanel;
    private final Player player;
    public EntityManager(GamePanel gamePanel){
        // Set the entities for the game
        this.gamePanel = gamePanel;
        EntitySetter entitySetter = new EntitySetter(gamePanel.getMapIDString(), gamePanel.loadSaved);
        this.entities = entitySetter.setEntities();
        this.camera = gamePanel.getCamera();
        this.player = gamePanel.player;
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
            //use iterator for future implementation of entity removal
            for (Character entity : entities.get()) {
                if (isOnScreen(entity)) {
                    entity.render(gc, this.gamePanel);
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
