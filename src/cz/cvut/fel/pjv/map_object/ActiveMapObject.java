package cz.cvut.fel.pjv.map_object;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fel.pjv.entity.Character;
import cz.cvut.fel.pjv.entity.DamageDealer;
import cz.cvut.fel.pjv.item.Item;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Class representing an active map object.
 */
@Slf4j
public abstract class ActiveMapObject extends MapObject{
    @JsonGetter("active_picture")
    public String getActivePicture() {
        return this.activePictureID;
    }
    String activePictureID;
    @Getter
    String activationItem;
    @Getter
    @Setter
    boolean activated = false;
    @Getter
    boolean dealingDamage = false;
    @JsonIgnore
    public final Image activeImage;
    @JsonIgnore
    DamageDealer damageDealer;

    /**
     * Constructor for an active map object.
     * @param worldCoordX world coordinate X
     * @param worldCoordY world coordinate Y
     * @param idlePictureID idle picture ID
     * @param activePictureID active picture ID
     * @param activationItemName activation item name
     */
    protected ActiveMapObject(@JsonProperty("x")int worldCoordX,
                              @JsonProperty("y") int worldCoordY,
                              @JsonProperty("idle_picture") String idlePictureID,
                              @JsonProperty("active_picture") String activePictureID,
                              @JsonProperty("activation_item") String activationItemName,
                              @JsonProperty("current_image") String currentImage){
        super(worldCoordX, worldCoordY, idlePictureID, currentImage);
        if (activationItemName != null){
            this.activationItem = createActivationItem(activationItemName);
        }
        this.activePictureID = activePictureID;
        this.activeImage = loadImage(activePictureID);
    }
    public void changeState(Item usedItem){
        // Change the state of the object
        if (activationItem == null) {
            reactToActivation();
        } else {
            if (usedItem != null && usedItem.getClass().getSimpleName().equals(activationItem)){
                reactToActivation();
                log.debug("Activation successful");
            }
        }
        log.debug("Activation unsuccessful, activation item should be {}", activationItem);
    }
    protected void reactToActivation(){
        activated = !activated;
        log.info("Object {}", (activated ? "activated" : "deactivated"));
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
