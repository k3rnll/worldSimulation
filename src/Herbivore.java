import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Herbivore extends Creature{
    private final Random random = new Random();

    public Herbivore(Coordinates coordinates) {
        super(coordinates, EntityType.HERBIVORE);
    }

    private int countStepsToClosestGrass(WorldMap map, int distance, MoveDirection direction){
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
            if(entity == null) {
                totalSteps = i + 1;
                continue;
            }
            if(entity instanceof Grass)
                return totalSteps + 1;
            else {
                break;
            }
        }
        return Integer.MAX_VALUE;
    }

    private int countGrassOnDirection(WorldMap map, int distance,MoveDirection direction){
        int totalGrass = 0;
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
            if(entity instanceof Grass)
                totalGrass++;
            if(entity != null && !(entity instanceof Grass))
                break;
        }

        return totalGrass;
    }

    public int makeMoveToGoal(WorldMap map){
        if(haveBiteSomethingAround(map))
            return 0;

        HashMap<MoveDirection, Integer> directionMap = new HashMap<>();
        int distance = map.getWidth() / 3;

        for(MoveDirection direction : MoveDirection.values()){
            directionMap.put(direction, countStepsToClosestGrass(map, distance, direction));
        }

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
        return 0;
    }

    private boolean haveBiteSomethingAround(WorldMap map){
        Set<Coordinates> viewField = getViewField(map, 1);
        for(Coordinates coordinates : viewField){
            Entity entity = map.getEntity(coordinates);
            if(entity instanceof Grass){
                this.makeMoveToCoordinates(map, coordinates);
                return true;
            }
        }
        return false;
    }
}
