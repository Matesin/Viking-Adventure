package menu;

import javafx.beans.binding.Bindings;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class MenuItem extends Pane {
    private Text text;
    private Effect shadow = new DropShadow(5, Color.BLACK);
    private Effect glow = new BoxBlur(1, 1, 3);
    private int textSize = 20;
    private int buttonWidth = 200;
    private int buttonHeight = 60;
    public MenuItem(String name){
        Polygon button = new Polygon(
                0, 0,
                buttonWidth, 0,
                buttonWidth,  buttonHeight,
                0, buttonHeight
        );
        text = new Text(name);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, textSize));
        text.setTranslateX(10);
        text.setTranslateY((double) buttonHeight / 2 + (double) textSize / 4);
        text.setFill(Color.BLACK);
        text.setStroke(Color.WHITE);
        text.effectProperty().bind(
                Bindings.when(hoverProperty())
                        .then(shadow)
                        .otherwise(glow)
        );
        getChildren().addAll(button, text);
    }
    public void setOnAction(Runnable action){
        setOnMouseClicked(e -> action.run());
    }
}
