package cz.cvut.fel.pjv.menu;

import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.List;

/**
 * Class representing game settings.
 */
public class GameSettings extends Pane implements GameMenu {
    // CLASS ALLOWING USER TO CHANGE SETTINGS, somehow import all settings possible, to be implemented
    private Scene scene;
    private List <Pair<String, Runnable>> menuButtons;
    public GameSettings() {
        // TODO document why this constructor is empty
    }
    private Slider createSlider() {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(50); // default value
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        return slider;
    }
    @Override
    public void initButtons() {

    }

    @Override
    public void addMenu() {

    }

    @Override
    public void setBackGround() {

    }

    @Override
    public void addTitle() {

    }
}
