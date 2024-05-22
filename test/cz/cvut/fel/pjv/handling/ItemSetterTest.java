package cz.cvut.fel.pjv.handling;

import cz.cvut.fel.pjv.item.Item;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ItemSetterTest {

    @Test
    void setItems() {
        ItemSetter itemSetter = new ItemSetter("01", false);
        Optional<List<Item>> items = itemSetter.setItems();
        assertTrue(items.isPresent(), "The items list should not be empty");
     }
}