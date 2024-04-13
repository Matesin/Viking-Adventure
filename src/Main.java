import javafx.application.Application;
import javafx.stage.Stage;
import menu.MainMenu;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainMenu mainMenu = new MainMenu();
        mainMenu.start(primaryStage);
    }
}