package gameloop;

import controller.InputHandler;
import entity.Player;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import map.GameMap;
import map.MapManager;
import utils.CollisionChecker;

@Slf4j
public class GamePanel extends Pane {
    static final int INITIAL_TILE_SIZE = 32;
    public static final int SCALE = 2;
    public static final int TILE_SIZE = INITIAL_TILE_SIZE * SCALE;
    public static final int SCREEN_COLS = 16;
    public static final int SCREEN_ROWS = 12;
    public static final int SCREEN_WIDTH = TILE_SIZE * SCREEN_COLS;
    public static final int SCREEN_HEIGHT = TILE_SIZE * SCREEN_ROWS;
    public static final int SCREEN_MIDDLE_X = SCREEN_WIDTH / 2;
    public static final int SCREEN_MIDDLE_Y = SCREEN_HEIGHT / 2;
    public Player player;

    @Getter
    @Setter
    private int fps = 60;
    // WORLD SETTINGS
    @Getter
    private int maxWorldRows;
    @Getter
    private int maxWorldCols;
    // PANE INIT
    Canvas canvas;
    GraphicsContext gc;
    InputHandler inputHandler;
    Scene scene;
    GameLoop gameLoop;
    StackPane root;
    MapManager mapManager;
    @Getter
    GameMap chosenMap;
    @Getter
    @Setter
    private int chosenMapIndex;
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public GamePanel(Scene scene, StackPane root){
        log.info("GamePanel created");
        this.scene = scene;
        this.root = root;
        //TODO: Add a map selector
        log.info("Setting up game panel");
        setMap(1);
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
    // GAME LOOP
    public void update() {
        player.update();
    }

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        gc.setFill(Color.CADETBLUE);
        gc.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        player.render(gc);
        mapManager.renderMap(gc);
        gc.setFill(Color.WHITE);
        Font statsFont = Font.font("Segoe Script", FontWeight.BOLD, 24);
        gc.setFont(statsFont);
        gc.fillText("Player X: " + player.getWorldCoordX(), 15, 30);
        gc.fillText("Player Y: " + player.getWorldCoordY(), 15, 60);
    }
}
