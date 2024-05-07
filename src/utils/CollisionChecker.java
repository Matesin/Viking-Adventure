package utils;

import entity.Character;
import gameloop.GamePanel;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.html.parser.Entity;
import java.util.Collections;

import static gameloop.Constants.Tile.*;
import static gameloop.Constants.Directions.*;

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
        int entityLeftX = entity.hitbox.getCoordX();
        int entityRightX = entity.hitbox.getCoordX() + entity.hitbox.getWidth();
        int entityTopY =  entity.hitbox.getCoordY();
        int entityBottomY = entity.hitbox.getCoordY() + entity.hitbox.getHeight();

        int entityLeftCol = entityLeftX / TILE_SIZE;
        int entityRightCol = entityRightX / TILE_SIZE;
        int entityTopRow = entityTopY / TILE_SIZE;
        int entityBottomRow = entityBottomY / TILE_SIZE;

        switch (entity.getDirection()){
            case DIR_UP:
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
            case DIR_DOWN:
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
            case DIR_LEFT:
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
            case DIR_RIGHT:
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
        checkEntity(entity);
    }
    public void checkEntity(Character entity){
        for (Character otherEntity : gamePanel.getEntityManager().getEntities().orElse(Collections.emptyList())) {
            if (!otherEntity.equals(entity) && entity.hitbox.intersects(otherEntity.hitbox)) {
                switch (entity.getDirection()) {
                    case DIR_UP:
                        if (entity.getWorldCoordY() > otherEntity.getWorldCoordY()) {
                            entity.setCollision(true);
                            logCollision(otherEntity, DIR_UP);
                        }
                        break;
                    case DIR_DOWN:
                        if (entity.getWorldCoordY() < otherEntity.getWorldCoordY()) {
                            entity.setCollision(true);
                            logCollision(otherEntity, DIR_DOWN);
                        }
                        break;
                    case DIR_LEFT:
                        if (entity.getWorldCoordX() > otherEntity.getWorldCoordX()) {
                            entity.setCollision(true);
                            logCollision(otherEntity, DIR_LEFT);
                        }
                        break;
                    case DIR_RIGHT:
                        if (entity.getWorldCoordX() < otherEntity.getWorldCoordX()) {
                            entity.setCollision(true);
                            logCollision(otherEntity, DIR_RIGHT);
                        }
                        break;
                    default:
                        break;
                }
                return;
            }
        }
    }
    private void logCollision(Character entity, String direction){
        log.debug("Other entity hitbox dimensions:\n x: {}, y: {}, width: {}, height: {}\n Direction: {}", entity.hitbox.getCoordX(), entity.hitbox.getCoordY(), entity.hitbox.getWidth(), entity.hitbox.getHeight(), direction);
    }
}
