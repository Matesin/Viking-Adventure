package cz.cvut.fel.pjv.map_object;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fel.pjv.entity.Character;
import cz.cvut.fel.pjv.entity.DamageDealer;
import cz.cvut.fel.pjv.item.Item;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ActiveMapObject extends MapObject{
    public final Item activationItem;
    @Getter
    @Setter
    boolean activated = false;
    @Getter
    boolean dealingDamage = false;
    public final Image activeImage;
    DamageDealer damageDealer;
    protected ActiveMapObject(@JsonProperty("x")int worldCoordX,
                              @JsonProperty("y") int worldCoordY,
                              @JsonProperty("idle_picture") String idlePictureID,
                              @JsonProperty("active_picture") String activePictureID,
                              @JsonProperty("activation_item") Item activationItem) {
        super(worldCoordX, worldCoordY, idlePictureID);
        this.activationItem = activationItem;
        this.activeImage = loadImage(activePictureID);
    }
    public void changeState(Item usedItem){
        // Change the state of the object
        if (usedItem == activationItem || activationItem == null){
            // Change the state of the object
//            this.activate();
            activated = !activated;
            log.debug("Object {}", (activated ? " activated" : " deactivated"));
            this.currentImage = activated ? activeImage : idleImage;
        }
    }
    abstract void activate();
    public void dealDamage(Character entity){
        damageDealer.dealDamage(entity);
    }
}
