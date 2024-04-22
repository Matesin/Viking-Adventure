package gameloop;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import menu.InGameMenu;

public class GameLoop extends AnimationTimer {
    GamePanel gamePanel;
    Canvas canvas;
    GraphicsContext gc;
    InGameMenu inGameMenu;
    double durationMillis = 100000000.0;
    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.canvas = gamePanel.canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.inGameMenu = new InGameMenu();
    }

    // GAME LOOP
    @Override
    public void handle(long now) {
        if (!this.gamePanel.inputHandler.isPaused()){
            this.gamePanel.update();
            this.gamePanel.draw(gc);
        } else {
            //placeholder
//            inGameMenu.draw(gc);
        }
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(durationMillis / gamePanel.getFps())));
        loop.setCycleCount(Animation.INDEFINITE);
        loop.play();
    }
}