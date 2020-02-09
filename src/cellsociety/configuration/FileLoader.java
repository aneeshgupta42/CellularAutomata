package cellsociety.configuration;

import cellsociety.model.Grid;
import cellsociety.view.Display;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileLoader {
    public static final String DATA_FILE_EXTENSION = "*.xml";
    // NOTE: generally accepted behavior that the chooser remembers where user left it last
    public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);

    public Grid uploadNewFile(Game myGame){
        Grid uploadedGrid = readFileSimulation(new Stage(), myGame);
        return uploadedGrid;
    }

    public Grid readFileSimulation (Stage primaryStage, Game myGame){
        File dataFile = FILE_CHOOSER.showOpenDialog(primaryStage);
        while(dataFile != null){
            try{
                XMLReader reader = new XMLReader("media");
                myGame = reader.getGame(dataFile.getPath());
                GridCreator creator = new GridCreator();
                Grid uploadedGrid = creator.newGridSelector(myGame);
                primaryStage.close();
                return uploadedGrid;
            }
            catch (XMLException e){
                showMessage(Alert.AlertType.ERROR, e.getMessage());
            }
            dataFile = FILE_CHOOSER.showOpenDialog(primaryStage);
        }
        primaryStage.close();
        return null; //wont ever hit this
    }

    private void showMessage (Alert.AlertType type, String message) {
        new Alert(type, message).showAndWait();
    }

    private static FileChooser makeChooser (String extensionAccepted) {
        FileChooser result = new FileChooser();
        result.setTitle("Open Data File");
        // pick a reasonable place to start searching for files
        result.setInitialDirectory(new File(System.getProperty("user.dir")));
        result.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Text Files", extensionAccepted));
        return result;
    }
}
