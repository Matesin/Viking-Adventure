package gameloop;

import handling.AssetSetter;
import handling.EntitySetter;
import utils.SaveUtility;

public class GameState {
    private final int save;
    private final String mapID;
    private final EntitySetter entitySetter;
    private final AssetSetter assetSetter;
    private final SaveUtility saveUtility;

    public GameState(int save, String mapID, GamePanel gamePanel){
        this.save = save;
        this.mapID = mapID;
        this.entitySetter = new EntitySetter(gamePanel, save);
        this.assetSetter = new AssetSetter(gamePanel);
        this.saveUtility = new SaveUtility();
    }

    public void setGameState(){
        assetSetter.setAssets();
        entitySetter.setEntities();
    }

    public void saveGame(){
        saveUtility.saveGame();
    }

    public void loadGame(){
        saveUtility.loadGame();
    }
}
