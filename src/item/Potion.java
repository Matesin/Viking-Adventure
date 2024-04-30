package item;

import java.io.FileNotFoundException;

public class Potion extends Item{

    String effectType;
    int effectValue;
    public Potion(String name, String effectType, int effectValue, String description) throws FileNotFoundException {
        this.name = name;
        this.effectType = effectType;
        this.effectValue = effectValue;
        this.description = description;
        loadImage(name);
    }
}
