package map;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Setter
@Getter
public class Tile implements Serializable {
    boolean solid = true;
    boolean collision = false;
    Image image;
}
