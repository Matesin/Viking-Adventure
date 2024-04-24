package gameloop;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import menu.InGameMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static gameloop.Constants.Game.*;

import static java.lang.Thread.sleep;
@Slf4j
public class GameLoop extends AnimationTimer {
    GamePanel gamePanel;
    Canvas canvas;
    GraphicsContext gc;
    InGameMenu inGameMenu;

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
        if (this.gamePanel.inputHandler.isPaused()) {
            this.stop();
            inGameMenu = new InGameMenu(this.gamePanel.getStage(), this.gamePanel);
            if (this.gamePanel.inputHandler.isPaused()) this.start();
        }
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(FRAME_TIME_MILLIS)));
        loop.setCycleCount(Animation.INDEFINITE);
        loop.play();
    }
}