package item;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public abstract class Item {
    /*
     * This class is the parent class for all items in the game. It contains the basic attributes that all items have.
     */
    String name;
    String description;
    public int worldCoordX;
    public int worldCoordY;
    Image placementImage = new Image("res/defaults/default_tile.png"); //Image used when the object is placed on the map
    Image inventoryImage; //Image used when the object is in the player's inventory
    void loadImage(String itemID) throws FileNotFoundException {
        String filepath = "res/items/" + itemID + ".png";
        FileInputStream fis = new FileInputStream(filepath);
        placementImage = new Image(fis);
        inventoryImage = new Image(fis, 50, 50, false, false); // TODO: Resize the image to fit in the inventory
    }
}
