package entity;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;


import java.io.IOException;

public abstract class Character {
    public int HP;
    @Getter
    int worldCoordX;
    @Getter
    int worldCoordY;
    @Getter
    @Setter
    int screenCoordX;
    @Getter
    @Setter
    int screenCoordY;
    @Getter
    String direction;
    @Getter
    int speed;
    int width;
    int height;
    public int movementSpeed;
    public int spriteID;
    public Hitbox hitbox;
    public boolean collision;
    Image up1;
    Image up2;
    Image down1;
    Image down2;
    Image left1;
    Image left2;
    Image right1;
    Image right2;

}
