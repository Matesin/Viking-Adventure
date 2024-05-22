package cz.cvut.fel.pjv.menu;

import javafx.beans.binding.Bindings;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static cz.cvut.fel.pjv.gameloop.Constants.Button.*;

/**
 * Class representing a menu button.
 * @see Pane
 */
public class MenuButton extends Pane {

    /**
     * Constructor for a menu button.
     * @param name text to be displayed on the button
     */
    public MenuButton(String name){
        Rectangle button = new Rectangle(
                BUTTON_WIDTH,
                BUTTON_HEIGHT
        );
        button.setOpacity(0.8);
        button.setArcWidth(20.0);
        button.setArcHeight(20.0);
        Text text = new Text(name);
        int textSize = 20;
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/rubber-biscuit/RUBBBB__.TTF"), textSize);
        text.setFont(font);
        text.setTranslateX(10);
        text.setTranslateY((double) BUTTON_HEIGHT / 2 + (double) textSize / 3);
        text.setFill(Color.BLACK);
        text.setStroke(Color.WHITE);
        Glow glow = new Glow(0.8);
        Effect shadow = new DropShadow(5, Color.BLACK);
        text.effectProperty().bind(
                Bindings.when(hoverProperty())
                        .then(shadow)
                        .otherwise(glow)
        );
        getChildren().addAll(button, text);
    }

    /**
     * Set action to be performed when the button is clicked.
     * @param action action to be performed
     */
    public void setOnAction(Runnable action){
        setOnMouseClicked(e -> action.run());
    }
}