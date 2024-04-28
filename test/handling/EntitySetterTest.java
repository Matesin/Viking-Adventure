package handling;

import entity.Character;
import gameloop.GamePanel;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EntitySetterTest {

    @Test
    void setEntities() {
        // Arrange

        EntitySetter entitySetter = new EntitySetter(new GamePanel().getSave(), new GamePanel().getMapID());

        // Act
        Optional<List<Character>> result = entitySetter.setEntities();

        // Assert
        assertTrue(result.isPresent(), "Result should be present");
        assertFalse(result.get().isEmpty(), "Result list should not be empty");
        // Add more assertions here based on what you expect the result data to be
    }
}