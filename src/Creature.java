import java.util.HashSet;
import java.util.Set;

public abstract class Creature extends Entity implements Movable, Lookable{
    private int healthPoints;
    private boolean isAlive = true;
    private Entity stayingOn = null;

    public Creature(Coordinates coordinates, EntityType type) {
        super(coordinates, type);
        healthPoints = 100;
    }

    public Set<Coordinates> getViewField(WorldMap map, int viewDistance){
        Set<Coordinates> viewField = new HashSet<>();
        int field = viewDistance * 2 + 1;
        int newX;
        int newY;
        for (int y = 0; y < field; y++) {
            newY = this.coordinates.y + (y - field / 2);
            for (int x = 0; x < field; x++) {
                newX = this.coordinates.x + (x - field / 2);
                if((newX >= 0 && newX < map.getWidth()) &&
                        (newY >= 0 && newY < map.getHeight()) &&
                        (newX != this.coordinates.x || newY != this.coordinates.y))
                    viewField.add(new Coordinates(newX, newY));
            }
        }
        return viewField;
    }

    public void addHealthPoints(int points){
        if(isAlive && points > 0){
            healthPoints = Math.min(healthPoints + points, 100);
        }
    }

    public void subHealthPoints(int points){
        if(isAlive && points > 0){
            isAlive = points < healthPoints;
            healthPoints -= points;
        }
    }

    public void makeMove(WorldMap map, MoveDirection direction) {
        int newX = this.coordinates.x;
        int newY = this.coordinates.y;

        if(direction == MoveDirection.UP && newY > 0)
            newY--;
        if(direction == MoveDirection.DOWN && map.getHeight() - 1 > 0 && newY < map.getHeight() - 1)
            newY++;
        if(direction == MoveDirection.LEFT && newX > 0)
            newX--;
        if(direction == MoveDirection.RIGHT && map.getWidth() - 1 > 0 && newX < map.getWidth() - 1)
            newX++;
        makeMoveToCoordinates(map, new Coordinates(newX, newY));
    }

    public void makeMoveToCoordinates(WorldMap map, Coordinates newCoordinates){
        if(this.isAlive()){
            Entity entityOnNewCoordinates = map.getEntity(newCoordinates);
            if(entityOnNewCoordinates == null || entityOnNewCoordinates.type == EntityType.GRASS){
                map.putEntity(this.getStayingOn(), this.coordinates);
                this.coordinates = newCoordinates;
                map.putEntity(this, this.coordinates);
                this.setStayingOn(entityOnNewCoordinates);
                if(this.type == EntityType.HERBIVORE && this.stayingOn != null){
                    this.addHealthPoints(20);
                    this.stayingOn = null;
                }
            }
        }
    }

    public void setStayingOn(Entity entity){
        this.stayingOn = entity;
    }

    public Entity getStayingOn(){
        return this.stayingOn;
    }

    public int getHealthPoints(){
        return healthPoints;
    }

    public boolean isAlive(){
        return isAlive;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\t{healthPoints=" + healthPoints +
                ", isAlive=" + isAlive +
                ", stayingOn=" + stayingOn +
                "} ";
    }
}
