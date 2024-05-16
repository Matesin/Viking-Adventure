package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.gameloop.Camera;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import cz.cvut.fel.pjv.map_object.ActiveMapObject;
import cz.cvut.fel.pjv.map_object.MapObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MapObjectManager {
    @Getter
    Optional<List<MapObject>> mapObjects;
    private final Camera camera;
    private final GamePanel gamePanel;
    private final Player player;
    public MapObjectManager(GamePanel gamePanel){
        // Set the entities for the game
        this.gamePanel = gamePanel;
        MapObjectSetter mapObjectSetter = new MapObjectSetter(gamePanel.getMapIDString(), gamePanel.isLoadSaved());
        this.mapObjects = mapObjectSetter.setEntities();
        this.camera = gamePanel.getCamera();
        this.player = gamePanel.player;
    }
    public void renderMapObjects(GraphicsContext gc){
        if (mapObjects.isPresent()) {
            //use iterator for future implementation of entity removal
            for (MapObject mapObject : mapObjects.get()) {
                mapObject.render(gc, this.gamePanel);
                if(mapObject instanceof ActiveMapObject activeMapObject){
                    if (player.reactToMapObject(activeMapObject)) {
                        log.debug("Player reacted to {}", activeMapObject);
                        activeMapObject.changeState(player.getInventory().getPickedItem());
                    } else if (player.hitbox.intersects(activeMapObject.hitbox)
                            && activeMapObject.isDealingDamage()) {
                        log.debug("Player hit by {}", activeMapObject);
                        activeMapObject.dealDamage(player);
                    }
                }

            }
        }
    }
    public void saveItems(){
        // Save the items
        log.info("Saving Map Objects...");
        Path path = Paths.get("res/save/map_obj.json");
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                log.error("Error deleting the file", e);
            }
        }
        JsonMapObjectHandler jsonMapObjectHandler = new JsonMapObjectHandler();
        if (mapObjects.isPresent()){
            try {
                jsonMapObjectHandler.serializeObjectsToFile(mapObjects.get(), "res/save/map_obj.json");
            } catch (Exception e) {
                log.error("Error saving map objects", e);
            }
            log.info("Map Objects saved successfully");
        } else {
            //create an empty file
            try {
                if (!Files.createFile(path).toFile().createNewFile()){
                    log.error("Error creating empty file");
                }
                log.info("Empty Map objects file created");
            } catch (Exception e) {
                log.error("Error creating empty file", e);
            }
        }
    }
}
