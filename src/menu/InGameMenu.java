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
    private void setMenu() {
        menuButtons = Arrays.asList(
                new Pair<>("Resume", () -> {
                    //exit the menu and return to the game
                    this.gamePanel.getInputHandler().setPaused(false);
                    mainStage.setScene(this.gamePanel.getScene());
                }),
                new Pair<>("Save & Exit", () -> {
                    // Create a new GamePanel
                }),
                new Pair<>("Settings", () -> {
                    StackPane settings = new StackPane();
                    Scene scene = new Scene(settings, SCREEN_WIDTH, SCREEN_HEIGHT);
                    Stage stage = (Stage) this.root.getScene().getWindow();
                    new SettingsGUI();
                    Text text = new Text("Under Construction");
                    text.setX(SCREEN_MIDDLE_X);
                    text.setY(SCREEN_MIDDLE_Y);
                    text.setFill(Color.BLACK);
                    text.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
                    this.root.getChildren().add(text);
                    stage.setScene(scene);
                })
        );
    }

    private final Pane root = new Pane();
    private final VBox menuBox = new VBox(-5);
    private final Stage mainStage;
    private final GamePanel gamePanel;
    private List<Pair<String, Runnable>> menuButtons;
    public InGameMenu(Stage mainStage, GamePanel gamePanel) {
        this.mainStage = mainStage;
        this.gamePanel = gamePanel;
        if (mainStage != null){
            mainStage.setScene(new Scene(createContent(), SCREEN_WIDTH, SCREEN_HEIGHT));
        }
        createContent();
        log.info("InGameMenu created");
    }

    private void setScene() {
        this.root.setStyle("-fx-background-color: white;");
    }
    private void addMenu(){
        menuButtons.forEach(data -> {
            MenuButton item = new MenuButton(data.getKey());
            item.setOnAction(data.getValue());
            Rectangle clip = new Rectangle(300, 60);
            item.setClip(clip);
            menuBox.getChildren().addAll(item);
        });
        root.getChildren().add(menuBox);
    }
    private Parent createContent(){
        setMenu();
        setScene();
        addMenu();
        return this.root;
    }
}