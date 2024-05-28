package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.gameloop.Constants;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import cz.cvut.fel.pjv.inventory.InGameInventoryBar;
import cz.cvut.fel.pjv.inventory.Inventory;
import cz.cvut.fel.pjv.item.Item;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static cz.cvut.fel.pjv.gameloop.Constants.Dialogues.DIALOGUE_TEXT_SIZE;
import static cz.cvut.fel.pjv.gameloop.Constants.Dialogues.DIALOGUE_TEXT_SLEEP;
import static javafx.scene.paint.Color.BLACK;

/**
 * Abstract class representing a task authority non-player character (NPC) in the game.
 * This class extends the RespondingNPC class and can be extended by specific types of task authority NPCs.
 */
@Slf4j
public abstract class TaskAuthority extends RespondingNPC{
    int currentDialogueID = 0;
    List<List<String>> dialogues;
    int dialogueThreshold;
    int fulfillmentState = 0;
    List<Item> requiredItems;
    List<Item> rewardItems;
    Text dialogueText = new Text();
    private boolean isDialogueCompleted = false;
    /**
     * Constructor for TaskAuthority with specified world coordinates.
     *
     * @param worldCoordX the x-coordinate of the NPC in the world
     * @param worldCoordY the y-coordinate of the NPC in the world
     */
    protected TaskAuthority(int worldCoordX, int worldCoordY, List<List<String>> dialogues, List<Item> interactableItems, List<Item> rewardItems) {
        super(worldCoordX, worldCoordY);
        this.dialogues = dialogues;
        if (dialogues != null){
            this.dialogueThreshold = dialogues.size();
            log.debug("Dialogue threshold: {}", dialogueThreshold);
        }
        this.requiredItems = interactableItems;
        this.rewardItems = rewardItems;
    }

    /**
     * This method is used to handle the interaction between the player and the NPC.
     * It checks if the fulfillment state is less than the dialogue threshold, and if so,
     * it sets the dialogue text properties, adds the dialogue text to the game panel,
     * removes the text after a delay, and then calls the interact method.
     *
     * @param gamePanel the game panel where the interaction occurs
     */

    public void respondToPlayer(GamePanel gamePanel){
        if (fulfillmentState < dialogueThreshold){
            if (isDialogueCompleted){
                currentDialogueID = 0;
            }
            String currentDialogue = dialogues.get(fulfillmentState).get(currentDialogueID);
            setTextProperties(currentDialogue);
            log.debug("dialogues size: {}", dialogues.get(fulfillmentState).size());
            removeTextAfterDelay(dialogueText, gamePanel.getRoot());
            if (!gamePanel.getRoot().getChildren().contains(dialogueText)) {
                gamePanel.getRoot().getChildren().add(dialogueText);
            }
            interact(gamePanel);
            currentDialogueID++;
            isDialogueCompleted = currentDialogueID == dialogues.get(fulfillmentState).size();
            log.debug("Current dialogue ID: {}", currentDialogueID);
        }
    }


    /**
     * This method is used to handle the interaction between the player and the NPC.
     * It checks if the required item is null or if the picked item is the required item,
     * and if so, it removes the picked item from the inventory, updates the inventory bar,
     * sets the world coordinates of the reward item, updates the hitbox of the reward item,
     * adds the reward item to the item manager, and increments the fulfillment state.
     *
     * @param gamePanel in order to add text and handle inventory
     */
    public void interact(GamePanel gamePanel){
        Inventory inventory = gamePanel.player.getInventory();
        Item pickedItem = inventory.getPickedItem();
        Item requiredItem = getRequiredItem();
        Item rewardItem = getRewardItem();
        log.debug("Picked item: {}", pickedItem);
        log.debug("Required item: {}", requiredItem);
        log.debug("Reward item: {}", rewardItem);
        log.debug("Fulfillment state: {}", fulfillmentState);
        if (isDialogueCompleted && hasRequiredItem(pickedItem, requiredItem)){
            removeRequiredItem(gamePanel, requiredItem);
            isDialogueCompleted = false;
            updateFulfillmentState();
        }
        if (isDialogueCompleted){
            handleReward(gamePanel, rewardItem);
        }
    }

    private Item getRequiredItem() {

        return fulfillmentState <= requiredItems.size() - 1 ? requiredItems.get(fulfillmentState) : null;
    }

    private Item getRewardItem() {
        return fulfillmentState > 0 && fulfillmentState < rewardItems.size() + 1? rewardItems.get(fulfillmentState - 1) : null;
    }

    private void updateFulfillmentState() {
        fulfillmentState = fulfillmentState == dialogues.size() - 1 ? dialogues.size() - 1 : fulfillmentState + 1;
    }


    private boolean hasRequiredItem(Item pickedItem, Item requiredItem) {
        return pickedItem != null && requiredItem != null && pickedItem.getName().equals(requiredItem.getName());
    }
    private void removeRequiredItem(GamePanel gamePanel, Item requiredItem) {
        Inventory inventory = gamePanel.player.getInventory();
        InGameInventoryBar inGameInventoryBar = gamePanel.getInGameInventoryBar();
        inventory.removeItem(requiredItem);
        inventory.setPickedItem(null);
        inventory.setFull(false);
        inGameInventoryBar.update();
    }

    private void handleReward(GamePanel gamePanel, Item rewardItem) {
        if (rewardItem == null) {
            log.warn("No reward item to drop");
            return;
        }
        rewardItem.setWorldCoordX(this.worldCoordX);
        rewardItem.setWorldCoordY(this.worldCoordY + 50);
        rewardItem.hitbox.update();
        gamePanel.getItemManager().getItems().ifPresent(items -> items.add(rewardItem));
        log.info("Entity dropped item {}", rewardItem.getName());
        updateFulfillmentState();
    }
    /**
     * This method is used to set the properties of the dialogue text.
     * It sets the X and Y, line spacing, font, fill color, and text of the dialogue text.
     *
     * @param dialogue the dialogue to be displayed
     */
    private void setTextProperties(String dialogue){
        dialogueText.setX(this.worldCoordX);
        dialogueText.setY(this.worldCoordY - 50);
        dialogueText.setLineSpacing(10);
        Font font = Font.font("Segoe Script", FontWeight.BOLD, DIALOGUE_TEXT_SIZE);
        dialogueText.setFill(BLACK);
        dialogueText.setFont(font);
        dialogueText.setText(dialogue);
    }
    /**
     * This method is used to remove the dialogue text from the root pane after a delay.
     * It creates a new thread that sleeps for a specified amount of time and then removes the text.
     * If the 'K' key is pressed while the thread is sleeping, the thread is interrupted.
     *
     * @param text the text to be removed
     * @param root the root pane where the text is displayed
     */
    private void removeTextAfterDelay(Text text, Pane root) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(DIALOGUE_TEXT_SLEEP);
            } catch (InterruptedException e) {
                log.info("Text removed before timeout", e);
                Thread.currentThread().interrupt();
            }
            Platform.runLater(() -> root.getChildren().remove(text));
        });
        thread.start();

        root.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.K) {
                thread.interrupt();
            }
        });
    }
}