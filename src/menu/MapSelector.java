package menu;

import gameloop.GamePanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static gameloop.Constants.Screen.*;

public class MapSelector {
    // CLASS ALLOWING USER TO SELECT MAP
    private StackPane root;
    private Scene scene;
    private Stage stage;
    public MapSelector() {
        this.root = new StackPane();
        this.scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.stage = (Stage) this.root.getScene().getWindow();
    }
    private void setScene() {
        root.setStyle("-fx-background-color: white;");
        this.stage.setScene(this.scene);

    }
}
