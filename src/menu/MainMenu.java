package menu;

import gameloop.GamePanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenu extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(root, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        GamePanel gamePanel = new GamePanel(scene, root);

        borderPane.setCenter(gamePanel);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
