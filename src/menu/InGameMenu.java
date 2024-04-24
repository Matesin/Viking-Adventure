package menu;

import static gameloop.Constants.Screen.*;

import gameloop.GamePanel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class InGameMenu {
    private final Pane root = new Pane();
    private final VBox menuBox = new VBox(-5);
    private final Stage mainStage;
    private final GamePanel gamePanel;
    private Scene previousScene;
    private List<Pair<String, Runnable>> menuButtons;
    public InGameMenu(Stage mainStage, GamePanel gamePanel) {
        this.mainStage = mainStage;
        this.gamePanel = gamePanel;
        if (mainStage != null){
            this.previousScene = mainStage.getScene();
            mainStage.setScene(new Scene(createContent(), SCREEN_WIDTH, SCREEN_HEIGHT));
        }
        log.info("InGameMenu created");
    }
    private void initButtons(){
        this.menuButtons = Arrays.asList(
                new Pair<>("Resume", () -> {
                    //exit the menu and return to the game
                    log.info("Resuming game");
                    gamePanel.getInputHandler().reset();
                    mainStage.setScene(previousScene);
                }),
                new Pair<>("Save & Exit", () -> {
                    //save the game and exit
                    log.info("Saving game and exiting");
                    System.exit(0);
                }),
                new Pair<>("Settings", () -> {
                    //this will implement the same settings class as the main menu
                })
        );
    }
    private void setScene() {
        this.root.setStyle("-fx-background-color: white;");
    }
    private void addMenu(){
        menuButtons.forEach(data -> {
            MenuButton item = new MenuButton(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(SCREEN_MIDDLE_X - 100);
            item.setTranslateY(SCREEN_MIDDLE_Y - 100 + menuButtons.indexOf(data) * 60);
            Rectangle clip = new Rectangle(300, 60);
            item.setClip(clip);
            menuBox.getChildren().addAll(item);
        });
        root.getChildren().add(menuBox);
    }
    private Parent createContent(){
        initButtons();
        setScene();
        addMenu();
        return this.root;
    }
}