package cz.cvut.fel.pjv.menu;

import cz.cvut.fel.pjv.gameloop.GamePanel;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Node;
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
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static cz.cvut.fel.pjv.gameloop.Constants.Screen.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Button.*;
import static cz.cvut.fel.pjv.gameloop.Constants.MenuLayout.*;

public class Menu extends Application implements GameMenu{
    private List<Pair<String, Runnable>> menuButtons;


    private final Pane root = new Pane();
    private final VBox menuBox = new VBox(2);

    @Override
    public void initButtons() {
        this.menuButtons = Arrays.asList(
                new Pair<>("New Game", () -> {
                    // Create a new GamePanel
                    StackPane game = new StackPane();
                    Scene scene = new Scene(game, SCREEN_WIDTH, SCREEN_HEIGHT);
                    // Set the scene of the current stage to the GamePanel
                    Stage stage = (Stage) this.root.getScene().getWindow();
                    new GamePanel(scene, game, false);
                    stage.setScene(scene);
                }),
                new Pair<>("Load Game", () -> {
                    // Load a saved game
                    StackPane game = new StackPane();
                    Scene scene = new Scene(game, SCREEN_WIDTH, SCREEN_HEIGHT);
                    Stage stage = (Stage) this.root.getScene().getWindow();
                    new GamePanel(scene, game, true);
                    stage.setScene(scene);
                }),
                new Pair<>("Settings", () -> {
                    StackPane settings = new StackPane();
                    Scene scene = new Scene(settings, SCREEN_WIDTH, SCREEN_HEIGHT);
                    Stage stage = (Stage) this.root.getScene().getWindow();
                    new GameSettings();
                    stage.setScene(scene);

                    //***TEMPORARY
                    Text text = new Text("Under Construction");
                    text.setX(SCREEN_MIDDLE_X);
                    text.setY(SCREEN_MIDDLE_Y);
                    text.setFill(Color.BLACK);
                    text.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
                    this.root.getChildren().add(text);
                    //TEMPORARY***
//                root.getChildren().add(settingsGUI);
                }),
                new Pair<>("Exit", () -> System.exit(0))
        );
    }


    private Parent createContent() throws FileNotFoundException {
        setBackGround();
        addTitle();
        initButtons();
        addMenu();
        startAnimation();
        return this.root;
    }

    @Override
    public void addMenu() {
        /*
         * TODO: ADD TRANSITIONS between menu and nodes
         */
        menuBox.setTranslateX(MENU_TRANSLATE_X);
        menuBox.setTranslateY(MENU_TRANSLATE_Y + 30);
        menuButtons.forEach(data -> {
            MenuButton item = new MenuButton(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-BUTTON_WIDTH);
            Rectangle clip = new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            menuBox.getChildren().addAll(item);
        });
        root.getChildren().add(menuBox);
    }

    @Override
    public void setBackGround() throws FileNotFoundException {

        InputStream stream = getClass().getResourceAsStream("/menu_backgrounds");
        if (stream == null) {
            throw new FileNotFoundException("Resource not found: res/menu_backgrounds");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        List<String> listOfFiles = reader.lines().toList();
        String file = listOfFiles.get(ThreadLocalRandom.current().nextInt(0, listOfFiles.size()));
        this.root.setStyle("-fx-background-image: url('/menu_backgrounds/" + file + "');" +
                            "-fx-background-size: contain;");
    }
    public void addTitle(){
        final int titleNameFontSize = 70;
        GameTitle title = new GameTitle("Viking Adventure", titleNameFontSize);
        title.display();
        title.setLayoutX(MENU_TITLE_X); // test value, change to dynamic
        title.setTranslateY(MENU_TITLE_Y);
        this.root.getChildren().add(title);
    }
    //TODO: CUSTOMIZE ANIMATION
    private void startAnimation(){
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), menuBox);
        st.setToX(1);
        st.setOnFinished(e -> {
            for (int i = 0; i < menuBox.getChildren().size(); i++){
                Node n = menuBox.getChildren().get(i);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.play();
            }
        });
        st.play();
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        Scene scene = new Scene(createContent(), SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("Viking Adventure");
        stage.setScene(scene);
        stage.show();
    }
}
