package gameloop;

import controller.InputHandler;
import entity.Character;
import entity.Player;
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
import utils.CollisionChecker;

import java.util.List;
import java.util.Optional;

import static gameloop.Constants.Screen.*;
import static gameloop.Constants.Tile.TILE_SIZE;

@Slf4j
public class GamePanel extends Pane {
    // WORLD SETTINGS
    @Getter
    private int maxWorldRows;
    @Getter
    private int maxWorldCols;
    // PANE INIT
    Canvas canvas;
    GraphicsContext gc;
    @Getter
    InputHandler inputHandler = new InputHandler();
    public final Player player;
    Scene scene;
    GameLoop gameLoop;
    @Getter
    StackPane root;
    MapManager mapManager;
    @Getter
    @Setter
    private int mapID = 2; //will be inherited from the map selector
    @Getter
    private final String mapIDString = mapID < 9 ? "0" + mapID : String.valueOf(mapID);
    @Getter
    GameMap chosenMap;
    @Getter
    @Setter
    private int chosenMapIndex;
    public final CollisionChecker collisionChecker = new CollisionChecker(this);
    private static Optional<List<Character>> entities;
    private final Camera camera;
    public boolean loadSaved;
    // CONSTRUCT GAME PANEL
    public GamePanel(Scene scene, StackPane root, boolean loadSaved){
        log.info("GamePanel created");
        this.scene = scene;
        this.root = root;
        this.loadSaved = loadSaved;
        log.info("Setting up game panel");
        setMap(mapID);
        log.info("Initializing player");
        this.player = initPlayer();
        this.camera = new Camera(this);
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
    private Player initPlayer(){
        int playerStartX = mapManager.getMap().getStartX();
        int playerStartY = mapManager.getMap().getStartY();
        return new Player(playerStartX, playerStartY, this, inputHandler);
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
        mapManager.loadMap();
        int worldHeight = mapManager.getMapHeight();
        int worldWidth = mapManager.getMapWidth();
        this.maxWorldRows = worldHeight * SCREEN_ROWS;
        this.maxWorldCols = worldWidth * SCREEN_COLS;
        this.chosenMap = mapManager.getMap();
    }
    // REFRESH ENTITY COORDS
    public void update() {
        this.player.update();
        camera.update();
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
        gc.setFill(Color.BLACK);
        Font statsFont = Font.font("Segoe Script", FontWeight.BOLD, 24);
        gc.setFont(statsFont);
        gc.fillText("Player Coords: " + player.getWorldCoordX() + ", " + player.getWorldCoordY(), 15, 30);
        gc.fillText("Hitbox Coords: " + player.getHitbox().getCoordX() + ", " + player.getHitbox().getCoordY(), 15, 60);
        gc.fillText("Camera Coords: " + camera.getCameraX() + ", " + camera.getCameraY(), 15, 90);
        gc.fillText("Max Camera X: " + (mapManager.getMapWidth() * TILE_SIZE - 2 * SCREEN_MIDDLE_X), 15, 120);
    }
    private void updateEntities(){
        if (entities.isPresent()) {
            for (Character entity : entities.orElseThrow()) {
                entity.update();
            }
        }
    }
    public boolean isOnScreen(int x, int y){
        boolean isOnScreen = false;
        int playerX = player.getWorldCoordX();
        int playerY = player.getWorldCoordY();
        int mapWidth = mapManager.getMapWidth();
        int mapHeight = mapManager.getMapHeight();
        if ((playerX - SCREEN_MIDDLE_X  - TILE_SIZE < 0 && x <= SCREEN_WIDTH) ||
            (playerY - SCREEN_MIDDLE_Y - TILE_SIZE < 0 && y <= SCREEN_WIDTH) ||
            (playerX + SCREEN_MIDDLE_X > mapWidth && x > mapWidth - SCREEN_WIDTH) ||
            (playerY + SCREEN_MIDDLE_Y > mapHeight && y >= mapHeight - SCREEN_WIDTH)) {
            isOnScreen = true;
        }
        else {
            isOnScreen = x >= playerX - SCREEN_MIDDLE_X - TILE_SIZE && x <= playerX + SCREEN_MIDDLE_X + TILE_SIZE &&
                    y >= playerY - SCREEN_MIDDLE_Y - TILE_SIZE && y <= playerY + SCREEN_MIDDLE_Y + TILE_SIZE;
        }
        return isOnScreen;
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
