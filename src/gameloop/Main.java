package gameloop;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game");
        StackPane root = new StackPane();
        Scene scene = new Scene(root, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        GamePanel gamePanel = new GamePanel(scene, root);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gamePanel);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
