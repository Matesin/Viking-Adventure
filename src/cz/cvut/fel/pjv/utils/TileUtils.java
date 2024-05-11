package cz.cvut.fel.pjv.utils;

import cz.cvut.fel.pjv.map.GameMap;
import cz.cvut.fel.pjv.map.Tile;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;
import cz.cvut.fel.pjv.map.TileProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static cz.cvut.fel.pjv.gameloop.Constants.Tile.*;

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
            gameMap.getTiles()[tileID].setType(tileType);
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
    public Optional<List<TileProperty>> loadTileIndexes(String mapID){
        String filepath = "res/maps/map" + mapID + "/tiles_indexed.txt";
        List<TileProperty> tilesList = new ArrayList<>();
        try{
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                TileProperty tile = switch (parts.length) {
                    case 2 -> new TileProperty(parts[0], Integer.parseInt(parts[1]));
                    case 3 -> new TileProperty(parts[0], Integer.parseInt(parts[1]), Boolean.parseBoolean(parts[2]));
                    case 4 ->
                            new TileProperty(parts[0], Integer.parseInt(parts[1]), Boolean.parseBoolean(parts[2]), Boolean.parseBoolean(parts[3]));
                    default -> {
                        log.error("Error loading tile indexes: Invalid number of arguments");
                        yield null;
                    }
                };
                tilesList.add(tile);
            }
        } catch (FileNotFoundException e){
            log.error("Error loading tile indexes: {}", e.getMessage());
        }
        return Optional.of(tilesList);
    }

}

