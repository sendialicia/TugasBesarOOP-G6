package farmTile;

import java.util.Objects;

public class TileLocation {
    public int worldX;
    public int worldY;

    public TileLocation(int x, int y){
        this.worldX = x;
        this.worldY = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TileLocation)) return false;
        TileLocation other = (TileLocation) obj;
        return this.worldX == other.worldX && this.worldY == other.worldY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(worldX, worldY);
    }
}
