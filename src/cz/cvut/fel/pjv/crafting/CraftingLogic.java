package cz.cvut.fel.pjv.crafting;

import cz.cvut.fel.pjv.inventory.Inventory;
import cz.cvut.fel.pjv.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CraftingLogic {
    private Inventory playerInventory;
    private List<Item> rewardItems = new ArrayList<>();

    public CraftingLogic(Inventory inventory) {
        /*
         * This class is responsible for creating the crafting logic and storing its values.
         * */
        this.playerInventory = inventory;
    }
    private void initLogic(){
        /*
         * This method initializes the crafting logic.
         */
        this.rewardItems = Arrays.asList(
                //TODO implement items that can be crafted
        );
    }
}
