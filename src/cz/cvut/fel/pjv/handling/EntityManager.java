package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.entity.Character;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.entity.TaskVillager;
import cz.cvut.fel.pjv.gameloop.Camera;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static cz.cvut.fel.pjv.gameloop.Constants.Screen.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Tile.TILE_SIZE;
/**
 * Class responsible for managing entities in the game.
 * This class provides methods for rendering, updating, and saving entities.
 */
@Slf4j
public class EntityManager {
    @Getter
    Optional<List<Character>> entities;
    private final Camera camera;
    private final GamePanel gamePanel;
    private Player player;

    /**
     * Constructor for EntityManager with specified game panel.
     *
     * @param gamePanel the game panel
     */
    public EntityManager(GamePanel gamePanel){
        // Set the entities for the game
        this.gamePanel = gamePanel;
        EntitySetter entitySetter = new EntitySetter(gamePanel.getMapIDString(), gamePanel.isLoadSaved());
        this.entities = entitySetter.setEntities();
        this.camera = gamePanel.getCamera();
        this.player = gamePanel.player;
    }
    /**
     * Checks if an entity is on the screen.
     *
     * @param entity the entity to check
     * @return true if the entity is on the screen, false otherwise
     */
    public boolean isOnScreen(Character entity){
        double cameraX = camera.getCameraX();
        double cameraY = camera.getCameraY();
        double entityX = entity.getWorldCoordX();
        double entityY = entity.getWorldCoordY();
        return entityX >= cameraX - TILE_SIZE && entityX <= cameraX + SCREEN_WIDTH + TILE_SIZE &&
                entityY >= cameraY - TILE_SIZE && entityY <= cameraY + SCREEN_HEIGHT + TILE_SIZE;
    }
    /**
     * Renders entities on the screen.
     *
     * @param gc the graphics context
     */
    public void renderEntities(GraphicsContext gc){
        if (entities.isPresent()) {
            //use iterator for future implementation of entity removal
            for (Character entity : entities.get()) {
                if (isOnScreen(entity)) {
                    entity.render(gc, this.gamePanel);
                    if (player.reactionRange.intersects(entity.hitbox) &&
                        player.react() &&
                        entity instanceof TaskVillager taskVillager){
                         taskVillager.respondToPlayer(this.gamePanel);
                         log.debug("Player reacted to {}", taskVillager);
                        }
                }
            }
        }
    }
    /**
     * Updates the states of entities.
     */
    public void updateEntities(){
        if (entities.isPresent()) {
            for (Character entity : entities.orElseThrow()) {
                entity.update();
            }
        }
    }
    /**
     * Saves the entities to a file.
     */
    public void saveEntities(){
        // Save the items
        log.info("Saving entities...");
        Path path = Paths.get("res", "save", "ent.json");
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                log.error("Error deleting the file", e);
            }
        }
        JsonEntityHandler jsonEntityHandler = new JsonEntityHandler();
        if (entities.isPresent()){
            try {
                jsonEntityHandler.serializeCharactersToFile(entities.get(), "res/save/ent.json");
            } catch (Exception e) {
                log.error("Error saving entities", e);
            }
            log.info("Items saved successfully");
        } else {
            //create an empty file
            try {
                if (!Files.createFile(path).toFile().createNewFile()){
                    log.error("Error creating empty file");
                }
                log.info("Empty entities file created");
            } catch (Exception e) {
                log.error("Error creating empty file", e);
            }
        }
    }
}
