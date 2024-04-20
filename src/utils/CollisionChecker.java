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
        int entityLeftX = (int) (entity.getWorldCoordX() + entity.hitbox.getX());
        int entityRightX = (int) (entity.getWorldCoordX() + entity.hitbox.getX() + entity.hitbox.getWidth());
        int entityTopY = (int) (entity.getWorldCoordY() + entity.hitbox.getY());
        int entityBottomY = (int) (entity.getWorldCoordY() + entity.hitbox.getY() + entity.hitbox.getHeight());

        int entityLeftCol = entityLeftX / GamePanel.TILE_SIZE;
        int entityRightCol = entityRightX / GamePanel.TILE_SIZE;
        int entityTopRow = entityTopY / GamePanel.TILE_SIZE;
        int entityBottomRow = entityBottomY / GamePanel.TILE_SIZE;

        int tileNum1, tileNum2;

        switch (entity.getDirection()){
            case "up":
                entityTopRow = (entityTopY - entity.getSpeed()) / GamePanel.TILE_SIZE;
                if (entityTopRow < 0){
                    entity.collision = true;
                    break;
                }
                log.debug("entityTopRow: {}", entityTopRow);
                tileNum1 = gamePanel.getChosenMap().getMap()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getChosenMap().getMap()[entityRightCol][entityTopRow];
                if (gamePanel.getChosenMap().getTiles()[tileNum1].isCollision() || gamePanel.getChosenMap().getTiles()[tileNum2].isCollision()){
                    entity.collision = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomY +  entity.getSpeed()) / GamePanel.TILE_SIZE;
                if (entityBottomRow >= gamePanel.getChosenMap().getMapHeight()){
                    entity.collision = true;
                    break;
                }
                tileNum1 = gamePanel.getChosenMap().getMap()[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.getChosenMap().getMap()[entityRightCol][entityBottomRow];
                if (gamePanel.getChosenMap().getTiles()[tileNum1].isCollision() ||
                    gamePanel.getChosenMap().getTiles()[tileNum2].isCollision()){
                    entity.collision = true;
                }
                break;
            case "left":
                entityRightCol = (entityRightX - entity.getSpeed()) / GamePanel.TILE_SIZE;
                if (entityRightCol < 0 ){
                    entity.collision = true;
                    break;
                }
                tileNum1 = gamePanel.getChosenMap().getMap()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getChosenMap().getMap()[entityRightCol][entityBottomRow];
                if (gamePanel.getChosenMap().getTiles()[tileNum1].isCollision() || gamePanel.getChosenMap().getTiles()[tileNum2].isCollision()){
                    entity.collision = true;
                }

                break;
            case "right":
                entityLeftCol = (entityLeftX + entity.getSpeed()) / GamePanel.TILE_SIZE;
                log.debug("entityLeftCol: {}", entityLeftCol);
                if (entityLeftCol >= gamePanel.getChosenMap().getMapWidth() ){
                    entity.collision = true;
                    break;
                }
                tileNum1 = gamePanel.getChosenMap().getMap()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getChosenMap().getMap()[entityRightCol][entityBottomRow];
                if (gamePanel.getChosenMap().getTiles()[tileNum1].isCollision() || gamePanel.getChosenMap().getTiles()[tileNum2].isCollision()){
                    entity.collision = true;
                }

                break;
            default:
                break;
        }

    }
}
