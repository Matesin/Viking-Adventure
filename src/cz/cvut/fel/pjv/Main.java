package cz.cvut.fel.pjv;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import cz.cvut.fel.pjv.menu.Menu;

@Slf4j
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