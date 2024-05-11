package cz.cvut.fel.pjv.map;

import lombok.Getter;

public class TileProperty {
    @Getter
    private final String tileName;
    @Getter
    private final int tileIndex;
    @Getter
    private final boolean isSolid;
    @Getter
    private final boolean collision;

    public TileProperty(String tileName, int tileIndex, boolean isSolid, boolean collision) {
        this.tileName = tileName;
        this.tileIndex = tileIndex;
        this.isSolid = isSolid;
        this.collision = collision;
    }

    public TileProperty(String tileName, int tileIndex, boolean isSolid) {
        this.tileName = tileName;
        this.tileIndex = tileIndex;
        this.isSolid = isSolid;
        this.collision = false;
    }


    public TileProperty(String tileName, int tileIndex) {
        this.tileName = tileName;
        this.tileIndex = tileIndex;
        this.isSolid = false;
        this.collision = false;
    }

}
