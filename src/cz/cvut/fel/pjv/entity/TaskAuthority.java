package cz.cvut.fel.pjv.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import cz.cvut.fel.pjv.inventory.Inventory;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Abstract class representing a task authority non-player character (NPC) in the game.
 * This class extends the RespondingNPC class and can be extended by specific types of task authority NPCs.
 */
@Slf4j
public abstract class TaskAuthority extends RespondingNPC{
    int currentDialogueID = 0;
//    @JsonSetter("dialogues")
//    public void setDialogues(Map<Integer, String> dialogues) {
//        this.dialogues = Optional.of(dialogues);
//        dialogueThresholds = dialogues.keySet().stream().mapToInt(i -> i).toArray();
//    }

//    Optional<Map<Integer, String>> dialogues;
    int[] dialogueThresholds;
    int fulfillmentState = 0;
    Optional<List<String>> interactableItems;
    /**
     * Constructor for TaskAuthority with specified world coordinates.
     *
     * @param worldCoordX the x-coordinate of the NPC in the world
     * @param worldCoordY the y-coordinate of the NPC in the world
     */
    protected TaskAuthority(int worldCoordX, int worldCoordY) {
        super(worldCoordX, worldCoordY);
//        getDialoguesForJson();
//        if (dialogues.isPresent()) {
//            dialogues.ifPresent(integerStringMap ->
//                    dialogueThresholds = integerStringMap.keySet().stream().mapToInt(i -> i).toArray());
//        }
    }
//    public void respondToPlayer(){
//        if (dialogues.isPresent() && dialogueThresholds != null){
//            for (int i = currentDialogueID; i < dialogueThresholds[fulfillmentState]; i++) {
//                log.info("NPC {} says: {}", this, dialogues.get().get(i));
//                currentDialogueID++;
//            }
//        } else {
//            log.info("Player tried to interact with NPC {} which has no dialogues.", this);
//        }
//    }

//    public void interact(Inventory inventory){
//        if (interactableItems.isPresent() && inventory.getPickedItem().getClass().getSimpleName().equals(interactableItems.get().getFirst())){
//            inventory.removeItem(inventory.getPickedItem());
//            fulfillmentState++;
//        }
//    }


//    @JsonGetter("dialogues")
//    public List<Map<String, String>> getDialoguesForJson() {
//        if (dialogues.isPresent()) {
//            List<Map<String, String>> dialoguesForJson = new ArrayList<>();
//            for (Map.Entry<Integer, String> entry : dialogues.get().entrySet()) {
//                dialoguesForJson.add(Collections.singletonMap(entry.getKey().toString(), entry.getValue()));
//            }
//            return dialoguesForJson;
//        }
//        return Collections.emptyList();
//    }
}