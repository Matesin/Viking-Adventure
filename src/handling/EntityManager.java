package handling;

import entity.Character;
import entity.Player;
import gameloop.Camera;
import gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.html.parser.Entity;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static gameloop.Constants.Screen.*;
import static gameloop.Constants.Screen.SCREEN_MIDDLE_Y;
import static gameloop.Constants.Tile.TILE_SIZE;

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
