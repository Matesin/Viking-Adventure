package entity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

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
    @Getter
    @Setter
    int hp; // will differ by character type
    @Getter
    int worldCoordX;
    @Getter
    int worldCoordY;
    @Getter
    @Setter
    private int screenCoordX;
    @Getter
    @Setter
    private int screenCoordY;
    @Getter
    String direction = "down";
    @Getter
    @Setter
    int speed;
    int width;
    int height;
    @Getter
    Hitbox hitbox;
    @Getter
    @Setter
    boolean collision = false;
    @Getter
    @Setter
    private boolean isMoving = false;
    Image up1;
    Image up2;
    Image down1;
    Image down2;
    Image left1;
    Image left2;
    Image right1;
    Image right2;
    protected Character(int worldCoordX, int worldCoordY) {
        this.worldCoordX = worldCoordX;
        this.worldCoordY = worldCoordY;
    }

    public void update(){
        // Update the character's position
        if (isMoving) move();
    }
    public void render(GraphicsContext gc){
        // Render the character

    }
    private void move(){
        // Move the character
        switch (direction){
            case "up":
                worldCoordY -= speed;
                break;
            case "down":
                worldCoordY += speed;
                break;
            case "left":
                worldCoordX -= speed;
                break;
            case "right":
                worldCoordX += speed;
                break;
            default:
                break;
        }
    }
}
