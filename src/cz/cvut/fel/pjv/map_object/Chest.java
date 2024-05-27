package cz.cvut.fel.pjv.map_object;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fel.pjv.entity.Hitbox;
import cz.cvut.fel.pjv.item.Item;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Class representing a chest map object.
 */
@Slf4j
public class Chest extends ActiveMapObject{
    @Getter
    private final List<Item> items;
    private int itemIndex = 0;
    @Getter
    private boolean droppingItem = false;
    /**
     * @param worldCoordX x-coordinate of the chest
     * @param worldCoordY y-coordinate of the chest
     * @param idlePictureID picture ID of the chest
     * @param activePictureID picture ID of the chest when activated
     * @param activationItem item needed to activate the chest
     */
//
//    protected Chest(@JsonProperty("x")int worldCoordX,
//                    @JsonProperty("y") int worldCoordY,
//                    @JsonProperty("idle_picture") String idlePictureID,
//                    @JsonProperty("active_picture") String activePictureID,
//                    @JsonProperty("activation_item") String activationItem) {
//        super(worldCoordX, worldCoordY, idlePictureID, activePictureID, activationItem);
//        this.items = null;
//    }

    protected Chest(@JsonProperty("x")int worldCoordX,
                    @JsonProperty("y") int worldCoordY,
                    @JsonProperty("idle_picture") String idlePictureID,
                    @JsonProperty("active_picture") String activePictureID,
                    @JsonProperty("activation_item") String activationItem,
                    @JsonProperty("items") List<Item> items) {
        super(worldCoordX, worldCoordY, idlePictureID, activePictureID, activationItem);
        this.items = items;
        if (items != null) {
            for (Item item : items) {
                log.debug("Item in chest: {}", item.getClass().getSimpleName());
            }
        }
    }

    @Override
    protected void reactToActivation(){
        activated = false;
        log.info("Chest opened");
        this.currentImage = activeImage;
    }

    @Override
    public void changeState(Item usedItem){
        // Change the state of the object
        if (activationItem == null) {
            reactToActivation();
            droppingItem = true;
            log.debug("Activation with no item successful");
        } else {
            if (usedItem != null && usedItem.getClass().getSimpleName().equals(activationItem)){
                reactToActivation();
                droppingItem = true;
                log.debug("Activation with item {} successful", usedItem.getClass().getSimpleName());
            }
        }
    }

    public Item dropItem(){
        Item droppedItem = null;
        if (items != null && itemIndex < items.size()) {
            droppedItem = items.get(itemIndex);
            droppedItem.setWorldCoordX(this.worldCoordX);
            droppedItem.setWorldCoordY(this.worldCoordY + 30);
            droppedItem.hitbox.update();
            items.remove(itemIndex);
            itemIndex++;
        }
        droppingItem = false;
        return droppedItem;
    }

}
