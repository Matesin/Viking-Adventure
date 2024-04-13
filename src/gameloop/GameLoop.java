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
    double durationMillis = 1000000000.0;
    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.canvas = gamePanel.canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    @Override
    public void handle(long now) {
        // This is the game loop
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(durationMillis / GamePanel.fps), event -> {
            this.gamePanel.update();
            this.gamePanel.draw(gc);
        }));
        loop.setCycleCount(Animation.INDEFINITE);
        loop.play();
    }
}