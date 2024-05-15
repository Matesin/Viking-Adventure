package map_object;

import cz.cvut.fel.pjv.item.Item;

public class Door extends ActiveMapObject{

    protected Door(int worldCoordX, int worldCoordY, String idlePictureID, String activePictureID, Item activationItem) {
        super(worldCoordX, worldCoordY, idlePictureID, activePictureID, activationItem);
    }

    @Override
    void activate() {

    }
}
