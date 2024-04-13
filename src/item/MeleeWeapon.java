package item;

public class MeleeWeapon extends Weapon{
    public MeleeWeapon(String name, int damage, String description){
        this.name = name;
        this.damage = damage;
        this.description = description;
    }
    @Override
    public void loadSprite() {

    }
}
