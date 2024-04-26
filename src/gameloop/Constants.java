package gameloop;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import static gameloop.Constants.Screen.*;
import static gameloop.Constants.Tile.TILE_SIZE;

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
        public static final int MENU_TITLE_Y = SCREEN_HEIGHT / 4;
    }

}
