package cz.cvut.fel.pjv.map_object;

public class Door extends ActiveMapObject{

    protected Door(int worldCoordX, int worldCoordY, String idlePictureID, String activePictureID, String activationItem) {
        super(worldCoordX, worldCoordY, idlePictureID, activePictureID, activationItem);
    }
}
