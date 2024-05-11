package cz.cvut.fel.pjv.map;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Tile {
    boolean solid = true;
    boolean collision = false;
    Image image;
    @Getter
    String type;

}

