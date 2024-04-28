package handling;

import entity.Character;
import gameloop.GamePanel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class EntitySetter {
    private final JsonEntityHandler jsonEntityHandler = new JsonEntityHandler();
    private final String filepath;

    public EntitySetter(GamePanel gamePanel, int save){
        this.filepath = save == -1 ? "res/maps/map" /*+ gamePanel.getMapID()*/ + "/ent.json" : "saves/save" + save + "/ent.json";
    }

    public Optional<List<Character>> setEntities(){
        Optional<List<Character>> characters = Optional.empty();
        try {
             characters = jsonEntityHandler.deserializeCharactersFromFile(filepath);
        } catch (Exception e) {
            log.error("Error setting entities", e);
        }
        return characters;
    }

}