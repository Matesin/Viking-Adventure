package cz.cvut.fel.pjv.map_object;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fel.pjv.entity.DamageDealer;
import cz.cvut.fel.pjv.entity.Hitbox;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;

import static cz.cvut.fel.pjv.gameloop.Constants.Fire.*;

/**
 * Class representing a fire map object.
 */
@Slf4j
public class Fire extends ActiveMapObject{
    private long lastChangeStateTime = 0;
    @JsonGetter("extinguished_picture")
    private String getExtinguishedPictureID(){
        return extinguishedPictureID;
    }
    private final String extinguishedPictureID;
    private final Image extinguishedImage;

    /**
     * Constructor for a fire map object.
     * @param worldCoordX world coordinate X
     * @param worldCoordY world coordinate Y
     * @param idlePictureID idle picture ID
     * @param activePictureID active picture ID
     * @param extinguishedPictureID extinguished picture ID
     * @param activationItemID activation item ID
     */
    protected Fire(@JsonProperty("x")int worldCoordX,
                    @JsonProperty("y") int worldCoordY,
                    @JsonProperty("idle_picture") String idlePictureID,
                    @JsonProperty("active_picture") String activePictureID,
                    @JsonProperty("extinguished_picture") String extinguishedPictureID,
                    @JsonProperty("activation_item") String activationItemID) {
        super(worldCoordX, worldCoordY, idlePictureID, activePictureID, activationItemID);
        this.extinguishedPictureID = extinguishedPictureID;
        this.extinguishedImage = loadImage(extinguishedPictureID);
        this.hitbox = new Hitbox(this,  this.idleImage.getWidth() /2, (int) (this.idleImage.getHeight()/1.3), this.idleImage.getWidth()/4,this.idleImage.getHeight()/4);
        log.debug("Fire hitbox dimensions: {}x{}, position x: {}, y: {}", hitbox.getWidth(), hitbox.getHeight(), this.hitbox.getCoordX(), this.hitbox.getCoordY());
        activated = true;
        dealingDamage = true;
        this.damageDealer = new DamageDealer(FIRE_DAMAGE, FIRE_KNOCKBACK);
    }

    @Override
    protected void reactToActivation(){
        activated = false;
        log.info("Fire extinguished");
        this.hitbox = new Hitbox(this, 0,0,0,0);
        this.currentImage = extinguishedImage;
    }

    @Override
    public void render(GraphicsContext gc, GamePanel gamePanel) {
        super.render(gc, gamePanel);
        if (activated){
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastChangeStateTime > 400){
                lastChangeStateTime = currentTime;
                this.currentImage = currentImage == idleImage ? activeImage : idleImage;
            }
        }
    }
}
