package cz.cvut.fel.pjv.entity;

/**
 * Abstract class representing a responding non-player character (NPC) in the game.
 * This class extends the NPC class and can be extended by specific types of responding NPCs.
 */
public abstract class RespondingNPC extends NPC{

        /**
         * Constructor for RespondingNPC with specified world coordinates.
         *
         * @param worldCoordX the x-coordinate of the NPC in the world
         * @param worldCoordY the y-coordinate of the NPC in the world
         */
        protected RespondingNPC(int worldCoordX, int worldCoordY) {
                super(worldCoordX, worldCoordY);
        }

        /**
         * Handles the NPC's response to the player's actions.
         */
        public void respondToPlayer(){
                // NPC will respond to the player's actions
        }
}