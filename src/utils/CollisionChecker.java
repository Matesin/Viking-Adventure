package utils;

import entity.Character;
import gameloop.GamePanel;
import lombok.extern.slf4j.Slf4j;

import static gameloop.Constants.Screen.*;
import static gameloop.Constants.Tile.*;

@Slf4j
public class CollisionChecker {
    //inspired by https://www.youtube.com/watch?v=oPzPpUcDiYY
    GamePanel gamePanel;
    public CollisionChecker(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    int tileNum1;
    int tileNum2;
    public void checkTile(Character entity){
        // Check if the entity is colliding with a tile
        int entityLeftX = entity.getHitbox().getCoordX();
        int entityRightX = entity.getHitbox().getCoordX() + entity.getHitbox().getWidth();
        int entityTopY =  entity.getHitbox().getCoordY();
        int entityBottomY = entity.getHitbox().getCoordY() + entity.getHitbox().getHeight();

        int entityLeftCol = entityLeftX / TILE_SIZE;
        int entityRightCol = entityRightX / TILE_SIZE;
        int entityTopRow = entityTopY / TILE_SIZE;
        int entityBottomRow = entityBottomY / TILE_SIZE;

        switch (entity.getDirection()){
            case "up":
                entityTopRow = (entityTopY - entity.getSpeed()) / TILE_SIZE;
                if (entityTopY - entity.getSpeed() < 0){
                    entity.setCollision(true);
                    return;
                }
                tileNum1 = gamePanel.getChosenMap().getMap()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getChosenMap().getMap()[entityRightCol][entityTopRow];
                if (gamePanel.getChosenMap().getTiles()[tileNum1].isCollision() ||
                    gamePanel.getChosenMap().getTiles()[tileNum2].isCollision()){
                    entity.setCollision(true);}
                break;
            case "down":
                entityBottomRow = (entityBottomY + entity.getSpeed()) / TILE_SIZE;
                if (entityBottomRow >= gamePanel.getChosenMap().getMapHeight()){
                    entity.setCollision(true);
                    break;
                }
                tileNum1 = gamePanel.getChosenMap().getMap()[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.getChosenMap().getMap()[entityRightCol][entityBottomRow];
                if (gamePanel.getChosenMap().getTiles()[tileNum1].isCollision() ||
                    gamePanel.getChosenMap().getTiles()[tileNum2].isCollision()){
                    entity.setCollision(true);
                }
                break;
            case "left":
                entityLeftCol = (entityLeftX - entity.getSpeed()) / TILE_SIZE;
                if (entityLeftX - entity.getSpeed() < 0 ){
                    entity.setCollision(true);
                    break;
                }
                tileNum1 = gamePanel.getChosenMap().getMap()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getChosenMap().getMap()[entityLeftCol][entityBottomRow];
                if (gamePanel.getChosenMap().getTiles()[tileNum1].isCollision() || gamePanel.getChosenMap().getTiles()[tileNum2].isCollision()){
                    entity.setCollision(true);
                }

                break;
            case "right":
                entityRightCol = (entityRightX + entity.getSpeed()) / TILE_SIZE;
                if (entityRightCol >= gamePanel.getChosenMap().getMapWidth() ){
                    entity.setCollision(true);
                    break;
                }
                tileNum1 = gamePanel.getChosenMap().getMap()[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.getChosenMap().getMap()[entityRightCol][entityBottomRow];
                if (gamePanel.getChosenMap().getTiles()[tileNum1].isCollision() || gamePanel.getChosenMap().getTiles()[tileNum2].isCollision()){
                    entity.setCollision(true);
                }
                break;
            default:
                break;
        }
    }
}
