package cz.cvut.fel.pjv.inventory;

import cz.cvut.fel.pjv.item.Item;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ItemInfo extends Pane {
    private Item item;
    private Rectangle base;
    private Pane root;
    private Text itemName;
    private Text itemDescription;
    private Text itemStats;
    public ItemInfo(ItemSlot itemSlot, Pane root) {
        this.item = itemSlot.getItem();
        this.root = root;
    }
    public void showInfo() {
        base = new Rectangle(
                100,
                100
        );
        int textSize = 20;
        base.setLayoutX(this.root.getLayoutX()+20);
        base.setLayoutY(this.root.getLayoutY()-40);
        itemName = new Text(item.getName());
        itemName.setLayoutX(this.root.getLayoutX()+20);
        itemName.setLayoutY(this.root.getLayoutY()-40);
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/rubber-biscuit/RUBBBB__.TTF"), textSize);
        itemName.setFont(font);
        itemDescription = new Text(item.getDescription());
        itemDescription.setLayoutX(this.root.getLayoutX()+20);
        itemDescription.setLayoutY(this.root.getLayoutY()-60);
        itemDescription.setFont(font);
        getChildren().addAll(base, itemName, itemDescription);
    }

}
