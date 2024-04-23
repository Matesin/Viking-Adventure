package map;

import gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import utils.TileUtils;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;

import static gameloop.Constants.Screen.*;
import static gameloop.Constants.Tile.*;

@Slf4j
public class GameMap implements Serializable {
    /*
    * The GameMap class is responsible for storing the map data and rendering the map to the screen.
    * NUM_TILE_TYPES - array of all tile types available
    * tiles - array of all tiles in the map
    * game - the game panel
     */
    private static final int NUM_TILE_TYPES = 3; //*SUBJECT TO CHANGE*
    @Getter
    private Tile[] tiles;
    @Getter
    private int mapWidth;
    @Getter
    private int mapHeight;
    @Setter
    @Getter
    private int[][] map;
    @Getter
    int startX;
    @Getter
    int startY;
    TileUtils utils;

    public GameMap(){
        this.utils = new TileUtils(this);
        tiles = new Tile[NUM_TILE_TYPES];
        startX = SCREEN_MIDDLE_X;
        startY = SCREEN_MIDDLE_Y;
        getTileImages();
    }
    public void getTileImages(){
        // Load the tile images
        utils.initTile("path", 0);
        utils.initTile("grass", 1, true, true);
        utils.initTile("water", 2);
    }


    public void loadMapFromFile(String filepath) throws FileNotFoundException {
        // Load the map from a file
        try (Scanner scanner = new Scanner(new File(filepath))) {
            readMetadata(scanner, filepath);
            log.info("Map {} loaded with dimensions {}x{}", filepath, this.mapWidth, this.mapHeight);
            // Initialize the map array
            this.map = new int[this.mapWidth][this.mapHeight];
            // Read the map data
            readMapData(scanner, filepath);
        }
    }
    private void readMapData(Scanner scanner, String filepath) {
        // MAP DATA FORMAT - space separated integers
        // Read the map data
        int row = 0;
        int col = 0;
        while (row < this.mapHeight && scanner.hasNextLine()) { //y
            String line = scanner.nextLine();
            while(col < this.mapWidth) { //x
                String[] numbers = line.split(" ");
                int num = Integer.parseInt(numbers[col]);
                this.map[col][row] = num;
                col++;
            }
            if (col != this.mapWidth){
                log.error("Map {} missing data on row {} expected {} got {}",
                        filepath,
                        row,
                        this.mapWidth,
                        col);
                // Fill in the rest of the row with 0's
                for (int i = col; i < this.mapWidth; i++){
                    this.map[row][i] = 0;
                }
            }
            col = 0;
            row++;
        }
    }
    private void readMetadata(Scanner scanner, String filepath) {
        // MAP METADATA FORMAT - hashed lines contain comment, then map width and height
        String line = null;
        String[] values;
        // Read the map metadata
        while (!(line = scanner.nextLine()).isEmpty()) {
            if (line.startsWith("#")) continue;
            values = line.split(" ");
            for (String value : values) {
                log.debug("Value: {}", value);
            }
            switch (values[0]) {
                case "width:" -> this.mapWidth = Integer.parseInt(values[1]);
                case "height:" -> this.mapHeight = Integer.parseInt(values[1]);
                case "start:" -> {
                    startX = Integer.parseInt(values[1]) * TILE_SIZE;
                    startY = Integer.parseInt(values[2]) * TILE_SIZE;
                }
                default -> log.info((Marker) Level.SEVERE, "Map {} has invalid data: {}", filepath, line);
            }
        }
    }
}
