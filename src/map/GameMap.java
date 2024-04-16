package map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Scanner;

public class GameMap implements Serializable {
    /*
    * The GameMap class is responsible for storing the map data and rendering the map to the screen.
    * NUM_TILE_TYPES - array of all tile types available
    * tiles - array of all tiles in the map
    * game - the game panel
     */
    private static final int NUM_TILE_TYPES = 3; //*SUBJECT TO CHANGE*
    Tile[] tiles;
    @Getter
    private int mapWidth;
    @Getter
    private int mapHeight;
    @Setter
    @Getter
    private int[][] map;


    public GameMap(){
        tiles = new Tile[NUM_TILE_TYPES];
        getTileImages();
    }
    public void getTileImages(){
        // Load the tile images
        try {
            tiles[0] = new Tile(); // water
            tiles[0].image = new Image(new FileInputStream("/map_tiles/water.png"));
            tiles[1] = new Tile(); // path
            tiles[1].image = new Image(new FileInputStream("/map_tiles/path.png"));
            tiles[2] = new Tile(); // grass
            tiles[2].image = new Image(new FileInputStream("/map_tiles/grass.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMapFromFile(String filepath) throws FileNotFoundException {
        // Load the map from a file
        try (Scanner input = new Scanner(new File(filepath))) {
            // MAP FORMAT - 2 lines (width, height) followed by the map data
            this.mapWidth = input.nextInt();
            this.mapHeight = input.nextInt();
            String line;
            int row = 0;
            int col = 0;
            this.map = new int[this.mapHeight][this.mapWidth];
            // Read the map data
            while (row < this.mapHeight && input.hasNextLine()) {
                line = input.nextLine();
                while(col < this.mapWidth) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    this.map[row][col] = num;
                    col++;
                }
                if (col != this.mapWidth){
                    System.out.printf("Map %s missing data on row %d expected %d got %d%n",
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
    }

    public void draw(GraphicsContext g2) {
        // Draw the map to the screen
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                int tileType = map[i][j];
                g2.drawImage(tiles[tileType].image, j * 32, i * 32);
            }
        }
    }
}
