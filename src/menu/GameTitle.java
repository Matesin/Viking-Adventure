package menu;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameTitle extends Pane {
    private final Text title = new Text();
    public GameTitle(String name, int fontSize){
        /*
         * This class is responsible for creating the title of the game and storing its values.
         * */
        title.setText(name);
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/bitmgothic/Bitmgothic.ttf"), fontSize * 1.5);
        title.setFont(font);
        title.setFill(Color.BLACK);
        title.setStroke(Color.WHITE);
        title.setStrokeWidth(2);
        title.setTranslateX(0);
        title.setTranslateY(50);
    }
    public void setTranslate(int x, int y){
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
    public void setStrokeWidth(int strokeWidth){
        title.setStrokeWidth(strokeWidth);
    }
    public void setStrokeColor(Color color){
        title.setStroke(color);
    }
    public void setFill(Color color){
        title.setFill(color);
    }
    public void setFont(Font font){
        title.setFont(font);
    }
    public void display(){
        getChildren().add(title);
    }
}
