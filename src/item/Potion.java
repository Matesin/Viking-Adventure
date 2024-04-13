package item;

public class Potion extends Item{

    String effectType;
    int effectValue;
    public Potion(String name, String effectType, int effectValue, String description){
        this.name = name;
        this.effectType = effectType;
        this.effectValue = effectValue;
        this.description = description;
    }
    @Override
    public void loadSprite() {

    }
}
