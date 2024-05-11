package cz.cvut.fel.pjv.menu;

import javafx.scene.Parent;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public interface GameMenu {
    void initButtons();
    void addMenu();
    void setBackGround() throws URISyntaxException, FileNotFoundException;
    void addTitle();
}
