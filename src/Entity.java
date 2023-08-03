enum EntityType {
    TREE,
    ROCK,
    GRASS,
    HERBIVORE,
    PREDATOR
}

public abstract class Entity {
    public Coordinates coordinates;
    public final EntityType type;

    public Entity(Coordinates coordinates, EntityType type) {
        this.coordinates = coordinates;
        this.type = type;
    }
}
