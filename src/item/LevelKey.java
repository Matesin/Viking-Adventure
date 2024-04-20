package item;

public class LevelKey extends Item{
    int level;
    int ID;

    public LevelKey(int level, int ID) {
        this.level = level;
        this.ID = ID;
    }
    public void setName(String name) {
        this.name = name;
    }

}
