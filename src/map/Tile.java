package map;

import javafx.scene.image.Image;

import java.io.Serializable;

public class Tile implements Serializable {
    boolean isSolid;
    boolean collision = false;
    public Image image;

}
