package cz.cvut.fel.pjv.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fel.pjv.item.Item;

import java.util.List;

/**
 * Class representing a task villager non-player character (NPC) in the game.
 * This class extends the TaskAuthority class and represents a specific type of task authority NPC.
 */
public class TaskVillager extends TaskAuthority{
    /**
     * Constructor for TaskVillager with specified world coordinates.
     *
     * @param worldCoordX the x-coordinate of the NPC in the world
     * @param worldCoordY the y-coordinate of the NPC in the world
     */
    @JsonCreator
    protected TaskVillager(@JsonProperty("x") int worldCoordX,@JsonProperty("y") int worldCoordY, @JsonProperty("dialogues") List<List<String>> dialogues, @JsonProperty("interactable_items") List<Item> interactableItems, @JsonProperty("reward_items") List<Item> rewardItems) {
        super(worldCoordX, worldCoordY, dialogues, interactableItems, rewardItems);
    }
}