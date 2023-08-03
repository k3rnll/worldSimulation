public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int MAP_WIDTH = 64;
        final int MAP_HEIGHT = 32;
        final int TREES = 50;
        final int ROCKS = 50;
        final int GRASS = 500;
        final int PREDATORS = 20;
        final int HERBIVORES = 80;
        final boolean PRINT_STATUS = true;


        WorldMap map = new WorldMap(MAP_WIDTH, MAP_HEIGHT);
        WorldSpinner worldSpinner = new WorldSpinner(map);
        ConsoleRender consoleRender = new ConsoleRender(PRINT_STATUS);
        worldSpinner.randomFillMapWith(EntityType.HERBIVORE, HERBIVORES);
        worldSpinner.randomFillMapWith(EntityType.PREDATOR, PREDATORS);
        worldSpinner.randomFillMapWith(EntityType.TREE, TREES);
        worldSpinner.randomFillMapWith(EntityType.ROCK, ROCKS);
        worldSpinner.randomFillMapWith(EntityType.GRASS, GRASS);
        consoleRender.render(map);
        int frame = 0;
        while (true) {
            frame++;
            worldSpinner.movePredators();
            if(frame % 2 == 0)
                worldSpinner.moveHerbivores();
            if(frame % 10 == 0) {
                if(worldSpinner.getMapFillCoefficient() < 0.5)
                    worldSpinner.randomFillMapWith(EntityType.GRASS, 100);
                worldSpinner.makeDamageForCreatures(1, EntityType.HERBIVORE);
                worldSpinner.makeDamageForCreatures(5, EntityType.PREDATOR);

            }
            if(frame % 50 == 0){
                worldSpinner.removeDeadCreatures();
            }
            if(frame % 20 == 0 && worldSpinner.getAliveCreaturesNum() == 0)
                break;
            consoleRender.render(map);
            Thread.sleep(100);
        }
    }
}