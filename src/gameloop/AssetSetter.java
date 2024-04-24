package gameloop;

import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.JsonEntityHandler;
import entity.Character;

import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AssetSetter {
    GamePanel gamePanel;
    private final JsonEntityHandler jsonEntityHandler = new JsonEntityHandler();
    public AssetSetter(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    public void setAsset(){
        // Set the assets for the game

    }
}
