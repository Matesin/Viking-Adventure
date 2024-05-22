package cz.cvut.fel.pjv.gameloop;

import cz.cvut.fel.pjv.controller.InputHandler;
import cz.cvut.fel.pjv.entity.Character;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.handling.EntityManager;
import cz.cvut.fel.pjv.handling.InventoryManager;
import cz.cvut.fel.pjv.handling.ItemManager;
import cz.cvut.fel.pjv.handling.MapObjectManager;
import cz.cvut.fel.pjv.inventory.InGameInventoryBar;
import cz.cvut.fel.pjv.map.GameMap;
import cz.cvut.fel.pjv.map.MapManager;
import cz.cvut.fel.pjv.utils.CollisionChecker;
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

import java.util.List;
import java.util.Optional;
/**
 * Class representing the game panel.
 * This class is responsible for setting up the game panel, initializing the player, starting the game loop, setting up the map, refreshing entity coordinates, and rendering graphics.
 */
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
    Pane root;
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
    @Getter
    private final boolean loadSaved;
    @Getter
    private final EntityManager entityManager;
    @Getter
    private final ItemManager itemManager;
    @Getter
    private final MapObjectManager mapObjectManager;
    @Getter
    private final InventoryManager inventoryManager;
    @Getter
    private final InGameInventoryBar inGameInventoryBar;
    boolean craftingOpened = false;
    /**
     * Constructor for GamePanel with specified scene, root and loadSaved.
     *
     * @param scene the scene
     * @param root the root
     * @param loadSaved the loadSaved
     */
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
        this.mapObjectManager = new MapObjectManager(this);
        this.inventoryManager = new InventoryManager(this);
        entities = entityManager.getEntities();
        initCanvas();
        inGameInventoryBar = new InGameInventoryBar(this.player.getInventory(), this.root);
        log.info("GamePanel created. Starting game loop");
        startGameLoop();
    }
    /**
     * Initializes the canvas.
     */
    private void initCanvas() {
        this.canvas = new Canvas(Constants.Screen.SCREEN_WIDTH, Constants.Screen.SCREEN_HEIGHT);
        this.canvas.setFocusTraversable(true);
        this.canvas.setOnKeyPressed(inputHandler);
        this.canvas.setOnKeyReleased(inputHandler);
        this.gc = canvas.getGraphicsContext2D();
        this.root.getChildren().add(canvas);
    }
    /**
     * Initializes the player.
     *
     * @return the player
     */
    private Player initPlayer(){
        int playerStartX = mapManager.getMap().getStartX();
        int playerStartY = mapManager.getMap().getStartY();
        log.info("Player initialized at {}, {}", playerStartX, playerStartY);
        return new Player(playerStartX, playerStartY, this, inputHandler);
    }

    /**
     * Initializes and starts the game loop.
     */

    public void startGameLoop(){
        this.gameLoop = new GameLoop(this);
        gameLoop.start();
    }

    /**
     * Sets up the map with the specified map index.
     *
     * @param mapIndex the map index
     */

    public void setMap(int mapIndex){
        this.mapManager = new MapManager(this);
        this.chosenMapIndex = mapIndex;
        this.mapManager.loadMap();
        int worldHeight = mapManager.getMapHeight();
        int worldWidth = mapManager.getMapWidth();
        this.maxWorldRows = worldHeight * Constants.Screen.SCREEN_ROWS;
        this.maxWorldCols = worldWidth * Constants.Screen.SCREEN_COLS;
        this.chosenMap = mapManager.getMap();
    }
    /**
     * Updates coords and states of entities.
     */
    public void update() {
        this.player.update();
        this.camera.update();
        this.entityManager.updateEntities();
    }
    /**
     * Renders the game panel.
     *
     * @param gc the graphics context
     */
    public void render(GraphicsContext gc) {
        refreshScreen(gc);
        this.mapManager.renderMap(gc);
        this.player.hitbox.display(gc);
        this.player.reactionRange.display(gc);
        this.itemManager.renderItems(gc);
        this.entityManager.renderEntities(gc);
        this.mapObjectManager.renderMapObjects(gc);
        this.player.render(gc);
    }
    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public Stage getStage(){
        return (Stage) this.root.getScene().getWindow();
    }
    /**
     * Refreshes the screen.
     *
     * @param gc the graphics context
     */
    private void refreshScreen(GraphicsContext gc){
        gc.clearRect(0, 0, Constants.Screen.SCREEN_WIDTH, Constants.Screen.SCREEN_HEIGHT);
        gc.setFill(Color.CADETBLUE);
        gc.fillRect(0, 0, Constants.Screen.SCREEN_WIDTH, Constants.Screen.SCREEN_HEIGHT);
    }
    /**
     * Saves the game.
     */
    public void saveGame(){
        // Save the game
        this.itemManager.saveItems();
        this.entityManager.saveEntities();
        this.mapObjectManager.saveItems();
        this.mapManager.saveMap();
        this.inventoryManager.saveInventory();
    }
}
