package gameloop;

import static gameloop.Constants.Tile.TILE_SIZE;

public class Constants {
    public static class Tile {
        public static final int INITIAL_TILE_SIZE = 32;
        public static final int SCALE = 2;
        public static final int TILE_SIZE = INITIAL_TILE_SIZE * SCALE;
    }
    public static class Screen {
        public static final int SCREEN_COLS = 16;
        public static final int SCREEN_ROWS = 12;
        public static final int SCREEN_WIDTH = TILE_SIZE * SCREEN_COLS;
        public static final int SCREEN_HEIGHT = TILE_SIZE * SCREEN_ROWS;
        public static final int SCREEN_MIDDLE_X = SCREEN_WIDTH / 2;
        public static final int SCREEN_MIDDLE_Y = SCREEN_HEIGHT / 2;
    }
    public static class Game {
        public static final int FPS = 60;
    }

}
