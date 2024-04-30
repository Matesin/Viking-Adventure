package item;

import java.io.FileNotFoundException;

public class RangedWeapon extends Weapon{
    public RangedWeapon(String name, int damage, int range, String description) throws FileNotFoundException {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.description = description;
        loadImage(name);
    }

}
