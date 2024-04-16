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
import map.MapManager;

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
    public int maxWorldRows;
    public int maxWorldCols;
    public int worldWidth;
    public int worldHeight;
    // PANE INIT
    Canvas canvas;
    GraphicsContext gc;
    InputHandler inputHandler;
    Scene scene;
    GameLoop gameLoop;
    StackPane root;
    MapManager mapManager;
    @Getter
    @Setter
    private int chosenMapIndex;
    public GamePanel(Scene scene, StackPane root){
        System.out.println("GamePanel created");
        this.scene = scene;
        this.root = root;
        //TODO: Add a map selector
        setMap(0);
        initPlayer();
        initCanvas();
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
        player.getPlayerImage();
        player.setDefaultValues(SCREEN_MIDDLE_X, SCREEN_MIDDLE_Y);
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
        this.maxWorldRows = mapManager.getMapHeight();
        this.maxWorldCols = mapManager.getMapWidth();
        worldWidth = TILE_SIZE * maxWorldCols;
        worldHeight = TILE_SIZE * maxWorldRows;
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
        gc.fillText("Player X: " + player.worldCoordX, 15, 30);
        gc.fillText("Player Y: " + player.worldCoordY, 15, 60);
    }
}