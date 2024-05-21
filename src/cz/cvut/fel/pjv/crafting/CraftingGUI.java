package cz.cvut.fel.pjv.crafting;

import cz.cvut.fel.pjv.gameloop.GamePanel;
import cz.cvut.fel.pjv.inventory.Inventory;
import cz.cvut.fel.pjv.inventory.ItemSlot;
import cz.cvut.fel.pjv.item.Bucket;
import cz.cvut.fel.pjv.item.Item;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static cz.cvut.fel.pjv.gameloop.Constants.Crafting.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Screen.SCREEN_HEIGHT;
import static cz.cvut.fel.pjv.gameloop.Constants.Screen.SCREEN_WIDTH;
@Slf4j
public class CraftingGUI extends Pane {
    private int capacity;
    private final Pane root = new Pane();
    private final GamePanel gamePanel;
    private Scene previousScene;
    private Inventory inventory;
    private List<Pair<Item, Runnable>> itemSlots;
    private Pair<Item, Runnable> resultPair;
    private List<Item> craftableItems;
    private Pane resultSlot = new Pane();
    private Stage mainStage;
    private GridPane craftingGrid;
    private ItemSlot resultItemSlot;


    public CraftingGUI(Stage mainStage, GamePanel gamePanel) {
        /*
         * This class is responsible for creating the crafting GUI and storing its values.
         * */
        this.gamePanel = gamePanel;
        this.inventory = gamePanel.player.getInventory();
        this.capacity = inventory.getCapacity();
        this.mainStage = mainStage;
        if (mainStage != null) {
            this.previousScene = mainStage.getScene();
            Scene inventoryScene = new Scene(createContent(), SCREEN_WIDTH, SCREEN_HEIGHT);
            inventoryScene.setOnKeyPressed(event -> {
                if(event.getCode().toString().equals("ESCAPE") || event.getCode().toString().equals("C")){
                    setPreviousScene();
                }
            });
            mainStage.setScene(inventoryScene);
        }
    }

    private void setBackground() {
        WritableImage snapshot = new WritableImage(SCREEN_WIDTH, SCREEN_HEIGHT);
        previousScene.getRoot().snapshot(null, snapshot);
        ImageView imageView = new ImageView(snapshot);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        imageView.setEffect(new GaussianBlur());
        imageView.setEffect(colorAdjust);
        Image blurredImage = imageView.snapshot(null, null);
        BackgroundImage background = new BackgroundImage(blurredImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.root.setBackground(new Background(background));
}
    private void initCraftingSlots(){
        this.itemSlots = new ArrayList<>();
        for (Item item : craftableItems){
            itemSlots.add(new Pair<>(item, () -> {
                log.debug("clicked on item {}", item.getName());
                int neededItems = item.getCraftingMaterials().size();
                int foundItems = 0;
                for (Item inventory_item : inventory.getItems()){
                    if(inventory_item != null && item.getClass().getSimpleName().equals(item.getClass().getSimpleName())) foundItems++;
                }
                log.debug("needed items: {}", neededItems);
                log.debug("found items: {}", foundItems);
                if(foundItems == neededItems){
                    for (Item inventory_item : inventory.getItems()){
                        if (inventory_item == item) inventory.removeItem(inventory_item);
                    }
                    gamePanel.inGameInventoryBar.update();
                    Item result = switch (item.getClass().getSimpleName()) {
                        case "Bucket" -> new Bucket("Bucket", "Use this bucket to extinguish fire", "bucket.png");
                        default -> null;
                    };
                    assert result != null;
                    log.debug("crafted item: {}", result.getClass().getSimpleName());
                    updateResultSlot(result);
                }
            }));
        }

    }
    private void initResultSlot(){
        this.resultItemSlot = new ItemSlot(null, CRAFTING_SLOT_SIZE, resultSlot);
        resultSlot.setLayoutX(RESULT_SLOT_X);
        resultSlot.setLayoutY(RESULT_SLOT_Y);
        this.resultSlot.getChildren().add(resultItemSlot.getInventorySlot());
        log.debug("result slot updated coords: {}x{}", resultSlot.getLayoutX(), resultSlot.getLayoutY());
        this.root.getChildren().add(resultSlot);
    }
    private void updateResultSlot(Item item){
        this.root.getChildren().remove(resultSlot);
        resultSlot.getChildren().clear();
        resultItemSlot = new ItemSlot(item, CRAFTING_SLOT_SIZE, resultSlot);
        resultPair = new Pair<>(item, () -> {
            if (resultPair.getKey() != null){
                inventory.addItem(resultPair.getKey());
            }
        });
        resultItemSlot.setOnAction(resultPair.getValue());
        resultSlot.setLayoutX(RESULT_SLOT_X);
        resultSlot.setLayoutY(RESULT_SLOT_Y);
        resultSlot.getChildren().add(resultItemSlot.getInventorySlot());
        log.debug("result slot updated coords: {}x{}", resultSlot.getLayoutX(), resultSlot.getLayoutY());
        this.root.getChildren().add(resultSlot);
    }

    private void initCraftableItems(){
        this.craftableItems = new ArrayList<>();
        craftableItems.add(new Bucket("Bucket", "Use this bucket to extinguish fire", "bucket.png"));
    }

    private void initCraftingGrid(){
        this.craftingGrid = new GridPane();
        craftingGrid.setTranslateX(CRAFTING_GRID_X);
        craftingGrid.setTranslateY(CRAFTING_GRID_Y);
        craftingGrid.setHgap(CRAFTING_SLOT_PADDING);
        craftingGrid.setVgap(CRAFTING_SLOT_PADDING);
        itemSlots.forEach(data -> {
            ItemSlot itemSlot = new ItemSlot(data.getKey(), CRAFTING_SLOT_SIZE, craftingGrid);
            itemSlot.setOnAction(data.getValue());
            craftingGrid.add(itemSlot.getInventorySlot(), itemSlots.indexOf(data) % 2, itemSlots.indexOf(data) / 2);
        });
        this.root.getChildren().add(craftingGrid);
    }

    private void setPreviousScene(){
        mainStage.setScene(previousScene);
        gamePanel.getInputHandler().setCrafting(false);
    }
    private Parent createContent(){
        setBackground();
        initCraftableItems();
        initCraftingSlots();
        initCraftingGrid();
        initResultSlot();
        return this.root;
    }
}
