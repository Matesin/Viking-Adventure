package cz.cvut.fel.pjv.inventory;

import cz.cvut.fel.pjv.gameloop.Constants;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import cz.cvut.fel.pjv.item.Item;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.html.Option;
import java.util.Optional;

import static cz.cvut.fel.pjv.gameloop.Constants.Inventory.*;
import static javafx.scene.paint.Color.BLACK;
@Slf4j
public class InventoryGUI {
    private int capacity;
    private final Pane root = new Pane();
    private final VBox menuBox = new VBox(-5);
    private final Stage mainStage;
    private final GamePanel gamePanel;
    private Scene previousScene;
    private Inventory inventory;

    public InventoryGUI(Stage mainStage, GamePanel gamePanel) {
        this.mainStage = mainStage;
        this.gamePanel = gamePanel;
        if (mainStage != null) {
            this.previousScene = mainStage.getScene();
            mainStage.setScene(new Scene(createContent(), Constants.Screen.SCREEN_WIDTH, Constants.Screen.SCREEN_HEIGHT));
        }
    }

    private void setBackground() {
        WritableImage snapshot = new WritableImage(Constants.Screen.SCREEN_WIDTH, Constants.Screen.SCREEN_HEIGHT);
        previousScene.getRoot().snapshot(null, snapshot);
        ImageView imageView = new ImageView(snapshot);
        imageView.setEffect(new GaussianBlur());
        Image blurredImage = imageView.snapshot(null, null);
        BackgroundImage background = new BackgroundImage(blurredImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.root.setBackground(new Background(background));
    }

    private void initInventory() {
        inventory = gamePanel.player.getInventory();
        capacity = inventory.getCapacity();
        for (int i = 0; i < capacity; i++) {
            ItemSlot itemSlot = new ItemSlot(Optional.ofNullable(inventory.getItems()[i]), i);
            this.root.getChildren().add(itemSlot.getSlot());

            // If the item slot has an item, create an ImageView for the item's image and add it to the root
            itemSlot.getItem().ifPresent(item -> {
                ImageView imageView = new ImageView(item.getInventoryImage());
                imageView.setLayoutX(itemSlot.getSlot().getLayoutX());
                imageView.setLayoutY(itemSlot.getSlot().getLayoutY());
                log.debug("layoutX: {}, layoutY: {}", itemSlot.getSlot().getLayoutX(), itemSlot.getSlot().getLayoutY());
                log.debug("image set to coords: {}, {}", imageView.getLayoutX(), imageView.getLayoutY());
                this.root.getChildren().add(imageView);
            });
        }
    }
    private Parent createContent(){
        setBackground();
        initInventoryBase();
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
}
