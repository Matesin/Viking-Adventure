package gameloop;

import controller.InputHandler;
import entity.Character;
import entity.Player;
import item.Item;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import map.GameMap;
import map.MapManager;
import handling.AssetSetter;
import utils.CollisionChecker;

import java.util.List;
import java.util.Optional;

import static gameloop.Constants.Screen.*;
import static gameloop.Constants.Tile.TILE_SIZE;

@Slf4j
public class GamePanel extends Pane {

    public Player player;
    // WORLD SETTINGS
    @Getter
    private int maxWorldRows;
    @Getter
    private int maxWorldCols;
    // PANE INIT
    Canvas canvas;
    GraphicsContext gc;
    @Getter
    InputHandler inputHandler;
    Scene scene;
    GameLoop gameLoop;
    @Getter
    StackPane root;
    MapManager mapManager;

    @Getter
    GameMap chosenMap;
    @Getter
    @Setter
    private int chosenMapIndex;
    public final CollisionChecker collisionChecker = new CollisionChecker(this);
    public final AssetSetter assetSetter = new AssetSetter(this);
    public Item[] items;
    public Optional<List<Character>> entities;
    // CONSTRUCT GAME PANEL
    public GamePanel(Scene scene, StackPane root){
        log.info("GamePanel created");
        this.scene = scene;
        this.root = root;
        //TODO: Add a map selector
        log.info("Setting up game panel");
        setMap(2);
        log.info("Initializing player");
        initPlayer();
        initCanvas();
        log.info("Starting game loop");
        startGameLoop();
    }
    // CANVAS INIT
    private void initCanvas() {
        this.canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.canvas.setFocusTraversable(true);
        this.canvas.setOnKeyPressed(inputHandler);
        this.canvas.setOnKeyReleased(inputHandler);
        this.gc = canvas.getGraphicsContext2D();
        this.root.getChildren().add(canvas);
    }
    // PLAYER INIT
    private void initPlayer(){
        this.inputHandler = new InputHandler();
        this.player = new Player(this, this.inputHandler);
        int playerStartX = mapManager.map.getStartX();
        int playerStartY = mapManager.map.getStartY();
        player.getPlayerImage();
        player.setDefaultValues(playerStartX, playerStartY);
    }
    // GAME LOOP INIT
    public void startGameLoop(){
        this.gameLoop = new GameLoop(this);
        gameLoop.start();
    }
    // SETUP MAP
    public void setMap(int mapIndex){
        this.mapManager = new MapManager(this);
        this.chosenMapIndex = mapIndex;
        mapManager.loadMap(chosenMapIndex);
        int worldHeight = mapManager.getMapHeight();
        int worldWidth = mapManager.getMapWidth();
        this.maxWorldRows = worldHeight * SCREEN_ROWS;
        this.maxWorldCols = worldWidth * SCREEN_COLS;
        this.chosenMap = mapManager.map;
    }

    // REFRESH ENTITY COORDS
    public void update() {
        player.update();
//        updateEntities();
    }
    // DRAW GRAPHICS
    public void draw(GraphicsContext gc) {
        refreshScreen(gc);
        player.getHitbox().display(gc);
        player.render(gc);
        mapManager.renderMap(gc);
//        renderEntities(gc);
        printPlayerStats(gc);
    }
    public Stage getStage(){
        return (Stage) this.root.getScene().getWindow();
    }
    private void refreshScreen(GraphicsContext gc){
        gc.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        gc.setFill(Color.CADETBLUE);
        gc.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }
    private void printPlayerStats(GraphicsContext gc){
        gc.setFill(Color.WHITE);
        Font statsFont = Font.font("Segoe Script", FontWeight.BOLD, 24);
        gc.setFont(statsFont);
        gc.fillText("Player X: " + player.getWorldCoordX(), 15, 30);
        gc.fillText("Player Y: " + player.getWorldCoordY(), 15, 60);
        gc.fillText("Current Sprite: " + player.getCurrentSprite(), 15, 90);
        gc.fillText("Hitbox Coords: " + player.getHitbox().getCoordX() + ", " + player.getHitbox().getCoordY(), 15, 120);
    }
    private void updateEntities(){
        if (entities.isPresent()) {
            for (Character entity : entities.orElseThrow()) {
                entity.update();
            }
        }
    }
    public boolean isOnScreen(int x, int y){
        return x >= player.getWorldCoordX() - SCREEN_MIDDLE_X - TILE_SIZE && x <= player.getWorldCoordX() + SCREEN_MIDDLE_X + TILE_SIZE&&
                y >= player.getWorldCoordY() - SCREEN_MIDDLE_Y - TILE_SIZE && y <= player.getWorldCoordY() + SCREEN_MIDDLE_Y + TILE_SIZE;
    }
    private void renderEntities(GraphicsContext gc){
        if (entities.isPresent()) {
            for (Character entity : entities.orElseThrow()) {
                if (isOnScreen(entity.getWorldCoordX(), entity.getWorldCoordY())) {
                    entity.render(gc);
                }
            }
        }
    }
}
