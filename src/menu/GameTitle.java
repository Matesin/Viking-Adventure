package menu;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameTitle extends Pane {
    /*
    * This class is responsible for creating the title of the game and storing its values.
    * */
    private Text title;
    private int fontSize;
    public GameTitle(String name, int fontSize){
        title = new Text(name);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, fontSize));
        title.setFill(Color.BLACK);
        title.setStroke(Color.WHITE);
        title.setStrokeWidth(2);
        title.setTranslateX(50);
        title.setTranslateY(50);
        getChildren().add(title);
    }
}
