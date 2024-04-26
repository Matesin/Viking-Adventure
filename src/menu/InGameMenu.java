package menu;

import static gameloop.Constants.Screen.*;

import gameloop.GamePanel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import static gameloop.Constants.MenuLayout.*;
import static gameloop.Constants.Button.*;

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
        WritableImage snapshot = new WritableImage(SCREEN_WIDTH, SCREEN_HEIGHT);
        previousScene.getRoot().snapshot(null, snapshot);
        ImageView imageView = new ImageView(snapshot);
        imageView.setEffect(new GaussianBlur());
        Image blurredImage = imageView.snapshot(null, null);
        BackgroundImage background = new BackgroundImage(blurredImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.root.setBackground(new Background(background));
    }
    private void addMenu(){
        menuButtons.forEach(data -> {
            MenuButton item = new MenuButton(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(MENU_BUTTONS_X);
            item.setTranslateY(MENU_BUTTONS_Y + menuButtons.indexOf(data) * MENU_BUTTONS_SPACING);
            Rectangle clip = new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT);
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