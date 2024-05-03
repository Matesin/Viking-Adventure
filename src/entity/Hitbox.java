package entity;

import gameloop.GamePanel;
import item.Item;
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
    private Item item;

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

    public Hitbox(Item item, int width, int height, int xOffset, int yOffset) {
        this.width = width;
        this.height = height;
        this.item = item;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.coordX = this.item.getWorldCoordX() + this.xOffset;
        this.coordY = this.item.getWorldCoordY() + this.yOffset;
        this.hitArea = new Rectangle();
        this.hitArea.setWidth(width);
        this.hitArea.setHeight(height);
        this.hitArea.setX(coordX);
        this.hitArea.setY(coordY);
        this.offset = true;
    }

    public void update(){
        if(entity != null){
            if(offset){
                this.coordX = this.entity.worldCoordX + this.xOffset;
                this.coordY = this.entity.worldCoordY + this.yOffset;
            } else {
                this.coordX = this.entity.worldCoordX;
                this.coordY = this.entity.worldCoordY;
            }
        } else if(item != null){
            if(offset){
                this.coordX = this.item.getWorldCoordX() + this.xOffset;
                this.coordY = this.item.getWorldCoordY() + this.yOffset;
            } else {
                this.coordX = this.item.getWorldCoordX();
                this.coordY = this.item.getWorldCoordY();
            }
        }
        this.hitArea.setX(coordX);
        this.hitArea.setY(coordY);
    }

    public void display(GraphicsContext gc){
        int displayX = entity != null ? entity.getScreenCoordX() : item.getScreenCoordX();
        int displayY = entity != null ? entity.getScreenCoordY() : item.getScreenCoordY();
        if (offset) {
            displayX += this.xOffset;
            displayY += this.yOffset;
        }
        gc.strokeRect(displayX, displayY, this.width, this.height);
    }
    public boolean intersects(Hitbox other) {
        return this.hitArea.intersects(other.hitArea.getBoundsInLocal());
    }
}