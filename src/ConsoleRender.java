import java.util.Arrays;
import java.util.TreeSet;

public class ConsoleRender {
    private final static String ANSI_RESET = "\u001B[0m";
    private final static String ANSI_GREEN_COLOR = "\u001B[32m";
    private final static String ANSI_RED_COLOR = "\u001B[31m";
    private final static String ANSI_BRIGHTRED_COLOR = "\u001B[91m";
    private final static String ANSI_BRIGHTGREEN_COLOR = "\u001B[92m";
    private final static String ANSI_BRIGHTBLACK_COLOR = "\u001B[90m";
    private final static String ANSI_BRIGHTWHITE_COLOR = "\u001B[97m";
    private boolean printStatus = false;

    public ConsoleRender(boolean printStatus) {
        this.printStatus = printStatus;
    }

    public void render(WorldMap map){
        StringBuilder output = new StringBuilder();
        char[] border = new char[map.getWidth() + 2];
        Arrays.fill(border, '-');
        String borderString = new String(border);
        output.append("\033[H\033[2J");
        output.append(borderString);
        output.append('\n');
        for (int y = 0; y < map.getHeight(); y++) {
            output.append("|");
            for (int x = 0; x < map.getWidth(); x++) {
                Entity entity = map.getEntity(new Coordinates(x, y));
                if(entity == null)
                    output.append(" ");
                else
                    output.append(getEntityShader(entity));
            }
            output.append("|\n");
        }
        output.append(borderString);
        output.append("\n");
        if(printStatus)
            output.append(getCreaturesStatus(map));
        System.out.println(output);
    }

    private String getCreaturesStatus(WorldMap map){
        long avgPredatorsHealth = 0;
        long avgHebrivoresHealth = 0;
        int predators = 0;
        int herbivores = 0;
        for(Entity entity : map.getHashMap().values()){
            if(entity instanceof Herbivore) {
                herbivores++;
                if(((Herbivore) entity).isAlive())
                    avgHebrivoresHealth += ((Herbivore) entity).getHealthPoints();
            }
            if(entity instanceof Predator) {
                predators++;
                if(((Predator) entity).isAlive())
                    avgPredatorsHealth += ((Predator) entity).getHealthPoints();
            }
        }
        return String.format("Predators: %4d, avgHP:%4d\nHerbivores:%4d, avgHP:%4d\n",
                predators, (avgPredatorsHealth / (predators == 0 ? 1 : predators)),
                herbivores, (avgHebrivoresHealth / (herbivores == 0 ? 1 : herbivores)));
    }

    private String getCreaturesDescription(WorldMap map){
        StringBuilder sb = new StringBuilder();
        TreeSet<String> set = new TreeSet<>();
        for(Entity entity : map.getHashMap().values()){
            if(entity instanceof Creature) {
                Creature creature = (Creature) entity;
                set.add(creature + "\n");
            }
        }
        for(String str : set)
            sb.append(str);
        return sb.toString();
    }

    private String getEntityShader(Entity entity){
        switch (entity.getClass().getSimpleName()){
            case "Tree" :
                return ANSI_BRIGHTGREEN_COLOR + "¶" + ANSI_RESET;
            case "Grass" :
                return ANSI_GREEN_COLOR + "░" + ANSI_RESET;
            case "Rock" :
                return ANSI_BRIGHTBLACK_COLOR + "▄" + ANSI_RESET;
            case "Herbivore" :
                return ((Herbivore) entity).isAlive() ?
                        (ANSI_BRIGHTWHITE_COLOR) + "@" + ANSI_RESET :
                        (ANSI_BRIGHTBLACK_COLOR) + "@" + ANSI_RESET;
            case "Predator" :
                return ((Predator) entity).isAlive() ?
                        (ANSI_BRIGHTRED_COLOR) + "8" + ANSI_RESET :
                        (ANSI_BRIGHTBLACK_COLOR) + "8" + ANSI_RESET;
        }
        return " ";
    }
}
