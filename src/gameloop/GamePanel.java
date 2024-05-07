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
    private Optional<List<Character>> entities;
    @Getter
    private final Camera camera;
    public boolean loadSaved;
    @Getter
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
        entities = entityManager.getEntities();
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
        this.mapManager.loadMap();
        int worldHeight = mapManager.getMapHeight();
        int worldWidth = mapManager.getMapWidth();
        this.maxWorldRows = worldHeight * SCREEN_ROWS;
        this.maxWorldCols = worldWidth * SCREEN_COLS;
        this.chosenMap = mapManager.getMap();
    }
    // REFRESH ENTITY COORDS
    public void update() {
        this.player.update();
        this.camera.update();
        this.entityManager.updateEntities();
    }
    // DRAW GRAPHICS
    public void render(GraphicsContext gc) {
        refreshScreen(gc);
        this.mapManager.renderMap(gc);
        this.player.hitbox.display(gc);
        this.player.render(gc);
        this.itemManager.renderItems(gc);
        this.entityManager.renderEntities(gc);
//        printPlayerStats(gc);
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
        gc.fillText("Hitbox Coords: " + player.hitbox.getCoordX() + ", " + player.hitbox.getCoordY(), 15, 60);
        gc.fillText("Camera Coords: " + camera.getCameraX() + ", " + camera.getCameraY(), 15, 90);
        if(entities.isPresent()){
            for (int i = 0; i < entities.get().size(); i++) {
                gc.fillText("Entity " + (i + 1) +
                            " Coords: " + entities.get().get(i).getWorldCoordX() + ", " +
                            entities.get().get(i).getWorldCoordY() + " Hitbox coords: " +
                            entities.get().get(i).hitbox.getCoordX() + ", " +
                            entities.get().get(i).hitbox.getCoordY(), 15, 120 + 30 * i);
            }

        }
    }
}
