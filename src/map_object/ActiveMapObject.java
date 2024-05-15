package map_object;

import cz.cvut.fel.pjv.item.Item;
import javafx.scene.image.Image;

public abstract class ActiveMapObject extends MapObject{
    public final Item activationItem;
    public boolean activated = false;
    public final Image activeImage;
    protected ActiveMapObject(int worldCoordX, int worldCoordY, String idlePictureID, String activePictureID, Item activationItem) {
        super(worldCoordX, worldCoordY, idlePictureID);
        this.activationItem = activationItem;
        this.activeImage = loadImage(activePictureID);
    }
    public void changeState(Item usedItem){
        // Change the state of the object
        if (usedItem == activationItem || activationItem == null){
            // Change the state of the object
            this.activate();
        }
    }
    abstract void activate();
}
