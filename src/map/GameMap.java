package map;

import gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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
    private int mapWidth;
    private int mapHeight;
    private int[][] map;


    public GameMap(){
        tiles = new Tile[NUM_TILE_TYPES];
        map = new int[GamePanel.MAX_WORLD_COLS][GamePanel.MAX_WORLD_ROWS];
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
    public void loadMap(String filepath) throws FileNotFoundException {
        // Load the map from a file
        try (Scanner input = new Scanner(new File(filepath))) {
            this.mapWidth = input.nextInt();
            this.mapHeight = input.nextInt();
            this.map = new int[this.mapHeight][this.mapWidth];
            for (int i = 0; i < this.mapHeight; i++) {
                for (int j = 0; j < this.mapWidth; j++) {
                    int tileType = input.nextInt();
                    this.map[i][j] = tileType;
                }
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
