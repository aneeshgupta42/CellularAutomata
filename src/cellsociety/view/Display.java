package cellsociety.view;


import cellsociety.configuration.Game;
import cellsociety.configuration.GridCreator;
import cellsociety.configuration.XMLException;
import cellsociety.configuration.XMLReader;
import cellsociety.model.Grid;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
        private Grid displayGrid;
        public static final String DATA_FILE_EXTENSION = "*.xml";
        // NOTE: generally accepted behavior that the chooser remembers where user left it last
        public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);


        private final int WIDTH = 1020;
        private final int HEIGHT = 570;


        /**
         * This program sets up the stage and creates a new scene using the variable MainView, which is where all our
         * content is held. The scene is then set in the stage and shown.
         * @param stage stage where everything is set
         * @throws Exception
         */
        @Override
        public void start(Stage stage) throws Exception {
//                Stage fileBrowser = new Stage();
                displayGrid = uploadNewFile();
//                fileBrowser.close();
                myMainview = new MainView(this);
                myScene = new Scene(myMainview, WIDTH, HEIGHT);
                stage.setScene(myScene);
                stage.setTitle("Simulation");
                stage.show();
        }

        public Grid uploadNewFile(){
                Grid uploadedGrid = readFileSimulation(new Stage());
                return uploadedGrid;
        }

        public Grid readFileSimulation (Stage primaryStage){
                File dataFile = FILE_CHOOSER.showOpenDialog(primaryStage);
                while(dataFile != null){
                        try{
                                XMLReader reader = new XMLReader("media");
                                Game myGame = reader.getGame(dataFile.getPath());
                                GridCreator creator = new GridCreator();
                                Grid uploadedGrid = creator.GridSelector(myGame.getMyChoice());
                                primaryStage.close();
                                return uploadedGrid;
                        }
                        catch (XMLException e){
                                showMessage(AlertType.ERROR, e.getMessage());
                        }
                        dataFile = FILE_CHOOSER.showOpenDialog(primaryStage);
                }
                return null; //wont ever hit this
        }

        private void showMessage (AlertType type, String message) {
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

        public Grid getDisplayGrid() {
                return displayGrid;
        }

        /**
         * Runs the program
         * @param args runs the program
         */
        public static void main(String[] args) {launch();}
}
