package entity;

import gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.html.parser.Entity;

import static gameloop.Constants.Screen.SCREEN_MIDDLE_X;
import static gameloop.Constants.Screen.SCREEN_MIDDLE_Y;

@Slf4j
public class Hitbox {
    @Getter
    private int width;
    @Getter
    private int height;
    @Getter
    private int coordX;
    @Getter
    private int coordY;
    private Rectangle hitArea;
    private boolean offset = false;
    private Character entity;
    private int xOffset;
    private int yOffset;

    public Hitbox(Character entity) {
        this.entity = entity;
        this.width = this.entity.width;
        this.height = this.entity.height;
        this.coordX = this.entity.worldCoordX;
        this.coordY = this.entity.worldCoordY;
        this.hitArea = new Rectangle(width, height, coordX, coordY);

    }

    public Hitbox(Character entity, int width, int height, int xOffset, int yOffset) {
        this.width = width;
        this.height = height;
        this.entity = entity;
        this.xOffset = xOffset;
        this.yOffset = yOffset;

        this.coordX = this.entity.worldCoordX + this.xOffset;
        this.coordY = this.entity.worldCoordY + this.yOffset;
        this.hitArea = new Rectangle();
        this.hitArea.setWidth(width);
        this.hitArea.setHeight(height);
        this.hitArea.setX(coordX);
        this.hitArea.setY(coordY);
        this.offset = true;
    }
    public void update(){
        if(offset){
            this.coordX = this.entity.worldCoordX + this.xOffset;
            this.coordY = this.entity.worldCoordY + this.yOffset;
        } else {
            this.coordX = this.entity.worldCoordX;
            this.coordY = this.entity.worldCoordY;
        }
//        log.debug("Hitbox updated to: x: {}, y: {}", this.coordX, this.coordY);
        this.hitArea.setX(coordX);
        this.hitArea.setY(coordY);
    }
    public void display(GraphicsContext gc){
        int displayX = entity.getScreenCoordX();
        int displayY = entity.getScreenCoordY();
        if (offset) {
            displayX += this.xOffset;
            displayY += this.yOffset;
        }
        gc.strokeRect(displayX, displayY, this.width, this.height);
    }
}
