package cz.cvut.fel.pjv.gameloop;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import static cz.cvut.fel.pjv.gameloop.Constants.Screen.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Tile.TILE_SIZE;

@Slf4j
public class Constants {
    private static final Marker CONSTANT_CREATION = MarkerFactory.getMarker("ILLEGAL CONSTANT CREATION");
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
        public static final int INITIAL_INVENTORY_CAPACITY = 5;
        public static final double INVENTORY_WIDTH = SCREEN_WIDTH * 0.8;
        public static final double INVENTORY_HEIGHT = SCREEN_HEIGHT * 0.8;
        public static final double INVENTORY_X = (SCREEN_WIDTH - INVENTORY_WIDTH) / 2;
        public static final double INVENTORY_Y = (SCREEN_HEIGHT - INVENTORY_HEIGHT) / 2;
        //TEMPORARY VALUES
        public static final double SLOT_SIZE = INVENTORY_WIDTH / 10;
        public static final double SLOT_PADDING = SLOT_SIZE / 5;
    }
}
