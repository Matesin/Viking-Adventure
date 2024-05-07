package entity;

import item.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

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
    private int xOffset;
    private int yOffset;
    private Item item;
    private Character entity;

    public Hitbox(Character entity) {
        this.entity = entity;
        this.width = (int) this.entity.currentSprite.getWidth();
        this.height = (int) this.entity.currentSprite.getHeight();
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
        //display the hitbox
        int displayX = entity != null ? entity.getScreenCoordX() : item.getScreenCoordX();
        int displayY = entity != null ? entity.getScreenCoordY() : item.getScreenCoordY();
        if (offset) {
            displayX += this.xOffset;
            displayY += this.yOffset;
        }
        gc.strokeRect(displayX, displayY, this.width, this.height);
    }
    public boolean intersects(Hitbox other) {
        //if the other hitbox is an item, check if any of the corners of the item hitbox are inside this hitbox
        //NOTE: had to hardcode this for entities, because the rectangle.intersects method was not working
        if (other.item == null) {
            double[] otherIntersectPointsX = {other.coordX, other.coordX + (other.width / 4.0),  other.coordX + (other.width / 2.0), other.coordX + (other.width * 0.75), other.coordX + other.width};
            double[] otherIntersectPointsY = {other.coordY, other.coordY + (other.height / 4.0), other.coordY + (other.height / 2.0), other.coordY + (other.height * 0.75), other.coordY + other.height};        //detect if any of the corners of the other hitbox are inside this hitbox
            for (double i : otherIntersectPointsX) {
                for (double j : otherIntersectPointsY) {
                    if (this.hitArea.contains(i, j)) {
                        return true;
                    }
                }
            }
            return false;
        } else return this.hitArea.intersects(other.hitArea.getBoundsInLocal());
    }
}