package cz.cvut.fel.pjv.crafting;

import cz.cvut.fel.pjv.gameloop.GamePanel;
import cz.cvut.fel.pjv.inventory.Inventory;
import cz.cvut.fel.pjv.inventory.ItemSlot;
import cz.cvut.fel.pjv.item.Bucket;
import cz.cvut.fel.pjv.item.Item;
import cz.cvut.fel.pjv.item.MeleeWeapon;
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
    private final Pane root = new Pane();
    private final GamePanel gamePanel;
    private Scene previousScene;
    private final Inventory inventory;
    private List<Pair<Item, Runnable>> itemSlots;
    private Pair<Item, Runnable> resultPair;
    private List<Item> craftableItems;
    private final Pane resultSlot = new Pane();
    private final Stage mainStage;


    public CraftingGUI(Stage mainStage, GamePanel gamePanel) {
        /*
         * This class is responsible for creating the crafting GUI and storing its values.
         * */
        this.gamePanel = gamePanel;
        this.inventory = gamePanel.player.getInventory();
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
                    if(inventory_item != null && item.getCraftingMaterials().contains(item.getClass().getSimpleName())) foundItems++;
                }
                log.debug("needed items: {}", neededItems);
                log.debug("found items: {}", foundItems);
                if(foundItems == neededItems){
                    //TODO: remove items from iventory first when the result item is clicked
                    for (Item inventory_item : inventory.getItems()){
                        if (inventory_item == item) inventory.removeItem(inventory_item);
                    }
                    gamePanel.getInGameInventoryBar().update();
                    Item result = Item.createItem(item.getClass().getSimpleName(), item.getName(), item.getDescription(), item.getPictureID());
                    log.debug("crafted item: {}", result.getClass().getSimpleName());
                    updateResultSlot(result);
                }
            }));
        }
    }
    private void initResultSlot(Item item){
        ItemSlot resultItemSlot = new ItemSlot(item, CRAFTING_SLOT_SIZE, resultSlot);
        log.debug("result item slot created with item: {}", item == null ? "null" : item.getName());
        resultSlot.setLayoutX(RESULT_SLOT_X);
        resultSlot.setLayoutY(RESULT_SLOT_Y);
        resultPair = new Pair<>(item, () -> {
            if (resultPair.getKey() != null){
                inventory.addItem(resultPair.getKey());
            }
        });
        resultItemSlot.setOnAction(resultPair.getValue());
        log.debug("result slot pane has {} children", resultSlot.getChildren().size());
        this.resultSlot.getChildren().add(resultItemSlot.getInventorySlot());
        log.debug("result slot added to the result slot pane");
        log.debug("result slot pane has {} children", resultSlot.getChildren().size());
    }
    private void updateResultSlot(Item item){
        log.debug("updating result slot");
        if (!resultSlot.getChildren().isEmpty()) {
            resultSlot.getChildren().remove(0);
        }
        initResultSlot(item);
        this.mainStage.sizeToScene();
        log.debug("result slot updated with item: {}", item.getName());
        log.debug("position of the result slot: x: {}, y: {}", resultSlot.getLayoutX(), resultSlot.getLayoutY());
    }

    private void initCraftableItems(){
        this.craftableItems = new ArrayList<>();
        craftableItems.add(new Bucket("Bucket", "Use this bucket to extinguish fire", "bucket.png"));
        craftableItems.add(new MeleeWeapon("Swordus minimus", "Jus' a miniature sword", "small_sword.png", 2));
        craftableItems.add(new MeleeWeapon("Axeus ultimus", "The ULTIMATE axe", "large_axe.png", 5));
        craftableItems.add(new MeleeWeapon("Axeus", "Normal axe", "axe.png", 2));
    }

    private void initCraftingGrid(){
        GridPane craftingGrid;
        craftingGrid = new GridPane();
        craftingGrid.setTranslateX(CRAFTING_GRID_X);
        craftingGrid.setTranslateY(CRAFTING_GRID_Y);
        craftingGrid.setHgap(CRAFTING_SLOT_PADDING);
        craftingGrid.setVgap(CRAFTING_SLOT_PADDING);
        itemSlots.forEach(data -> {
            ItemSlot itemSlot = new ItemSlot(data.getKey(), CRAFTING_SLOT_SIZE, craftingGrid);
            itemSlot.setOnAction(data.getValue());
            craftingGrid.add(itemSlot.getInventorySlot(), itemSlots.indexOf(data) % CRAFTING_GRID_COLS, itemSlots.indexOf(data) / CRAFTING_GRID_ROWS);
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
        initResultSlot(null);
        this.root.getChildren().add(resultSlot);
        return this.root;
    }
}
