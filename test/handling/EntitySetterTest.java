package handling;

import cz.cvut.fel.pjv.handling.EntitySetter;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;
import cz.cvut.fel.pjv.entity.Character;

import static org.junit.jupiter.api.Assertions.*;

class EntitySetterTest {

    @Test
    void setEntities() {
        EntitySetter entitySetter = new EntitySetter("01", false);
        Optional<List<Character>> characters = entitySetter.setEntities();
        assertTrue(characters.isPresent(), "The characters list should not be empty");
    }
}