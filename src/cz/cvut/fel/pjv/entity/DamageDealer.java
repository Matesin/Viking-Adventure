package cz.cvut.fel.pjv.entity;

import lombok.Getter;
import lombok.Setter;

import static cz.cvut.fel.pjv.gameloop.Constants.Directions.*;
/**
 * Class representing a damage dealer in the game.
 * This class is responsible for dealing damage and applying knockback to characters.
 */
@Getter
@Setter
public class DamageDealer {
    private int damage;
    private int knockBack;

    /**
     * Constructor for DamageDealer with specified damage and knockback.
     *
     * @param damage the amount of damage to deal
     * @param knockBack the amount of knockback to apply
     */
    public DamageDealer(int damage, int knockBack) {
        this.damage = damage;
        this.knockBack = knockBack;
    }

    /**
     * Constructor for DamageDealer with specified damage and no knockback.
     *
     * @param damage the amount of damage to deal
     */
    public DamageDealer(int damage) {
        this.damage = damage;
        this.knockBack = 0;
    }

    /**
     * Deals damage to a character and applies knockback.
     *
     * @param character the character to deal damage to
     */
    public void dealDamage(Character character) {
        character.setHealth(character.getHealth() - this.damage);
        knockBack(character);
    }

    /**
     * Applies knockback to a character.
     *
     * @param character the character to apply knockback to
     */
    public void knockBack(Character character) {
        switch (character.direction){
            case DIR_UP:
                character.worldCoordY = character.worldCoordY + this.knockBack;
                break;
            case DIR_DOWN:
                character.worldCoordY = character.worldCoordY - this.knockBack;
                break;
            case DIR_LEFT:
                character.worldCoordX = character.worldCoordX + this.knockBack;
                break;
            case DIR_RIGHT:
                character.worldCoordX = character.worldCoordX - this.knockBack;
                break;
            default:
                break;
        }
    }
}
