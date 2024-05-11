package cz.cvut.fel.pjv.entity;

public abstract class RespondingNPC extends NPC{

        protected RespondingNPC(int worldCoordX, int worldCoordY) {
                super(worldCoordX, worldCoordY);
        }

        public void respondToPlayer(){
            // NPC will respond to the res.player's actions
        }
}
