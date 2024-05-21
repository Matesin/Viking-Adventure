package cz.cvut.fel.pjv.map_object;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fel.pjv.entity.DamageDealer;
import cz.cvut.fel.pjv.entity.Hitbox;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;

import static cz.cvut.fel.pjv.gameloop.Constants.Fire.*;

@Slf4j
public class Fire extends ActiveMapObject{
    private long lastChangeStateTime = 0;
    private Image extinguishedImage;
    protected Fire(@JsonProperty("x")int worldCoordX,
                    @JsonProperty("y") int worldCoordY,
                    @JsonProperty("idle_picture") String idlePictureID,
                    @JsonProperty("active_picture") String activePictureID,
                    @JsonProperty("extinguished_picture") String extinguishedPictureID,
                    @JsonProperty("activation_item") String activationItemID) {
        super(worldCoordX, worldCoordY, idlePictureID, activePictureID, activationItemID);
        this.extinguishedImage = loadImage(extinguishedPictureID);
        this.hitbox = new Hitbox(this, (int) this.idleImage.getWidth()/2, (int) (this.idleImage.getHeight()/1.3), (int) this.idleImage.getWidth()/4,(int) this.idleImage.getHeight()/4);
        log.debug("Fire hitbox dimensions: {}x{}, position x: {}, y: {}", hitbox.getWidth(), hitbox.getHeight(), this.hitbox.getCoordX(), this.hitbox.getCoordY());
        activated = true;
        dealingDamage = true;
        this.damageDealer = new DamageDealer(FIRE_DAMAGE, FIRE_KNOCKBACK);
    }

    @Override
    protected void reactToActivation(){
        activated = false;
        log.debug("Fire extinguished");
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
