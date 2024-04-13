package menu;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameTitle extends Pane {
    private Text title;
    public GameTitle(String name){
        title = new Text(name);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
        title.setFill(Color.BLACK);
        title.setStroke(Color.WHITE);
        title.setStrokeWidth(2);
        title.setTranslateX(50);
        title.setTranslateY(50);
        getChildren().add(title);
    }
}
