package handling;

import entity.Character;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EntitySetterTest {

    @Test
    void setEntities() {
        // Arrange
        String save = "save.json";
        String mapID = "02";
        EntitySetter entitySetter = new EntitySetter(save, mapID);

        // Act
        Optional<List<Character>> result = entitySetter.setEntities();

        // Assert
        assertTrue(result.isPresent(), "Result should be present");
        assertFalse(result.get().isEmpty(), "Result list should not be empty");
        // Add more assertions here based on what you expect the result data to be
    }
}