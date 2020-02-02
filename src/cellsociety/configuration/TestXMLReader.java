package cellsociety.configuration;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class TestXMLReader {
    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        System.out.println("Testing out XML reading");
        XMLReader reader = new XMLReader("media");
        Game game = reader.getGame("data/percolation.xml");
        System.out.println("Author: " + game.getAuthor());
        System.out.println("Simulation name: " + game.getSimulationName());
        System.out.println("Choice: " + game.getMyChoice());
        System.out.println("Rows: "+ game.getMyRows());
        System.out.println("Cols: "+ game.getMyCols());
        System.out.println("Size: "+ game.getMySize());
    }
}
