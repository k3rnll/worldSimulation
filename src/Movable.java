enum MoveDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

public interface Movable {
    void makeMove(WorldMap map, MoveDirection direction);
    void makeMoveToCoordinates(WorldMap map, Coordinates newCoordinates);
}
