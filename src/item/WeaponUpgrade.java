package item;

public class WeaponUpgrade extends Item{
    String upgradeType;
    public WeaponUpgrade(String name, String description, String upgradeType){
        this.name = name;
        this.description = description;
        this.upgradeType = upgradeType;
    }
    @Override
    public void loadSprite() {

    }
}
