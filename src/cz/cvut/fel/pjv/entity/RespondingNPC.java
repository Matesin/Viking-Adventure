package cz.cvut.fel.pjv.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import cz.cvut.fel.pjv.inventory.Inventory;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

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