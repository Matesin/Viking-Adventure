package item;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;

@Getter
@Setter
public class LevelKey extends Item{
    private int level;
    private int ID;

    public LevelKey(String name, int level, int ID, int worldCoordX, int worldCoordY) throws FileNotFoundException {
        this.name = name;
        this.level = level;
        this.ID = ID;
        this.worldCoordX = worldCoordX;
        this.worldCoordY = worldCoordY;
        loadImage(level + "_key_" + ID);
    }
    public void setName(String name) {
        this.name = name;
    }

}
