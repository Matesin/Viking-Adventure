package cz.cvut.fel.pjv.map_object;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fel.pjv.entity.Character;
import cz.cvut.fel.pjv.entity.DamageDealer;
import cz.cvut.fel.pjv.item.Item;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@Slf4j
public abstract class ActiveMapObject extends MapObject{
    String activationItem;
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
                              @JsonProperty("activation_item") String activationItemName) {
        super(worldCoordX, worldCoordY, idlePictureID);
        if (activationItemName != null){
            this.activationItem = createActivationItem(activationItemName);
            log.debug("{} activation item: {}", this.getClass().getSimpleName(), activationItem);
        }

        this.activeImage = loadImage(activePictureID);
    }
    public void changeState(Item usedItem){
        // Change the state of the object
        if (usedItem != null){
            log.debug("Used item: {}", usedItem.getClass().getSimpleName());
        }
        if (activationItem != null){
            log.debug("Activation item: {}", activationItem);
        }

        if (activationItem == null) {
            reactToActivation();
        } else {
            if (usedItem != null && usedItem.getClass().getSimpleName().equals(activationItem)){
                reactToActivation();
            }
        }
        log.debug("Activation unsuccessful");
    }
    protected void reactToActivation(){
        activated = !activated;
        log.debug("Object {}", (activated ? "activated" : "deactivated"));
        this.currentImage = activated ? activeImage : idleImage;
    }
    public void dealDamage(Character entity){
        damageDealer.dealDamage(entity);
    }
    private String createActivationItem(String activationItemName){
        if (activationItemName != null){
            //return activationItemName with first letter capitalized
            return activationItemName.substring(0, 1).toUpperCase() + activationItemName.substring(1);
        }
        return null;
    }
}
