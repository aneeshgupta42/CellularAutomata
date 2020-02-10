package cellsociety.configuration;

import cellsociety.model.Grid;

/**
 * Testing out the XML reader, by passsing in a file.
 * @author Aneesh Gupta, Shruthi Kumar, Chris Warren
 */
public class TestXMLWriter {
    /**
     * Start of the program.
     */

    public static void main (String[] args) throws Exception {
        System.out.println("Testing out XML writing");
        XMLReader reader = new XMLReader("media");
        Game game = reader.getGame("data/gameOfLife.xml");
        GridCreator creator = new GridCreator();
        Grid myGrid = creator.newGridSelector(game);
        XMLWriter writer = new XMLWriter(myGrid,game);
        writer.outputFile();
    }
}
