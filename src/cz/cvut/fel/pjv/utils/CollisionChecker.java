package cz.cvut.fel.pjv.utils;

import cz.cvut.fel.pjv.entity.Character;
import cz.cvut.fel.pjv.entity.Hitbox;
import cz.cvut.fel.pjv.gameloop.GamePanel;
import lombok.extern.slf4j.Slf4j;
import cz.cvut.fel.pjv.map_object.MapObject;

import java.util.Collections;

import static cz.cvut.fel.pjv.gameloop.Constants.Tile.*;
import static cz.cvut.fel.pjv.gameloop.Constants.Directions.*;

@Slf4j
public class CollisionChecker {
    //inspired by https://www.youtube.com/watch?v=oPzPpUcDiYY
    GamePanel gamePanel;

    /**
     * Constructor for CollisionChecker with specified game panel.
     *
     * @param gamePanel the game panel
     */
    public CollisionChecker(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    int tileNum1;
    int tileNum2;

    /**
     * Check if the entity is colliding with a tile, entity, or object.
     * @param entity the entity the collision is being checked for
     */
    public void checkTile(Character entity){
        // Check if the entity is colliding with a tile
        double entityLeftX = entity.hitbox.getCoordX();
        double entityRightX = entity.hitbox.getCoordX() + entity.hitbox.getWidth();
        double entityTopY =  entity.hitbox.getCoordY();
        double entityBottomY = entity.hitbox.getCoordY() + entity.hitbox.getHeight();

        int entityLeftCol = (int) entityLeftX / TILE_SIZE;
        int entityRightCol = (int) entityRightX / TILE_SIZE;
        int entityTopRow = (int) entityTopY / TILE_SIZE;
        int entityBottomRow = (int) entityBottomY / TILE_SIZE;
        switch (entity.getDirection()){
            case DIR_UP:
                entityTopRow = (int) (entityTopY - entity.getSpeed()) / TILE_SIZE;
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
                entityBottomRow = (int) (entityBottomY + entity.getSpeed()) / TILE_SIZE;
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
                entityLeftCol = (int) (entityLeftX - entity.getSpeed()) / TILE_SIZE;
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
                entityRightCol = (int) (entityRightX + entity.getSpeed()) / TILE_SIZE;
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
        checkObject(entity);
    }
    public void checkEntity(Character entity){
        for (Character otherEntity : gamePanel.getEntityManager().getEntities().orElse(Collections.emptyList())) {
            if (!otherEntity.equals(entity) && entity.hitbox.intersects(otherEntity.hitbox)) {
                handleCollision(entity, otherEntity.hitbox);
                return;
            }
        }
    }

    public void checkObject(Character player){
        for (MapObject mapObject : gamePanel.getMapObjectManager().getMapObjects().orElseThrow()){
            if (player.hitbox.intersects(mapObject.hitbox)) {
                handleCollision(player, mapObject.hitbox);
                return;
            }
        }
    }

    private void handleCollision(Character player, Hitbox other) {
        switch (player.getDirection()) {
            case DIR_UP:
                checkCollisionUp(player, other);
                break;
            case DIR_DOWN:
                checkCollisionDown(player, other);
                break;
            case DIR_LEFT:
                checkCollisionLeft(player, other);
                break;
            case DIR_RIGHT:
                checkCollisionRight(player, other);
                break;
            default:
                break;
        }
    }

    private void checkCollisionUp(Character player, Hitbox other) {
        if (player.hitbox.getCoordY() + player.getSpeed() > other.getCoordY()) {
            player.setCollision(true);
        }
    }

    private void checkCollisionDown(Character player, Hitbox other) {
        if (player.hitbox.getCoordY() - player.getSpeed() < other.getCoordY()) {
            player.setCollision(true);
        }
    }

    private void checkCollisionLeft(Character player, Hitbox other) {
        if (player.hitbox.getCoordX() + player.getSpeed() > other.getCoordX()) {
            player.setCollision(true);
        }
    }

    private void checkCollisionRight(Character player, Hitbox other) {
        if (player.hitbox.getCoordX() - player.getSpeed()  < other.getCoordX()) {
            player.setCollision(true);
        }
    }
}
