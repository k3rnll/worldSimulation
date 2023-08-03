import java.util.Set;

public interface Lookable {
    Set<Coordinates> getViewField(WorldMap map, int viewDistance);
}
