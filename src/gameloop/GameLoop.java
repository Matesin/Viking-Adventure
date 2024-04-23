package gameloop;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;
import menu.InGameMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

public class GameLoop extends AnimationTimer {
    private static final Logger log = LoggerFactory.getLogger(GameLoop.class);
    GamePanel gamePanel;
    Canvas canvas;
    GraphicsContext gc;
    InGameMenu inGameMenu;
    double durationMillis = 100000000.0;
    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.canvas = gamePanel.canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    // GAME LOOP
    @Override
    public void handle(long now) {
        if (!this.gamePanel.inputHandler.isPaused()){
            this.gamePanel.update();
            this.gamePanel.draw(gc);
        } else {
            //start new thread for in game menu
            //pause game loop
            this.stop();
            inGameMenu = new InGameMenu((Stage) this.gamePanel.root.getScene().getWindow(), this.gamePanel);
            if (this.gamePanel.inputHandler.isPaused()) this.start();
        }
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(durationMillis / gamePanel.getFps())));
        loop.setCycleCount(Animation.INDEFINITE);
        loop.play();
    }
}