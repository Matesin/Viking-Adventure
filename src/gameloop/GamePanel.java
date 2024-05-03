package gameloop;

import controller.InputHandler;
import entity.Character;
import entity.Player;
import handling.EntityManager;
import handling.ItemManager;
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

import javax.swing.text.html.parser.Entity;
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
    @Getter
    MapManager mapManager;
    @Getter
    @Setter
    private int mapID = 1; //will be inherited from the map selector
    @Getter
    private final String mapIDString = mapID < 9 ? "0" + mapID : String.valueOf(mapID);
    @Getter
    GameMap chosenMap;
    @Getter
    @Setter
    private int chosenMapIndex;
    public final CollisionChecker collisionChecker = new CollisionChecker(this);
    private static Optional<List<Character>> entities;
    @Getter
    private final Camera camera;
    public boolean loadSaved;
    private final EntityManager entityManager;
    private final ItemManager itemManager;
    // CONSTRUCT GAME PANEL
    public GamePanel(Scene scene, StackPane root, boolean loadSaved){
        log.info("GamePanel created");
        log.info("Setting up game panel");
        this.scene = scene;
        this.root = root;
        this.loadSaved = loadSaved;
        setMap(mapID);
        log.info("Initializing player");
        this.player = initPlayer();
        this.camera = new Camera(this);
        this.entityManager = new EntityManager(this);
        this.itemManager = new ItemManager(this);
        initCanvas();
        log.info("GamePanel created. Starting game loop");
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
        log.info("Player initialized at {}, {}", playerStartX, playerStartY);
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
        entityManager.updateEntities();
    }
    // DRAW GRAPHICS
    public void draw(GraphicsContext gc) {
        refreshScreen(gc);
        itemManager.renderItems(gc);
        player.getHitbox().display(gc);
        player.render(gc);
        entityManager.renderEntities(gc);
        mapManager.renderMap(gc);
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
}
