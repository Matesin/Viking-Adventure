package cz.cvut.fel.pjv.map_object;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fel.pjv.entity.Character;
import cz.cvut.fel.pjv.entity.DamageDealer;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import cz.cvut.fel.pjv.item.Item;
import javafx.scene.canvas.GraphicsContext;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.html.parser.Entity;

@Slf4j
public class Fire extends ActiveMapObject{
    private long lastChangeStateTime = 0;
    protected Fire(@JsonProperty("x")int worldCoordX,
                    @JsonProperty("y") int worldCoordY,
                    @JsonProperty("idle_picture") String idlePictureID,
                    @JsonProperty("active_picture") String activePictureID,
                    @JsonProperty("activation_item") Item activationItem) {
        super(worldCoordX, worldCoordY, idlePictureID, activePictureID, activationItem);
        activated = true;
        dealingDamage = true;
        this.damageDealer = new DamageDealer(1, 10);
    }

    @Override
    void activate() {
//        TODO
    }
    @Override
    public void changeState(Item usedItem){
        // Change the state of the object
        if (usedItem == activationItem || activationItem == null){
            // Change the state of the object
//            this.activate()
            activated = false; //extinguish the fire permanently
            log.debug("Object {}", (isActivated() ? " activated" : " deactivated"));
            this.currentImage = activated ? activeImage : idleImage;
        }
    }

    @Override
    public void render(GraphicsContext gc, GamePanel gamePanel) {
        super.render(gc, gamePanel);
        if (activated){
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastChangeStateTime > 400){
                lastChangeStateTime = currentTime;
                currentImage = currentImage == idleImage ? activeImage : idleImage;
            }
        }
    }
}
