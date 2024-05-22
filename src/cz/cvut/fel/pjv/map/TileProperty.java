package cz.cvut.fel.pjv.map;

import lombok.Getter;

/**
 * Class representing a tile property.
 */
@Getter
public class TileProperty {
    private final String tileName;
    private final int tileIndex;
    private final boolean isSolid;
    private final boolean collision;

    /**
     * @param tileName name of the tile
     * @param tileIndex index of the tile
     * @param isSolid if the tile is solid
     * @param collision if the tile has collision
     */
    public TileProperty(String tileName, int tileIndex, boolean isSolid, boolean collision) {
        this.tileName = tileName;
        this.tileIndex = tileIndex;
        this.isSolid = isSolid;
        this.collision = collision;
    }

    /**
     * @param tileName name of the tile
     * @param tileIndex index of the tile
     * @param isSolid if the tile is solid
     */
    public TileProperty(String tileName, int tileIndex, boolean isSolid) {
        this.tileName = tileName;
        this.tileIndex = tileIndex;
        this.isSolid = isSolid;
        this.collision = false;
    }

    /**
     * @param tileName name of the tile
     * @param tileIndex index of the tile
     * assuming the tile is not solid by default
     */
    public TileProperty(String tileName, int tileIndex) {
        this.tileName = tileName;
        this.tileIndex = tileIndex;
        this.isSolid = false;
        this.collision = false;
    }

}
