package cellsociety.configuration;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class TestXMLReader {
    /**
     * Start of the program.
     */
    public static final String DATA_FILE_EXTENSION = "*.xml";
    // NOTE: generally accepted behavior that the chooser remembers where user left it last
    public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);

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

    public void start (Stage primaryStage){
        File dataFile = FILE_CHOOSER.showOpenDialog(primaryStage);
        XMLReader reader = new XMLReader("media");
        Game game = reader.getGame(dataFile.getPath());
        // nothing selected, so quit the application
        Platform.exit();
    }

    private static FileChooser makeChooser (String extensionAccepted) {
        FileChooser result = new FileChooser();
        result.setTitle("Open Data File");
        // pick a reasonable place to start searching for files
        result.setInitialDirectory(new File(System.getProperty("user.dir")));
        result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
        return result;
    }
}
