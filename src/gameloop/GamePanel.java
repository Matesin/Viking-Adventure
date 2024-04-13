package gameloop;

import controller.InputHandler;
import entity.Player;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


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
    Player player;
    public static int fps = 60;
    // WORLD SETTINGS
    public final int maxWorldRows = 100;
    public final int maxWorldCols = 100;
    public final int worldWidth = TILE_SIZE * maxWorldCols;
    public final int worldHeight = TILE_SIZE * maxWorldRows;
    // PANE INIT
    Canvas canvas;
    GraphicsContext gc;
    InputHandler inputHandler;
    Scene scene;
    GameLoop gameLoop;
    StackPane root;
    public GamePanel(Scene scene, StackPane root){
        System.out.println("GamePanel created");
        this.scene = scene;
        this.root = root;
        initPlayer();
        initCanvas();
        startGameLoop();
    }
    // CANVAS INIT
    private void initCanvas() {
        this.canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.canvas.setFocusTraversable(true);
        this.root.getChildren().add(canvas);
        this.canvas.setOnKeyPressed(inputHandler);
        this.canvas.setOnKeyReleased(inputHandler);
        this.gc = canvas.getGraphicsContext2D();
    }
    // PLAYER INIT
    private void initPlayer(){
        this.inputHandler = new InputHandler();
        this.player = new Player(this, this.inputHandler);
        player.getPlayerImage();
    }
    // GAME LOOP INIT
    public void startGameLoop(){
        update();
        draw(this.gc);
        this.gameLoop = new GameLoop(this);
        System.out.println("GamePanel startGameLoop");
        gameLoop.start();
    }
    // GAME LOOP
    public void update() {
        player.update();
        System.out.println("GamePanel update");
    }
    public void draw(GraphicsContext gc) {
        System.out.println("GamePanel draw");
        player.render(this.gc);
        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }
}
