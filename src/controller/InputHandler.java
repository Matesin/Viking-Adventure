package controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.Getter;

public class InputHandler implements EventHandler<KeyEvent> {
    @Getter
    boolean upPressed;
    @Getter
    boolean downPressed;
    @Getter
    boolean leftPressed;
    @Getter
    boolean rightPressed;

    public InputHandler() {
        upPressed = downPressed = leftPressed = rightPressed = false;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (keyCode){
                case W, UP:
                    upPressed = true;
                    break;
                case S, DOWN:
                    downPressed = true;
                    break;
                case A, LEFT:
                    leftPressed = true;
                    break;
                case D, RIGHT:
                    rightPressed = true;
                    break;
                default:
                    rightPressed = leftPressed = downPressed = upPressed = false;
                    break;
            }
        } else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (keyCode){
                case W, UP:
                    upPressed = false;
                    break;
                case S, DOWN:
                    downPressed = false;
                    break;
                case A, LEFT:
                    leftPressed = false;
                    break;
                case D, RIGHT:
                    rightPressed = false;
                    break;
                default:
                    rightPressed = leftPressed = downPressed = upPressed = false;
                    break;
            }
        }
    }
}