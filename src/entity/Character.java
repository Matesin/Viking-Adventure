package entity;
import javafx.scene.image.Image;
import shapes.Rectangle;


import java.io.IOException;

public abstract class Character {
    public int HP;
    public int worldCoordX;
    public int worldCoordY;
    public int movementSpeed;
    public int spriteID;
    public Rectangle hitbox;
    Image up1;
    Image up2;
    Image down1;
    Image down2;
    Image left1;
    Image left2;
    Image right1;
    Image right2;
    public void getImage() throws IOException {
        // Load the image for the character
    }
}
