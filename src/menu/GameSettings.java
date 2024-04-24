package menu;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameSettings extends Pane {
    // CLASS ALLOWING USER TO CHANGE SETTINGS, somehow import all settings possible, to be implemented
    private Scene previousScene;
    private Scene scene;
    public GameSettings(Stage stage) {
        if (stage != null){
            this.previousScene = stage.getScene();
        }
    }
}
