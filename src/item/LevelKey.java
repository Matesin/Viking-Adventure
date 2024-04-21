package item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelKey extends Item{
    private int level;
    private int ID;

    public LevelKey() {
    }
    public void setName(String name) {
        this.name = name;
    }

}
