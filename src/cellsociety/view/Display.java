package cellsociety.view;


import cellsociety.configuration.Game;
import cellsociety.configuration.XMLReader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Display class which holds the stage and runs the program
 * @author Chris Warren, Aneesh Gupta, Shruthi Kumar
 */
public class Display extends Application {


        private Scene myScene;
        private MainView myMainview;
        private Game game;
        public static final String DATA_FILE_EXTENSION = "*.xml";
        // NOTE: generally accepted behavior that the chooser remembers where user left it last
        public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);


        /**
         * This program sets up the stage and creates a new scene using the variable MainView, which is where all our
         * content is held. The scene is then set in the stage and shown.
         * @param stage stage where everything is set
         * @throws Exception
         */
        @Override
        public void start(Stage stage) throws Exception {
                Stage newstage = new Stage();
                myMainview = new MainView();
                myScene = new Scene(myMainview, 740, 570);
                stage.setScene(myScene);
                stage.setTitle("Simulation");
                stage.show();
                readFileSimulation(newstage);
                newstage.show();
        }
        public void readFileSimulation (Stage primaryStage){
                File dataFile = FILE_CHOOSER.showOpenDialog(primaryStage);
                XMLReader reader = new XMLReader("media");
                game = reader.getGame(dataFile.getPath());
                // nothing selected, so quit the application
//                Platform.exit();
        }
        
        private static FileChooser makeChooser (String extensionAccepted) {
                FileChooser result = new FileChooser();
                result.setTitle("Open Data File");
                // pick a reasonable place to start searching for files
                result.setInitialDirectory(new File(System.getProperty("user.dir")));
                result.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Text Files", extensionAccepted));
                return result;
        }

        /**
         * Runs the program
         * @param args runs the program
         */
        public static void main(String[] args) {launch();}
}
