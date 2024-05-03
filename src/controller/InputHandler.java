package controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
public class InputHandler implements EventHandler<KeyEvent> {
    boolean upPressed;
    boolean downPressed;
    boolean leftPressed;
    boolean rightPressed;
    @Setter
    boolean paused = false;
    @Setter
    boolean pickUp = false;
    boolean inventory = false;

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
                case ESCAPE:
                    if (inventory){
                        inventory = false;
                    } else{
                        paused = !paused;
                    }
                    break;
                case E:
                    pickUp = true;
                    break;
                case I:
                    inventory = !inventory;
                    break;
                default:
                    rightPressed = leftPressed = downPressed = upPressed = false;
                    break;
            }
        } else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (keyCode) {
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
                case E:
                    pickUp = false;
                    break;
                default:
                    rightPressed = leftPressed = downPressed = upPressed = false;
                    break;
            }
        }
    }
    public void reset(){
        upPressed = downPressed = leftPressed = rightPressed = paused = false;
    }
}