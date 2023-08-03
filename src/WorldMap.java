import java.util.HashMap;

public class WorldMap {
    private final Integer width;
    private final Integer height;
    private final HashMap<Coordinates, Entity> map = new HashMap<>();

    public WorldMap(Integer width, Integer height){
        this.width = width;
        this.height = height;
    }

    public void removeEntity(Coordinates coordinates){
        if(coordinates != null && coordinates.x < width && coordinates.y < height)
            map.remove(coordinates);
    }

    public void putEntity(Entity entity, Coordinates coordinates){
        if(coordinates != null && coordinates.x < width && coordinates.y < height){
            if(entity != null)
                map.put(coordinates, entity);
            else
                map.remove(coordinates);
        }

    }

    public Entity getEntity(Coordinates coordinates){
        return map.get(coordinates);
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public HashMap<Coordinates, Entity> getHashMap() {
        return map;
    }

}
