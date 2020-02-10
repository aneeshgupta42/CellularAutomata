package cellsociety.configuration;

/**
 * Testing out the XML reader, by passsing in a file.
 * @author Aneesh Gupta, Shruthi Kumar, Chris Warren
 */
public class TestXMLReader {
    /**
     * Start of the program.
     */

    public static void main (String[] args) {
        System.out.println("Testing out XML reading");
        XMLReader reader = new XMLReader("media");
        Game game = reader.getGame("data/random1.xml");
        System.out.println("Author: " + game.getAuthor());
        System.out.println("Simulation name: " + game.getSimulationName());
        System.out.println("Choice: " + game.getMyChoice());
        System.out.println("Rows: "+ game.getMyRows());
        System.out.println("Layout: "+ game.getMyLayout());
        System.out.println("Cols: "+ game.getMyCols());
        System.out.println("isLayout: "+ game.getIsLayout());
    }
}
