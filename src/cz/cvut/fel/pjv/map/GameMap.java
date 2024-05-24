package cz.cvut.fel.pjv.map;

import cz.cvut.fel.pjv.entity.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import cz.cvut.fel.pjv.utils.TileUtils;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;

import static cz.cvut.fel.pjv.gameloop.Constants.Screen.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Tile.TILE_SIZE;
/**
 * The GameMap class is responsible for storing the map data and rendering the map to the screen.
 */
@Slf4j
@Getter
public class GameMap implements Serializable {
    /*
    * The GameMap class is responsible for storing the map data and rendering the map to the screen.
    * NUM_TILE_TYPES - array of all tile types available
    * tiles - array of all tiles in the map
    * game - the game panel
     */
    private transient Tile[] tiles;
    private int mapWidth;
    private int mapHeight;
    @Setter
    private int[][] map;
    double startX;
    double startY;
    private final String mapID;
    transient TileUtils utils;

    /**
     * Constructor for the GameMap class.
     * @param mapID The ID of the map.
     */
    public GameMap(String mapID){
        if (mapID == null){
            throw new IllegalArgumentException("Map ID cannot be null");
        }
        this.mapID = mapID;
        this.utils = new TileUtils(this);
        startX = SCREEN_MIDDLE_X;
        startY = SCREEN_MIDDLE_Y;
        getTileImages();

    }
    /**
     * Method to get tile images.
     */
    public void getTileImages(){
        Optional<List<TileProperty>> tileIndexes = utils.loadTileIndexes(this.mapID);
        if (tileIndexes.isPresent()){
            tiles = new Tile[tileIndexes.get().size()];
            int counter = 0;
            for (TileProperty tile : tileIndexes.get()){
                log.debug("Tile: {}, counter: {}", tile.getTileName(), counter);
                utils.initTile(tile.getTileName(), tile.getTileIndex(), tile.isSolid(), tile.isCollision());
                counter++;
            }
        } else {
            tiles = new Tile[0];
            log.error("Error loading tile indexes for map {}", this.mapID);
        }
    }
    /**
     * Method to load map from a file.
     * @param filepath The path of the file.
     * @throws FileNotFoundException If the file is not found.
     */
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
                    startX = Double.parseDouble(values[1]);
                    startY = Double.parseDouble(values[2]);
                }
                default -> log.info((Marker) Level.SEVERE, "Map {} has invalid data: {}", filepath, line);
            }
        }
    }
    /**
     * Method to save the map to a file.
     * @param filepath The path of the file.
     * @param player The player object.
     */
    public void save(String filepath, Player player) {
        // Save the map to a file
        try (PrintWriter writer = new PrintWriter(filepath);
                BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
                // Write the map metadata
                bufferedWriter.write("# Map metadata");
                bufferedWriter.newLine();
                bufferedWriter.write("width: " + this.mapWidth);
                bufferedWriter.newLine();
                bufferedWriter.write("height: " + this.mapHeight);
                bufferedWriter.newLine();
                bufferedWriter.write("start: " + player.getWorldCoordX() / TILE_SIZE + " " + player.getWorldCoordY() / TILE_SIZE);
                bufferedWriter.newLine();
                bufferedWriter.write("# Map data");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
                // Write the map dat
                for (int row = 0; row < this.mapHeight; row++) {
                    for (int col = 0; col < this.mapWidth; col++) {
                        bufferedWriter.write(this.map[col][row] + " ");
                    }
                    bufferedWriter.newLine();
                }
            } catch (IOException e) {
                log.error("Error saving map: {}", e.getMessage());
            }
    }
}
