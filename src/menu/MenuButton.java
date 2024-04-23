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


public class MenuButton extends Pane {

    public MenuButton(String name){
        int buttonWidth = 200;
        int buttonHeight = 60;
        Polygon button = new Polygon(
                0, 0,
                buttonWidth, 0,
                buttonWidth, buttonHeight,
                0, buttonHeight
        );
        Text text = new Text(name);
        int textSize = 20;
        text.setFont(Font.font("Verdana", FontWeight.BOLD, textSize));
        text.setTranslateX(10);
        text.setTranslateY((double) buttonHeight / 2 + (double) textSize / 4);
        text.setFill(Color.BLACK);
        text.setStroke(Color.WHITE);
        Effect glow = new BoxBlur(1, 1, 3);
        Effect shadow = new DropShadow(5, Color.BLACK);
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
