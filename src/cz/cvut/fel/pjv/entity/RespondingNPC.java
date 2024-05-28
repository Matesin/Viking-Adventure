package cz.cvut.fel.pjv.entity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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


}