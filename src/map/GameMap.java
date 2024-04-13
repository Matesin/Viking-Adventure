package map;

import gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.Objects;
import java.awt.*;
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
    GamePanel game;
    public int mapWidth;
    public int mapHeigh;
    public int[][] map;


    public GameMap(GamePanel game){
        this.game = game;
        tiles = new Tile[NUM_TILE_TYPES];
        map = new int[game.maxWorldCols][game.maxWorldRows];
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
            this.mapHeigh = input.nextInt();
            this.map = new int[this.mapHeigh][this.mapWidth];
            for (int i = 0; i < this.mapHeigh; i++) {
                for (int j = 0; j < this.mapWidth; j++) {
                    int tileType = input.nextInt();
                    this.map[i][j] = tileType;
                }
            }
        }
    }

    public void draw(GraphicsContext g2) {
        // Draw the map to the screen
        for (int i = 0; i < mapHeigh; i++) {
            for (int j = 0; j < mapWidth; j++) {
                int tileType = map[i][j];
                g2.drawImage(tiles[tileType].image, j * 32, i * 32);
            }
        }
    }
}
