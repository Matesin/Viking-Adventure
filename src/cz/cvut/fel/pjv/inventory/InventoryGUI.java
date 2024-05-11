package cz.cvut.fel.pjv.inventory;

import cz.cvut.fel.pjv.gameloop.Constants;
import cz.cvut.fel.pjv.gameloop.GamePanel;
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

import static javafx.scene.paint.Color.BLACK;

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
        Rectangle inventoryBase = initInventoryBase();
        inventory = gamePanel.player.getInventory();
        capacity = inventory.getCapacity();
        for (int i = 0; i < capacity; i++) {
            Rectangle slot = new Rectangle(Constants.Inventory.SLOT_SIZE, Constants.Inventory.SLOT_SIZE);
            slot.setFill(Color.WHITE);
            slot.setOpacity(0.5);
            slot.setArcWidth(20);
            slot.setArcHeight(20);
            slot.setLayoutX(Constants.Inventory.INVENTORY_X + (i % 5) * (Constants.Inventory.SLOT_SIZE + Constants.Inventory.SLOT_PADDING));
            slot.setLayoutY(Constants.Inventory.INVENTORY_Y + ((double) i / 5) * (Constants.Inventory.SLOT_SIZE + Constants.Inventory.SLOT_PADDING));
            this.root.getChildren().add(slot);
        }
    }
    private Parent createContent(){
        setBackground();
        return this.root;
    }
    private Rectangle initInventoryBase() {
        Rectangle inventoryBase = new Rectangle(Constants.Inventory.INVENTORY_WIDTH, Constants.Inventory.INVENTORY_HEIGHT);
        inventoryBase.setFill(BLACK); // Black background
        inventoryBase.setOpacity(0.5); // 50% opacity
        inventoryBase.setArcWidth(20); // Rounded corners
        inventoryBase.setArcHeight(20); // Rounded corners
        inventoryBase.setLayoutX(Constants.Inventory.INVENTORY_X); // Positioning the rectangle
        inventoryBase.setLayoutY(Constants.Inventory.INVENTORY_Y);
        this.root.getChildren().add(inventoryBase);
        return inventoryBase;
    }
}
