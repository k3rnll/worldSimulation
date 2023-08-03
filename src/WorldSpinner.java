import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class WorldSpinner {
    private final WorldMap map;

    public WorldSpinner(WorldMap worldMap){
        this.map = worldMap == null ? new WorldMap(10, 10) : worldMap;
    }

    public void moveHerbivores(){
        Set<Coordinates> coordinatesSet = new HashSet<>(map.getHashMap().keySet());
        Set<Herbivore> movedHerbivores = new HashSet<>();
        for(Coordinates coordinates : coordinatesSet){
            if(map.getEntity(coordinates) instanceof Herbivore){
                Herbivore herbivore = (Herbivore) map.getEntity(coordinates);
                if(!movedHerbivores.contains(herbivore)) {
                    herbivore.makeMoveToGoal(map);
                    movedHerbivores.add(herbivore);
                }
            }
        }
    }

    public void movePredators(){
        Set<Coordinates> coordinatesSet = new HashSet<>(map.getHashMap().keySet());
        Set<Predator> movedPredators = new HashSet<>();
        for(Coordinates coordinates : coordinatesSet){
            if(map.getEntity(coordinates) instanceof Predator){
                Predator predator = (Predator) map.getEntity(coordinates);
                if(!movedPredators.contains(predator)) {
                    predator.MakeMoveToGoal(map);
                    movedPredators.add(predator);
                }
            }
        }
    }

    public void makeDamageForAllCreatures(int damagePoints){
        for(Coordinates coordinates : map.getHashMap().keySet()){
            if(map.getEntity(coordinates) instanceof Creature){
                ((Creature) map.getEntity(coordinates)).subHealthPoints(damagePoints);
            }
        }
    }

    public void makeDamageForCreatures(int damagePoints, EntityType type){
        for(Coordinates coordinates : map.getHashMap().keySet()){
            if(map.getEntity(coordinates) instanceof Creature && map.getEntity(coordinates).type == type){
                ((Creature) map.getEntity(coordinates)).subHealthPoints(damagePoints);
            }
        }
    }

    public void removeDeadCreatures(){
        Set<Coordinates> coordinatesSet = new HashSet<>(map.getHashMap().keySet());
        for(Coordinates coordinates : coordinatesSet){
            if(map.getEntity(coordinates) instanceof Creature){
                Creature creature = (Creature) map.getEntity(coordinates);
                if(!creature.isAlive()) {
                    map.removeEntity(coordinates);
                    map.putEntity(new Grass(coordinates), coordinates);
                }
            }
        }
    }

    public void randomFillMapWith(EntityType type, Integer count){
        Random random = new Random();
        int freeSpace = (map.getWidth() * map.getHeight()) - map.getHashMap().size();
        if(count > freeSpace){
            count = freeSpace;
        }
        for (int element = 0; element < count; ) {
            Coordinates coordinates = new Coordinates(random.nextInt(map.getWidth()),
                                                      random.nextInt(map.getHeight()));
            if(map.getEntity(coordinates) != null)
                continue;
            if(type == EntityType.TREE)
                map.putEntity(new Tree(coordinates), coordinates);
            if(type == EntityType.ROCK)
                map.putEntity(new Rock(coordinates), coordinates);
            if(type == EntityType.GRASS)
                map.putEntity(new Grass(coordinates), coordinates);
            if(type == EntityType.HERBIVORE)
                map.putEntity(new Herbivore(coordinates), coordinates);
            if(type == EntityType.PREDATOR)
                map.putEntity(new Predator(coordinates), coordinates);
            element++;
        }
    }

    public double getMapFillCoefficient(){
        return (double) map.getHashMap().size() / (map.getWidth() * map.getHeight()) ;
    }

    public int getAliveCreaturesNum(){
        int aliveCreatures = 0;
        for(Coordinates coordinates : map.getHashMap().keySet()){
            if((map.getEntity(coordinates) instanceof Creature) && (((Creature)map.getEntity(coordinates)).isAlive()))
                aliveCreatures++;
        }
        return aliveCreatures;
    }

}
