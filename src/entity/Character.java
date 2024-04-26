package entity;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;


import java.io.IOException;

//DECLARE JSON SUBTYPES
@JsonTypeInfo(  use = JsonTypeInfo.Id.NAME,
                include = JsonTypeInfo.As.PROPERTY,
                property = "type")

@JsonSubTypes({
        @JsonSubTypes.Type(value = TaskVillager.class, name = "task_villager"),
        @JsonSubTypes.Type(value = Mob.class, name = "mob"),
        @JsonSubTypes.Type(value = Warden.class, name = "warden"),
})
public abstract class Character {
    public int HP;
    @Getter
    int worldCoordX;
    @Getter
    int worldCoordY;
    @Getter
    @Setter
    int screenCoordX;
    @Getter
    @Setter
    int screenCoordY;
    @Getter
    String direction;
    @Getter
    int speed;
    int width;
    int height;
    public int spriteID;
    @Getter
    Hitbox hitbox;
    @Getter
    @Setter
    boolean collision;
    Image up1;
    Image up2;
    Image down1;
    Image down2;
    Image left1;
    Image left2;
    Image right1;
    Image right2;
    public void update(){
        // Update the character's position
    }
    public void render(GraphicsContext gc){
        // Render the character
    }
}
