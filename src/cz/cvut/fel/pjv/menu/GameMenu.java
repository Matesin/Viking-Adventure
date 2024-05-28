package cz.cvut.fel.pjv.menu;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

/**
 * Interface for game menu.
 */
public interface GameMenu {

    /**
     * Method to initialize buttons.
     */
    void initButtons();

    /**
     * Method to add menu.
     */
    void addMenu();

    /**
     * Method to set background.
     */
    void setBackGround() throws URISyntaxException, FileNotFoundException;
    /**
     * Method to add title.
     */
    void addTitle();
}
