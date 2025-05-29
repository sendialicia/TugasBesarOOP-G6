package farmTile;

public class TileObject {
    public String type;
    public TileLocation location = new TileLocation(0, 0);

    public TileObject(String type){
        this.type = type;
    }

    public TileObject(String type, int x, int y){
        this.type = type;
        this.location.worldX = x;
        this.location.worldY = y;
    }
}
