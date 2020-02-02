package cellsociety.configuration;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main {
    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        System.out.println("Testing out XML reading");
        XMLReader reader = new XMLReader("media");
        Game game = reader.getGame("data/example.xml");
        System.out.println(game.getAuthor());
        System.out.println(game.getSimulationName());
        System.out.println(game.getMyRows());
        System.out.println(game.getMyCols());
        System.out.println(game.getMySize());
    }
}
