package cz.cvut.fel.pjv.gameloop;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import static cz.cvut.fel.pjv.gameloop.Constants.Inventory.SLOT_SIZE;
import static cz.cvut.fel.pjv.gameloop.Constants.Screen.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Tile.TILE_SIZE;
/**
 * Class containing all the constants used in the game.
 * This class is not meant to be instantiated.
 */
@Slf4j
public class Constants {

    private static final Marker CONSTANT_CREATION = MarkerFactory.getMarker("ILLEGAL CONSTANT CREATION");

    /**
     * Private constructor to prevent instantiation.
     */

    private Constants() {
        log.error(CONSTANT_CREATION, "Utility class created!");
    }
    public static class Tile {
        private Tile() {
            log.error(CONSTANT_CREATION, "Utility Tile class created!");
        }
        public static final int INITIAL_TILE_SIZE = 32;
        public static final int SCALE = 2;
        public static final int TILE_SIZE = INITIAL_TILE_SIZE * SCALE;
    }
    public static class Screen {
        private Screen() {
            log.error(CONSTANT_CREATION, "Utility Screen class created!");
        }
        public static final int SCREEN_COLS = 16;
        public static final int SCREEN_ROWS = 12;
        public static final int SCREEN_WIDTH = TILE_SIZE * SCREEN_COLS;
        public static final int SCREEN_HEIGHT = TILE_SIZE * SCREEN_ROWS;
        public static final int SCREEN_MIDDLE_X = SCREEN_WIDTH / 2;
        public static final int SCREEN_MIDDLE_Y = SCREEN_HEIGHT / 2;
    }
    public static class Game {
        private Game() {
            log.error(CONSTANT_CREATION, "Utility Game class created!");
        }
        public static final int FPS = 60;
        public static final int FRAME_TIME_MILLIS = 1_000_000_000 / FPS;
    }
    public static class Button {
        private Button() {
            log.error(CONSTANT_CREATION, "Utility Button class created");
        }
        public static final int BUTTON_WIDTH = 200;
        public static final int BUTTON_HEIGHT = 80;
    }
    public static class MenuLayout {
        private MenuLayout() {
            log.error(CONSTANT_CREATION, "Utility MenuLayout class created");
        }
        public static final int MENU_BUTTONS_X = SCREEN_MIDDLE_X - Button.BUTTON_WIDTH / 2;
        public static final int MENU_BUTTONS_Y = SCREEN_MIDDLE_Y / 2;
        public static final int MENU_BUTTONS_SPACING = 30;
        public static final int MENU_TITLE_X = SCREEN_MIDDLE_X / 3;
        public static final int MENU_TITLE_Y = SCREEN_HEIGHT / 7;
        public static final int MENU_TRANSLATE_X = SCREEN_MIDDLE_X - Button.BUTTON_WIDTH / 2;
        public static final int MENU_TRANSLATE_Y = SCREEN_MIDDLE_Y / 2;
    }
    public static class Directions {
        private Directions() {
            log.error(CONSTANT_CREATION, "Utility Directions class created");
        }
        public static final String DIR_UP = "up";
        public static final String DIR_DOWN = "down";
        public static final String DIR_LEFT = "left";
        public static final String DIR_RIGHT = "right";
    }
    public static class GraphicsDefaults {
        private GraphicsDefaults() {
            log.error(CONSTANT_CREATION, "Utility GraphicsDefaults class created");
        }
        public static final String DEFAULT_TILE_FILEPATH = "res/defaults/default_tile.png";
        public static final String DEFAULT_ITEM_FILEPATH = "res/defaults/default_item.png";
        public static final String DEFAULT_ENTITY_FILEPATH = "res/defaults/default_entity.png";
    }
    public static class Inventory{
        private Inventory() {
            log.error(CONSTANT_CREATION, "Utility Inventory class created");
        }
        //TEMPORARY FX
        public static final int INITIAL_INVENTORY_CAPACITY = 5;
        public static final double SLOT_SIZE = SCREEN_WIDTH / 10.0;
        public static final double SLOT_PADDING = SLOT_SIZE / 5.0;
        public static final double SCREEN_SLOT_SIZE = SCREEN_WIDTH / 15.0;
        public static final double SCREEN_SLOT_PADDING = SCREEN_SLOT_SIZE / 5.0;
        public static final double INVENTORY_WIDTH = (SLOT_SIZE + SLOT_PADDING) * INITIAL_INVENTORY_CAPACITY + SLOT_PADDING;
        public static final double INVENTORY_HEIGHT = SLOT_SIZE + 2 * SLOT_PADDING;
        public static final double INVENTORY_X = (SCREEN_WIDTH - INVENTORY_WIDTH) / 2;
        public static final double INVENTORY_Y = (SCREEN_HEIGHT - INVENTORY_HEIGHT) / 2;
        public static final double FIRST_SLOT_X = INVENTORY_X + SLOT_PADDING;
        public static final double FIRST_SLOT_Y = INVENTORY_Y + SLOT_PADDING;
        public static final double SCREEN_INVENTORY_WIDTH = SCREEN_SLOT_SIZE + 2 * SCREEN_SLOT_PADDING;
        public static final double SCREEN_INVENTORY_HEIGHT = SCREEN_SLOT_SIZE + 2 * SCREEN_SLOT_PADDING;
        public static final double SCREEN_INVENTORY_X = (SCREEN_WIDTH - SCREEN_INVENTORY_WIDTH - SCREEN_SLOT_PADDING);
        public static final double SCREEN_INVENTORY_Y = SCREEN_HEIGHT - SCREEN_INVENTORY_HEIGHT - SCREEN_SLOT_PADDING;
        public static final double FIRST_SCREEN_SLOT_X = SCREEN_INVENTORY_X + SCREEN_SLOT_PADDING;
        public static final double FIRST_SCREEN_SLOT_Y = SCREEN_INVENTORY_Y + SCREEN_SLOT_PADDING;
    }
    public static class Player{
        private Player() {
            log.error(CONSTANT_CREATION, "Utility Player class created");
        }
        public static final int INITIAL_SPEED = 5;
        public static final long CHANGE_STATE_COOLDOWN = 50; // cooldown of mapObject state change
        public static final int SPEED_CONSTANT = 1000;
    }
    public static class Fire{
        private Fire() {
            log.error(CONSTANT_CREATION, "Utility Fire class created");
        }
        public static final int FIRE_DAMAGE = 1;
        public static final int FIRE_KNOCKBACK = 30;
    }
    public static class Crafting {
        private Crafting() {
            log.error(CONSTANT_CREATION, "Utility Crafting class created");
        }
        public static final int CRAFTING_GRID_ROWS = 2;
        public static final int CRAFTING_GRID_COLS = 2;
        public static final double CRAFTING_GRID_X = SCREEN_WIDTH / 5.0 - 75;
        public static final double CRAFTING_GRID_Y = SCREEN_HEIGHT / 4.0;
        public static final double CRAFTING_SLOT_SIZE = SLOT_SIZE * 1.8;
        public static final double CRAFTING_SLOT_PADDING = 20;
        public static final double RESULT_SLOT_Y = CRAFTING_GRID_Y + CRAFTING_SLOT_SIZE / 2 + CRAFTING_SLOT_PADDING / 2;
        public static final double CRAFTING_ARROW_SIZE = 150;
        public static final double CRAFTING_ARROW_X = CRAFTING_GRID_X + (CRAFTING_SLOT_SIZE + CRAFTING_SLOT_PADDING) * CRAFTING_GRID_COLS;
        public static final double CRAFTING_ARROW_Y = CRAFTING_GRID_Y + (CRAFTING_SLOT_SIZE + CRAFTING_SLOT_PADDING / 2) * CRAFTING_GRID_ROWS / 2 - CRAFTING_ARROW_SIZE / 2;
        public static final double RESULT_SLOT_X = CRAFTING_GRID_X + (CRAFTING_SLOT_SIZE + CRAFTING_SLOT_PADDING) * CRAFTING_GRID_COLS + CRAFTING_ARROW_SIZE + CRAFTING_SLOT_PADDING;
        public static final int RESULT_TEXT_SLEEP = 3000;
        public static final int RESULT_TEXT_SIZE = 35;
    }
    public static class Dialogues {
        private Dialogues() {
            log.error(CONSTANT_CREATION, "Utility Dialogues class created");
        }
        public static final int DIALOGUE_TEXT_SIZE = 20;
        public static final int DIALOGUE_TEXT_SLEEP = 4000;
    }
}
