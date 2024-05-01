package gameloop;

import handling.EntitySetter;
import utils.SaveUtility;

public class GameState {
    private final int save;
    private final String mapID;
    private final EntitySetter entitySetter;
    private final SaveUtility saveUtility;

    public GameState(int save, String mapID, GamePanel gamePanel){
        this.save = save;
        this.mapID = mapID;
        this.entitySetter = new EntitySetter(gamePanel.getMapIDString(), gamePanel.loadSaved);
        this.saveUtility = new SaveUtility();
    }

    public void setGameState(){
        entitySetter.setEntities();
    }

    public void saveGame(){
        saveUtility.saveGame();
    }

    public void loadGame(){
        saveUtility.loadGame();
    }
}
