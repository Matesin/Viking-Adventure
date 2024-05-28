package cz.cvut.fel.pjv.crafting;

import cz.cvut.fel.pjv.gameloop.GamePanel;
import cz.cvut.fel.pjv.inventory.Inventory;
import cz.cvut.fel.pjv.inventory.ItemSlot;
import cz.cvut.fel.pjv.item.Bucket;
import cz.cvut.fel.pjv.item.Item;
import cz.cvut.fel.pjv.item.MeleeWeapon;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static cz.cvut.fel.pjv.gameloop.Constants.Crafting.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Screen.SCREEN_HEIGHT;
import static cz.cvut.fel.pjv.gameloop.Constants.Screen.SCREEN_WIDTH;
import static javafx.scene.paint.Color.WHITE;

/**
 * The CraftingGUI class is responsible for creating the crafting GUI and storing its values.
 * It manages the crafting slots, craftable items, and the result slot.
 */
@Slf4j
public class CraftingGUI extends Pane{
    private final Pane root = new Pane();
    private final GamePanel gamePanel;
    private Scene previousScene;
    private final Inventory inventory;
    private List<Pair<Item, Runnable>> itemSlots;
    private List<Item> craftableItems;
    private ItemSlot resultSlot;
    private final Stage mainStage;

    /**
     * Constructor for the CraftingGUI class.
     * Initializes the crafting GUI and sets up the necessary values.
     *
     * @param mainStage The main stage of the application.
     * @param gamePanel The game panel.
     */
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
            this.resultSlot = new ItemSlot(null, CRAFTING_SLOT_SIZE, root);
            initResultSlot();
            mainStage.setScene(inventoryScene);
        }
    }
    /**
     * Sets the background for the crafting GUI.
     */
    private void setBackground() {
        //assuming the background is the previous scene
        WritableImage snapshot = new WritableImage(SCREEN_WIDTH, SCREEN_HEIGHT);
        previousScene.getRoot().snapshot(null, snapshot);
        ImageView imageView = new ImageView(snapshot);
        //setting the background to be blurred and darkened
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        imageView.setEffect(new GaussianBlur());
        imageView.setEffect(colorAdjust);
        Image blurredImage = imageView.snapshot(null, null);
        BackgroundImage background = new BackgroundImage(blurredImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.root.setBackground(new Background(background));
    }
    /**
     * Initializes the crafting slots.
     */
    private void initCraftingSlots(){
        this.itemSlots = new ArrayList<>();
        for (Item item : craftableItems){
            itemSlots.add(new Pair<>(item, () -> handleCraftingSlotClick(item)));
        }
    }

    /**
     * Initializes the crafting arrow.
     */

    private void initArrow(){
        //assuming the arrow is located in this file every time
        ImageView arrow = new ImageView(new Image("gui/crafting_arrow.png"));
        arrow.setFitWidth(CRAFTING_ARROW_SIZE);
        arrow.setFitHeight(CRAFTING_ARROW_SIZE);
        arrow.setLayoutX(CRAFTING_ARROW_X);
        arrow.setLayoutY(CRAFTING_ARROW_Y);
        arrow.setOpacity(0.75);
        this.root.getChildren().add(arrow);
    }

    /**
     * Handles the crafting slot click.
     *
     * @param item The item to handle the crafting slot click for.
     */

    private void handleCraftingSlotClick(Item item) {
        log.debug("clicked on item {}", item.getName());
        int neededItems = item.getCraftingMaterials().size();
        int foundItems = countFoundItems(item);
        log.debug("needed items: {}", neededItems);
        log.debug("found items: {}", foundItems);
        if(foundItems == neededItems){
            Item result = Item.createItem(item.getClass().getSimpleName(), item.getName(), item.getDescription(), item.getPictureID());
            log.debug("crafted item: {}", result.getClass().getSimpleName());
            updateResultSlot(result);
            log.debug("result slot updated");
        } else {
            Text text = new Text("Not enough materials");
            text.setLayoutX(CRAFTING_GRID_X + CRAFTING_SLOT_PADDING * 2);
            text.setLayoutY(CRAFTING_GRID_Y - CRAFTING_SLOT_PADDING);
            Font font = Font.font("Segoe Script", FontWeight.BOLD, 40);
            text.setFill(WHITE);
            text.setFont(font);
            root.getChildren().add(text);

            // Create a new thread to remove the text after 5 seconds
            new Thread(() -> {
                try {
                    Thread.sleep(5000); // Wait for 5 seconds
                } catch (InterruptedException e) {
                   log.error("Error while waiting", e);
                    Thread.currentThread().interrupt(); // Re-interrupt the thread
                }
                // Update the UI on the JavaFX Application Thread
                Platform.runLater(() -> root.getChildren().remove(text));
            }).start();
        }
    }

    /**
     * Counts the found items.
     *
     * @param item The item to count the found items for.
     * @return The count of the found items.
     */

    private int countFoundItems(Item item) {
        int foundItems = 0;
        for (String neededItem : item.getCraftingMaterials()){
            foundItems += countInventoryItems(neededItem);
        }
        return foundItems;
    }

    /**
     * Counts the inventory items.
     *
     * @param neededItem The item to count.
     * @return The count of the inventory items.
     */

    private int countInventoryItems(String neededItem) {
        int count = 0;
        for (int i = 0; i < inventory.getCapacity(); i++){
            if (inventory.getItems()[i] != null && inventory.getItems()[i].getClass().getSimpleName().equals(neededItem)){
                count++;
                break;
            }
        }
        return count;
    }

    /**
     * Initializes the result slot.
     */
    private void initResultSlot(){
        this.root.getChildren().add(resultSlot.getInventorySlot());
        log.debug("result slot initialized");
        this.root.getChildren().add(resultSlot);
        resultSlot.getInventorySlot().setLayoutX(RESULT_SLOT_X);
        resultSlot.getInventorySlot().setLayoutY(RESULT_SLOT_Y);
    }

    /**
     * Updates the result slot with the given item.
     *
     * @param item The item to update the result slot with.
     */
    private void updateResultSlot(Item item){
        removeResultSlotFromRoot();
        setResultSlotItem(item);
        setResultSlotOnMouseClicked(item);
        addResultSlotToRoot();
        logResultSlotUpdate(item);
    }

    /**
     * Removes the result slot from the root.
     */

    private void removeResultSlotFromRoot() {
        this.root.getChildren().remove(resultSlot.getInventorySlot());
    }

    /**
     * Sets the item of the result slot.
     *
     * @param item The item to set the result slot with.
     */

    private void setResultSlotItem(Item item) {
        resultSlot.setItem(item);
        resultSlot.initImage();
    }

    /**
     * Sets the on mouse clicked event for the result slot.
     *
     * @param item The item to set the on mouse clicked event for.
     */

    private void setResultSlotOnMouseClicked(Item item) {
        resultSlot.getInventorySlot().setOnMouseClicked(event -> {
            inventory.addItem(item);
            if (item.getCraftingMaterials() != null) {
                handleCraftingMaterials(item);
                displayRemovedItems(item);
            }
            updateResultSlot(null);
            gamePanel.getInGameInventoryBar().update();
        });
    }

    /**
     * Displays the removed items.
     *
     * @param item The item to display the removed items for.
     */

    private void displayRemovedItems(Item item) {
        Text text = createText("Removed items:", CRAFTING_ARROW_X, CRAFTING_GRID_Y - CRAFTING_SLOT_PADDING);
        root.getChildren().add(text);
        removeTextAfterDelay(text);
        int i;
        for (i = 0; i < item.getCraftingMaterials().size(); i++) {
            Text materialText = createText(item.getCraftingMaterials().get(i), CRAFTING_ARROW_X, CRAFTING_GRID_Y - CRAFTING_SLOT_PADDING + RESULT_TEXT_SIZE + i * RESULT_TEXT_SIZE);
            root.getChildren().add(materialText);
            removeTextAfterDelay(materialText);
        }
        Text removedFromInvText = createText("from inventory", CRAFTING_ARROW_X, CRAFTING_GRID_Y - CRAFTING_SLOT_PADDING + RESULT_TEXT_SIZE + i * RESULT_TEXT_SIZE);
        root.getChildren().add(removedFromInvText);
        removeTextAfterDelay(removedFromInvText);
    }

    /**
     * Creates a text with the given content, x, and y.
     *
     * @param content The content of the text.
     * @param x The x coordinate of the text.
     * @param y The y coordinate of the text.
     * @return The created text.
     */

    private Text createText(String content, double x, double y) {
        Text text = new Text(content);
        text.setLayoutX(x);
        text.setLayoutY(y);
        text.setLineSpacing(10);
        Font font = Font.font("Segoe Script", FontWeight.BOLD, RESULT_TEXT_SIZE);
        text.setFill(WHITE);
        text.setFont(font);
        return text;
    }

    /**
     * Removes the text after a delay.
     *
     * @param text The text to remove.
     */
    private void removeTextAfterDelay(Text text) {
        new Thread(() -> {
            try {
                Thread.sleep(RESULT_TEXT_SLEEP); // Wait for 3 seconds
            } catch (InterruptedException e) {
                log.error("Error while waiting", e);
                Thread.currentThread().interrupt(); //Re-interrupt the thread
            }
            //Update the UI
            Platform.runLater(() -> root.getChildren().remove(text));
        }).start();
    }

    /**
     * Handles the crafting materials.
     *
     * @param item The item to craft.
     */

    private void handleCraftingMaterials(Item item) {
        for (int i = 0; i < item.getCraftingMaterials().size(); i++) {
            handleInventoryItems(item, i);
        }
    }

    /**
     * Handles the inventory items.
     *
     * @param item The item to craft.
     * @param i The index of the item in the crafting materials list.
     */
    private void handleInventoryItems(Item item, int i) {
        for (int j = 0; j < inventory.getCapacity(); j++) {
            if (inventory.getItems()[j] != null && inventory.getItems()[j].getClass().getSimpleName().equals(item.getCraftingMaterials().get(i))) {
                handlePickedItem(j);
                inventory.removeItem(inventory.getItems()[j]);
                break;
            }
        }
    }

    /**
     * Handles the picked item.
     *
     * @param j The index of the item in the inventory.
     */
    private void handlePickedItem(int j) {
        if(inventory.getPickedItem() != null && inventory.getPickedItem().equals(inventory.getItems()[j])){
            inventory.setPickedItem(null);
        }
    }

    /**
     * Adds the result slot to the root.
     */
    private void addResultSlotToRoot() {
        this.root.getChildren().add(resultSlot.getInventorySlot());
    }

    /**
     * Logs the result slot update.
     *
     * @param item The item to update the result slot with.
     */
    private void logResultSlotUpdate(Item item) {
        if (item != null){
            log.debug("result slot updated with item {}", item.getName());
        } else {
            log.debug("result slot updated with null");
        }
    }

    /**
     * Initializes the list of craftable items.
     */
    private void initCraftableItems(){
        this.craftableItems = new ArrayList<>();
        craftableItems.add(new Bucket("Bucket", "Use this bucket to extinguish fire", "bucket.png"));
        craftableItems.add(new MeleeWeapon("Swordus minimus", "Jus' a miniature sword", "small_sword.png", 2));
        craftableItems.add(new MeleeWeapon("Axeus ultimus", "The ULTIMATE axe", "large_axe.png", 5));
        craftableItems.add(new MeleeWeapon("Axeus", "Normal axe", "axe.png", 2));
    }

    /**
     * Initializes the crafting grid.
     */
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

    /**
     * Sets the previous scene.
     */
    private void setPreviousScene(){
        mainStage.setScene(previousScene);
        gamePanel.getInputHandler().setCrafting(false);
    }

    /**
     * Creates the content for the crafting GUI.
     *
     * @return The root pane containing the crafting GUI content.
     */
    private Parent createContent(){
        setBackground();
        initCraftableItems();
        initCraftingSlots();
        initCraftingGrid();
        initArrow();
        return this.root;
    }
}
