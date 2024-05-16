package cz.cvut.fel.pjv.crafting;

import cz.cvut.fel.pjv.gameloop.GamePanel;
import cz.cvut.fel.pjv.inventory.InGameInventoryBar;
import cz.cvut.fel.pjv.inventory.Inventory;
import cz.cvut.fel.pjv.item.Item;
import cz.cvut.fel.pjv.map_object.ActiveMapObject;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.List;

import static cz.cvut.fel.pjv.gameloop.Constants.Screen.SCREEN_HEIGHT;
import static cz.cvut.fel.pjv.gameloop.Constants.Screen.SCREEN_WIDTH;

public class CraftingGUI extends Pane {
    private int capacity;
    private final Pane root = new Pane();
    private final GamePanel gamePanel;
    private Scene previousScene;
    private Inventory inventory;
    private List<Pair<Item, Runnable>> itemSlots;
    private Stage mainStage;
    private ActiveMapObject craftingObject; //the object used for crafting (crafting table, etc.)

    public CraftingGUI(Stage mainStage, GamePanel gamePanel, ActiveMapObject craftingObject) {
        /*
         * This class is responsible for creating the crafting GUI and storing its values.
         * */
        this.gamePanel = gamePanel;
        this.inventory = gamePanel.player.getInventory();
        this.capacity = inventory.getCapacity();
        this.mainStage = mainStage;
        this.craftingObject = craftingObject;
        if (mainStage != null) {
            this.previousScene = mainStage.getScene();
            Scene inventoryScene = new Scene(createContent(), SCREEN_WIDTH, SCREEN_HEIGHT);
            inventoryScene.setOnKeyPressed(event -> {
                if(event.getCode().toString().equals("ESCAPE")){
                    setPreviousScene();
                }
            });
            mainStage.setScene(inventoryScene);
        }
    }
    public void display(){
        getChildren().add(this);
    }
    private void setPreviousScene(){
        mainStage.setScene(previousScene);
        this.craftingObject.setActivated(false);
    }
    private Parent createContent(){
        //TODO
        return this.root;
    }
}
