package handling;

import gameloop.GamePanel;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;
import entity.Character;

import static org.junit.jupiter.api.Assertions.*;

class EntitySetterTest {

    @Test
    void setEntities() {
        EntitySetter entitySetter = new EntitySetter("01", false);
        Optional<List<Character>> characters = entitySetter.setEntities();
        assertTrue(characters.isPresent(), "The characters list should not be empty");
    }
}