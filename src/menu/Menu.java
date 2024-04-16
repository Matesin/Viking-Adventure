package menu;

import gameloop.GamePanel;
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

import java.util.Arrays;
import java.util.List;

public class Menu extends Application {
/*
* TODO: ADD TRANSITIONS between menu and nodes
*/
    private List<Pair<String, Runnable>> menuButtons = Arrays.asList(
            new Pair<>("Start", () -> {
                // Create a new GamePanel
                StackPane root = new StackPane();
                Scene scene = new Scene(root, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
                GamePanel gamePanel = new GamePanel(scene, root);
                // Set the scene of the current stage to the GamePanel
                Stage stage = (Stage) this.root.getScene().getWindow();
                stage.setScene(scene);
            }),
            new Pair<>("Settings", () -> {
                StackPane root = new StackPane();
                Scene scene = new Scene(root, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
                Stage stage = (Stage) this.root.getScene().getWindow();
                SettingsGUI settingsGUI = new SettingsGUI();
                //***TEMPORARY
                Text text = new Text("Under Construction");
                text.setX(GamePanel.SCREEN_MIDDLE_X);
                text.setY(GamePanel.SCREEN_MIDDLE_Y);
                text.setFill(Color.BLACK);
                text.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
                root.getChildren().add(text);
                //TEMPORARY***
//                root.getChildren().add(settingsGUI);
                stage.setScene(scene);
            }),
            new Pair<>("Exit", () -> {
                System.exit(0);
            }),
            new Pair<>("Jebuto", () -> {
                StackPane root = new StackPane();
                Scene scene = new Scene(root, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
                Stage stage = (Stage) this.root.getScene().getWindow();
                root.setStyle("-fx-background-color: white;");
                Text text = new Text("Jebuto");
                text.setX(GamePanel.SCREEN_MIDDLE_X);
                text.setY(GamePanel.SCREEN_MIDDLE_Y);
                text.setFill(Color.BLACK);
                text.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
                root.getChildren().add(text);
                stage.setScene(scene);
            })
    );
    private Pane root = new Pane();
    private final VBox menuBox = new VBox(2);

    private Parent createContent(){
        setBackGround();
        addTitle();
        addMenu( 100, 300);
        startAnimation();
        return this.root;
    }
//TODO: CREATE BACKGROUND IMAGE
    private void setBackGround(){
        this.root.setStyle("-fx-background-color: white;");
    }
    private void addTitle(){
        final int titleNameFontSize = 70;
        GameTitle title = new GameTitle("Viking Adventure", titleNameFontSize);
        title.setLayoutX((double) GamePanel.SCREEN_MIDDLE_X / 3); // test value, change to dynamic
        title.setTranslateY((double) GamePanel.SCREEN_HEIGHT / 5);

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

    private void addMenu(int xCoord, int yCoord){
        menuBox.setTranslateX(xCoord);
        menuBox.setTranslateY(yCoord);
        menuButtons.forEach(data -> {
            MenuButton item = new MenuButton(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-300);
            Rectangle clip = new Rectangle(300, 60);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            menuBox.getChildren().addAll(item);
        });
        root.getChildren().add(menuBox);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent(), GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        stage.setTitle("Viking Adventure");
        stage.setScene(scene);
        stage.show();
    }
}
