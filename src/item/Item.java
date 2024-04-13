package item;

import java.awt.image.BufferedImage;

public abstract class Item {
    public String name;
    public String description;
    BufferedImage placementImage; //Image used when the object is placed on the map
    BufferedImage inventoryImage; //Image used when the object is in the player's inventory

    public abstract void loadSprite();
}
