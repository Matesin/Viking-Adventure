package gameloop;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class GameLoop extends AnimationTimer {
    GamePanel gamePanel;
    Canvas canvas;
    GraphicsContext gc;
    double durationMillis = 100000000.0;
    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.canvas = gamePanel.canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    // GAME LOOP
    @Override
    public void handle(long now) {
        this.gamePanel.update();
        this.gamePanel.draw(gc);
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(durationMillis / gamePanel.getFps())));
        loop.setCycleCount(Animation.INDEFINITE);
        loop.play();
    }
}