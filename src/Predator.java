import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Predator extends Creature implements Movable, Lookable{
    private final Random random = new Random();

    public Predator(Coordinates coordinates) {
        super(coordinates, EntityType.PREDATOR);
    }

    private int countStepsToClosestHerbivore(WorldMap map, int distance, MoveDirection direction){
        int totalSteps = 0;
        for (int i = 0; i < distance; i++) {
            int newX = this.coordinates.x;
            int newY = this.coordinates.y;
            if(direction == MoveDirection.RIGHT)
                newX = this.coordinates.x + i + 1;
            else if(direction == MoveDirection.LEFT)
                newX = this.coordinates.x - i - 1;
            else if(direction == MoveDirection.UP)
                newY = this.coordinates.y - i - 1;
            else if(direction == MoveDirection.DOWN)
                newY = this.coordinates.y + i + 1;
            else
                break;
            Entity entity = map.getEntity(new Coordinates(newX, newY));
            if(entity == null || entity instanceof Grass) {
                totalSteps = i + 1;
                continue;
            }
            if(entity instanceof Herbivore)
                return totalSteps + 1;
            else {
                break;
            }
        }

        return Integer.MAX_VALUE;
    }

    public void MakeMoveToGoal(WorldMap map){
        haveBiteSomebodyAround(map);

        HashMap<MoveDirection, Integer> directionMap = new HashMap<>();
        int distance = map.getWidth() / 3;
        directionMap.put(MoveDirection.UP, countStepsToClosestHerbivore(map, distance, MoveDirection.UP));
        directionMap.put(MoveDirection.DOWN, countStepsToClosestHerbivore(map, distance, MoveDirection.DOWN));
        directionMap.put(MoveDirection.LEFT, countStepsToClosestHerbivore(map, distance, MoveDirection.LEFT));
        directionMap.put(MoveDirection.RIGHT, countStepsToClosestHerbivore(map, distance, MoveDirection.RIGHT));

        MoveDirection direction = MoveDirection.values()[random.nextInt(MoveDirection.values().length)];
        int total = Integer.MAX_VALUE;
        for(MoveDirection newDirection : directionMap.keySet()){
            int val = directionMap.get(newDirection);
            if(val < total){
                total = val;
                direction = newDirection;
            }
        }
        this.makeMove(map, direction);
    }

    private boolean haveBiteSomebodyAround(WorldMap map){
        Set<Coordinates> viewField = getViewField(map, 1);
        for(Coordinates coordinates : viewField){
            Entity entity = map.getEntity(coordinates);
            if(entity instanceof Herbivore){
                Herbivore herbivore = (Herbivore) entity;
                if(herbivore.isAlive()) {
                    herbivore.subHealthPoints(5);
                    this.addHealthPoints(5);
                }
                else
                    this.addHealthPoints(1);
                return true;
            }
        }
        return false;
    }
}
