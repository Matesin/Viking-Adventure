package item;

import java.io.FileNotFoundException;

public class MeleeWeapon extends Weapon{
    public MeleeWeapon(String name, int damage, String description, int worldCoordX, int worldCoordY) throws FileNotFoundException {
        this.name = name;
        this.damage = damage;
        this.description = description;
        this.worldCoordX = worldCoordX;
        this.worldCoordY = worldCoordY;
        loadImage(name);
    }

}
