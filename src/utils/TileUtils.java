package utils;

import gameloop.GamePanel;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;
import map.GameMap;
import map.Tile;

import java.io.FileInputStream;
import java.io.IOException;

import static gameloop.GamePanel.TILE_SIZE;

@Slf4j
public class TileUtils {
    GameMap gameMap;

    public TileUtils(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void initTile(String tileType, int tileID){

        try {
            gameMap.getTiles()[tileID] = new Tile();
            Image image = new Image(new FileInputStream("res/map_tiles/" + tileType +".png"), TILE_SIZE, TILE_SIZE, false, false);
            gameMap.getTiles()[tileID].setImage(image);
        } catch (IOException e) {
            log.error("Error loading tile image: {}", e.getMessage());
        }
    }
    public void initTile(String tileType, int tileID, boolean isSolid){
        // Load the tile image
        initTile(tileType, tileID);
        gameMap.getTiles()[tileID].setSolid(isSolid);
    }
    public void initTile(String tileType, int tileID, boolean isSolid, boolean collision){
        // Load the tile image
        initTile(tileType, tileID);
        gameMap.getTiles()[tileID].setSolid(isSolid);
        gameMap.getTiles()[tileID].setCollision(collision);
    }
}
