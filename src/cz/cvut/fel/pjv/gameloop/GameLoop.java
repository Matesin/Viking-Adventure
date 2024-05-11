package cz.cvut.fel.pjv.gameloop;

import cz.cvut.fel.pjv.menu.InGameMenu;
import cz.cvut.fel.pjv.inventory.InventoryGUI;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameLoop extends AnimationTimer {

    GamePanel gamePanel;
    GraphicsContext gc;
    InGameMenu inGameMenu;
    Thread inventoryThread;
    InventoryGUI inventory;

    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.gc = this.gamePanel.canvas.getGraphicsContext2D();
        log.info("GameLoop created");
    }

    // GAME LOOP
    @Override
    public void handle(long now) {
            this.gamePanel.update();
            this.gamePanel.render(gc);
        if (this.gamePanel.inputHandler.isPaused()) {
            this.stop();
            inGameMenu = new InGameMenu(this.gamePanel.getStage(), this.gamePanel);
            if (this.gamePanel.inputHandler.isPaused()) this.start();
        } else if (this.gamePanel.inputHandler.isInventory()) {
            this.stop();
            inventory = new InventoryGUI(this.gamePanel.getStage(), this.gamePanel);
            if (this.gamePanel.inputHandler.isInventory()) this.start();

        }
//        else if (this.gamePanel.inputHandler.isInventory() && (inventoryThread == null || !inventoryThread.isAlive())) {
//                inventoryThread = new Thread(() -> {
//                    log.info("Inventory thread created");
//                    inventory = new InventoryGUI(this.gamePanel.getStage(), this.gamePanel);
//                    inventoryThread = null;
//                });
//                inventoryThread.start();
//        } else {
//            if (inventoryThread != null && inventoryThread.isAlive()) {
//                inventoryThread.interrupt();
//                inventoryThread = null;
//            }
//        }
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(Constants.Game.FRAME_TIME_MILLIS)));
        loop.setCycleCount(Animation.INDEFINITE);
        loop.play();
    }
}