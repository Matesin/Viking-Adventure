package controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler implements EventHandler<KeyEvent> {
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;

    public InputHandler() {
        upPressed = downPressed = leftPressed = rightPressed = false;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (keyCode){
                case W:
                case UP:
                    System.out.println("Up pressed");
                    upPressed = true;
                    break;
                case S:
                case DOWN:
                    System.out.println("Down pressed");
                    downPressed = true;
                    break;
                case A:
                case LEFT:
                    System.out.println("Left pressed");
                    leftPressed = true;
                    break;
                case D:
                case RIGHT:
                    System.out.println("Right pressed");
                    rightPressed = true;
                    break;
                default:
                    rightPressed = leftPressed = downPressed = upPressed = false;
                    break;
            }
        } else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (keyCode){
                case W:
                case UP:
                    System.out.println("Up released");
                    upPressed = false;
                    break;
                case S:
                case DOWN:
                    System.out.println("Down released");
                    downPressed = false;
                    break;
                case A:
                case LEFT:
                    System.out.println("Left released");
                    leftPressed = false;
                    break;
                case D:
                case RIGHT:
                    System.out.println("Right released");
                    rightPressed = false;
                    break;
                default:
                    rightPressed = leftPressed = downPressed = upPressed = false;
                    break;
            }
        }
        System.out.println("input status: " + upPressed + downPressed + leftPressed + rightPressed);
    }
}