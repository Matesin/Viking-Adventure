package cz.cvut.fel.pjv.map_object;

/**
 * Class representing a door map object.
 */
public class Door extends ActiveMapObject{
    protected Door(int worldCoordX, int worldCoordY, String idlePictureID, String activePictureID, String activationItem, String currentImage) {
        super(worldCoordX, worldCoordY, idlePictureID, activePictureID, activationItem, currentImage);
    }
}
