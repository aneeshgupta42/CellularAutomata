package cellsociety.configuration;
import javafx.application.Platform;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;

/**
 * Testing out the XML reader
 * @author Aneesh Gupta, Shruthi Kumar, Chris Warren
 */
public class TestXMLReader {
    /**
     * Start of the program.
     */

    public static void main (String[] args) {
        System.out.println("Testing out XML reading");
        XMLReader reader = new XMLReader("media");
        Game game = reader.getGame("data/populatedCells.xml");
        System.out.println("Author: " + game.getAuthor());
        System.out.println("Simulation name: " + game.getSimulationName());
        System.out.println("Choice: " + game.getMyChoice());
        System.out.println("Rows: "+ game.getMyRows());
        System.out.println("Layout: "+ game.getMyLayout());
        System.out.println("Cols: "+ game.getMyCols());
        System.out.println("Size: "+ game.getMySize());
    }
}
