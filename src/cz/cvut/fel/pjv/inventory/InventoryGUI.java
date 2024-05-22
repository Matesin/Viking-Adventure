package cz.cvut.fel.pjv.inventory;

import cz.cvut.fel.pjv.gameloop.GamePanel;
import cz.cvut.fel.pjv.item.Bucket;
import cz.cvut.fel.pjv.item.Item;
import cz.cvut.fel.pjv.menu.Menu;
import cz.cvut.fel.pjv.menu.MenuButton;
import javafx.beans.binding.Bindings;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cz.cvut.fel.pjv.gameloop.Constants.Button.BUTTON_HEIGHT;
import static cz.cvut.fel.pjv.gameloop.Constants.Button.BUTTON_WIDTH;
import static cz.cvut.fel.pjv.gameloop.Constants.Inventory.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Screen.SCREEN_HEIGHT;
import static cz.cvut.fel.pjv.gameloop.Constants.Screen.SCREEN_WIDTH;
import static javafx.scene.paint.Color.BLACK;

@Slf4j
public class InventoryGUI {
    private int capacity;
    private final Pane root = new Pane();
    private final GamePanel gamePanel;
    private Scene previousScene;
    private final Inventory inventory;
    private final InGameInventoryBar inGameInventoryBar;
    private List<Pair<Item, Runnable>> itemSlots;
    private final Stage mainStage;
    private final HBox inventoryBox = new HBox();
    private List<Pair<Item, Runnable>> itemChoiceButtons;
    private HBox itemChoiceBox;
    private boolean isItemChoiceBox = false;


    public InventoryGUI(Stage mainStage, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.inGameInventoryBar = gamePanel.getInGameInventoryBar();
        this.inventory = gamePanel.player.getInventory();
        this.capacity = inventory.getCapacity();
        this.mainStage = mainStage;
        if (mainStage != null) {
            this.previousScene = mainStage.getScene();
            Scene inventoryScene = new Scene(createContent(), SCREEN_WIDTH, SCREEN_HEIGHT);
            inventoryScene.setOnKeyPressed(event -> {
                if(event.getCode().toString().equals("I") || event.getCode().toString().equals("ESCAPE")){
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
        imageView.setEffect(new GaussianBlur());
        Image blurredImage = imageView.snapshot(null, null);
        BackgroundImage background = new BackgroundImage(blurredImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.root.setBackground(new Background(background));
    }

    //TODO: Fix the bug with choiceButtons only displaying once
    private void initSlots() {
        this.itemSlots = new ArrayList<>();
        int i = 0;
        for(Item item : inventory.getItems()){
            double x = (FIRST_SLOT_X + (i % capacity) * (SLOT_SIZE + SLOT_PADDING) - (double) BUTTON_WIDTH / 2);
            double y = (FIRST_SLOT_Y + 1.5 * BUTTON_HEIGHT + ((double) i / capacity));
            itemSlots.add(new Pair<>(item, () -> {
                if (!isItemChoiceBox){
                    addChoiceButtons(item, x, y);
                    isItemChoiceBox = true;
                    this.root.setOnMouseClicked(event -> {
                    if (!itemChoiceBox.getBoundsInParent().contains(event.getX(), event.getY())) {
                            this.root.getChildren().remove(itemChoiceBox);
                            isItemChoiceBox = false;
                        }
                    });
                }
                this.root.getChildren().add(itemChoiceBox);
            }));
            i++;
        }
    }

    private void initInventory(){
        itemSlots.forEach(data -> {
            ItemSlot itemSlot = new ItemSlot(data.getKey(), SLOT_SIZE, inventoryBox);
            itemSlot.setOnAction(data.getValue());
            itemSlot.getInventorySlot().setLayoutX(FIRST_SLOT_X + (itemSlots.indexOf(data) % capacity) * (SLOT_SIZE + SLOT_PADDING));
            itemSlot.getInventorySlot().setLayoutY(FIRST_SLOT_Y + ((double) itemSlots.indexOf(data) / capacity));
            setSlotEffects(itemSlot.getBase());
            //TODO: Implement item info
            ItemInfo itemInfo = new ItemInfo(itemSlot, itemSlot.getInventorySlot());
            this.root.getChildren().addAll(itemSlot.getInventorySlot(), itemInfo);
        });
    }

    private void initItemButtons(Item item){
        this.itemChoiceButtons = Arrays.asList(
                new Pair<>(item, () -> {
                    inventory.setPickedItem(item);
                    if(inGameInventoryBar != null){
                        inGameInventoryBar.update();
                    }
                    setPreviousScene();
                }),
                new Pair<>(item, () -> {
                    gamePanel.player.dropItem(item);
                    if(inGameInventoryBar != null){
                        inGameInventoryBar.update();
                    }
                    setPreviousScene();
                })
        );
    }

    private void addChoiceButtons(Item item, double x, double y){
        itemChoiceBox = new HBox(2);
        itemChoiceBox.setSpacing(10);
        itemChoiceBox.setLayoutX(x);
        itemChoiceBox.setLayoutY(y);
        if (item != null) {
            initItemButtons(item);
            MenuButton button1 = new MenuButton("Equip");
            button1.setOnAction(itemChoiceButtons.get(0).getValue());
            MenuButton button2 = new MenuButton("Drop");
            button2.setOnAction(itemChoiceButtons.get(1).getValue());
            itemChoiceBox.getChildren().addAll(button1, button2);
        }
    }

    private void setSlotEffects(Rectangle base){
        Glow glow = new Glow(0.8);
        Effect shadow = new DropShadow(5, Color.BLACK);
        base.effectProperty().bind(
                Bindings.when(base.hoverProperty())
                        .then(shadow)
                        .otherwise(glow)
        );
    }


    private Parent createContent(){
        setBackground();
        initInventoryBase();
        initSlots();
        initInventory();
        return this.root;
    }

    private void initInventoryBase() {
        Rectangle inventoryBase = new Rectangle(INVENTORY_WIDTH, INVENTORY_HEIGHT);
        inventoryBase.setFill(BLACK); // Black background
        inventoryBase.setOpacity(0.5); // 50% opacity
        inventoryBase.setArcWidth(20); // Rounded corners
        inventoryBase.setArcHeight(20); // Rounded corners
        inventoryBase.setLayoutX(INVENTORY_X); // Positioning the rectangle
        inventoryBase.setLayoutY(INVENTORY_Y);
        this.root.getChildren().add(inventoryBase);
    }
    private void setPreviousScene(){
        mainStage.setScene(previousScene);
        gamePanel.getInputHandler().setInventory(false);
    }

}