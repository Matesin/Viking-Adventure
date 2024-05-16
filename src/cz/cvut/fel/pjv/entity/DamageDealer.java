package cz.cvut.fel.pjv.entity;

import lombok.Getter;
import lombok.Setter;

import static cz.cvut.fel.pjv.gameloop.Constants.Directions.*;

public class DamageDealer {
    @Getter
    @Setter
    private int damage;
    private int knockBack;

    public DamageDealer(int damage, int knockBack) {
        this.damage = damage;
        this.knockBack = knockBack;
    }

    public DamageDealer(int damage) {
        this.damage = damage;
        this.knockBack = 0;
    }

    public void dealDamage(Character character) {
        character.setHealth(character.getHealth() - this.damage);
        knockBack(character);
    }

    public void knockBack(Character character) {
        switch (character.direction){
            case DIR_UP:
                character.worldCoordY = character.worldCoordY + 10;
                break;
            case DIR_DOWN:
                character.worldCoordY = character.worldCoordY - 10;
                break;
            case DIR_LEFT:
                character.worldCoordX = character.worldCoordX + 10;
                break;
            case DIR_RIGHT:
                character.worldCoordX = character.worldCoordX - 10;
                break;
            default:
                break;
        }
    }
}
