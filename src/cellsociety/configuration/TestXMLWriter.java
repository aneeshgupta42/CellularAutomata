package cellsociety.configuration;

import cellsociety.model.Grid;

/**
 * Testing out the XML writer, by reading in a file.
 * And then writing it out.
 * @author Aneesh Gupta, Shruthi Kumar, Chris Warren
 */
public class TestXMLWriter {
    /**
     * Start of the program.
     */

    public static void main (String[] args) throws Exception {
        System.out.println("Testing out XML writing");
        XMLReader reader = new XMLReader("media");
        Game game = reader.getGame("data/percolationLayout.xml");
        GridCreator creator = new GridCreator();
        Grid myGrid = creator.newGridSelector(game);
        XMLWriter writer = new XMLWriter(myGrid,game);
        writer.outputFile();
    }
}
