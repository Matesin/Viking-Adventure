import javafx.application.Application;
import javafx.stage.Stage;
import menu.Menu;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Menu mainMenu = new Menu();
        mainMenu.start(primaryStage);
    }
}