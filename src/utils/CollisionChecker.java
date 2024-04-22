package utils;

import entity.Character;
import gameloop.GamePanel;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CollisionChecker {
    //inspired by https://www.youtube.com/watch?v=oPzPpUcDiYY
    GamePanel gamePanel;
    public CollisionChecker(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    public void checkTile(Character entity){
        // Check if the entity is colliding with a tile
        int entityLeftX = entity.hitbox.getCoordX();
        int entityRightX = entity.hitbox.getCoordX() + entity.hitbox.getWidth();
        int entityTopY =  entity.hitbox.getCoordY();
        int entityBottomY = entity.hitbox.getCoordY() + entity.hitbox.getHeight();

        int entityLeftCol = entityLeftX / GamePanel.TILE_SIZE;
        int entityRightCol = entityRightX / GamePanel.TILE_SIZE;
        int entityTopRow = entityTopY / GamePanel.TILE_SIZE;
        int entityBottomRow = entityBottomY / GamePanel.TILE_SIZE;

        int tileNum1;
        int tileNum2;

        switch (entity.getDirection()){
            case "up":
                entityTopRow = (entityTopY - GamePanel.TILE_SIZE) / GamePanel.TILE_SIZE;
                if (entityTopRow < 0){
                    entity.collision = true;
                    break;
                }
                log.debug("entityTopRow: {}", entityTopRow);
                tileNum1 = gamePanel.getChosenMap().getMap()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getChosenMap().getMap()[entityRightCol][entityTopRow];
                if (gamePanel.getChosenMap().getTiles()[tileNum1].isCollision() ||
                    gamePanel.getChosenMap().getTiles()[tileNum2].isCollision()){
                    entity.collision = true;
                    log.debug("Collision detected with tile type: {}", gamePanel.getChosenMap().getTiles()[tileNum1].getType());
                }
                break;
            case "down":
                entityBottomRow = (entityBottomY + entity.getSpeed()) / GamePanel.TILE_SIZE;
                log.debug("entityBottomRow: {}", entityBottomRow);
                if (entityBottomRow >= gamePanel.getChosenMap().getMapHeight()){
                    entity.collision = true;
                    break;
                }
                tileNum1 = gamePanel.getChosenMap().getMap()[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.getChosenMap().getMap()[entityRightCol][entityBottomRow];
                if (gamePanel.getChosenMap().getTiles()[tileNum1].isCollision() ||
                    gamePanel.getChosenMap().getTiles()[tileNum2].isCollision()){
                    entity.collision = true;
                    log.debug("Collision detected with tile type: {}", gamePanel.getChosenMap().getTiles()[tileNum1].getType());
                }
                break;
            case "left":
                entityLeftCol = (entityLeftX - GamePanel.TILE_SIZE) / GamePanel.TILE_SIZE;
                log.debug("entityLeftCol: {}", entityLeftCol);
                if (entityLeftCol < 0 ){
                    entity.collision = true;
                    break;
                }
                tileNum1 = gamePanel.getChosenMap().getMap()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getChosenMap().getMap()[entityLeftCol][entityBottomRow];
                if (gamePanel.getChosenMap().getTiles()[tileNum1].isCollision() || gamePanel.getChosenMap().getTiles()[tileNum2].isCollision()){
                    entity.collision = true;
                    log.debug("Collision detected with tile type: {}", gamePanel.getChosenMap().getTiles()[tileNum1].getType());
                }

                break;
            case "right":
                entityRightCol = (entityRightX + entity.getSpeed()) / GamePanel.TILE_SIZE;
                log.debug("entityRightCol: {}", entityRightCol);
                if (entityRightCol >= gamePanel.getChosenMap().getMapWidth() ){
                    entity.collision = true;
                    break;
                }
                tileNum1 = gamePanel.getChosenMap().getMap()[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.getChosenMap().getMap()[entityRightCol][entityBottomRow];
                if (gamePanel.getChosenMap().getTiles()[tileNum1].isCollision() || gamePanel.getChosenMap().getTiles()[tileNum2].isCollision()){
                    entity.collision = true;
                    log.debug("Collision detected with tile type: {}", gamePanel.getChosenMap().getTiles()[tileNum1].getType());
                    log.debug("Player position in map array: x: {}, y: {}", (entity.getWorldCoordX() / GamePanel.TILE_SIZE) - 1, (entity.getWorldCoordY()/GamePanel.TILE_SIZE) -1 );
                }

                break;
            default:
                break;
        }
    }
}
