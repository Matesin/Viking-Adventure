package handling;

import cz.cvut.fel.pjv.entity.Character;
import cz.cvut.fel.pjv.handling.JsonEntityHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JsonEntityHandlerTest {

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void serializeCharactersToFile() {
        // Arrange
        JsonEntityHandler jsonEntityHandler = new JsonEntityHandler();
        List<Character> characters = Arrays.asList();
        String mapID = "02";
        String save = "save" + mapID;
        // Act
        try {
            jsonEntityHandler.serializeCharactersToFile(characters, save);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }

        // Assert
        // Add assertions here based on what you expect the result data to be
    }

    @org.junit.jupiter.api.Test
    void deserializeCharactersFromFile() {
        // Arrange
        JsonEntityHandler jsonEntityHandler = new JsonEntityHandler();
        String save = "save";
        String mapID = ".json";

        // Act
        Optional<List<Character>> result = Optional.empty();
        try {
            result = jsonEntityHandler.deserializeCharactersFromFile(save + mapID);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }

        // Assert
        assertTrue(result.isPresent(), "Result should be present");
        assertFalse(result.get().isEmpty(), "Result list should not be empty");
        // Add more assertions here based on what you expect the result data to be
    }
}