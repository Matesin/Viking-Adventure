package gameloop;

import controller.InputHandler;
import entity.Player;
import item.Item;
import javafx.fxml.FXML;
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

import static gameloop.Constants.Screen.*;
import static gameloop.Constants.Tile.*;
import static gameloop.GamePanel.*;

@Slf4j
public class GamePanel extends Pane {

    public Player player;
    // WORLD SETTINGS
    @Getter
    private int maxWorldRows;
    @Getter
    private int maxWorldCols;
    // PANE INIT
    @FXML
    Canvas canvas;
    @FXML
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
    }
    // DRAW GRAPHICS
    public void draw(GraphicsContext gc) {
        refreshScreen(gc);
        player.hitbox.display(gc);
        player.render(gc);
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
        gc.setFill(Color.WHITE);
        Font statsFont = Font.font("Segoe Script", FontWeight.BOLD, 24);
        gc.setFont(statsFont);
        gc.fillText("Player X: " + player.getWorldCoordX(), 15, 30);
        gc.fillText("Player Y: " + player.getWorldCoordY(), 15, 60);
        gc.fillText("Current Sprite: " + player.getCurrentSprite(), 15, 90);
        gc.fillText("Hitbox Coords: " + player.hitbox.getCoordX() + ", " + player.hitbox.getCoordY(), 15, 120);
    }
}
