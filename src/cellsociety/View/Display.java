package cellsociety.View;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Display class which holds the stage and runs the program
 * @author Chris Warren, Aneesh Gupta, Shruthi Kumar
 */
public class Display extends Application {


        private Scene myScene;
        private MainView myMainview;

        /**
         * This program sets up the stage and creates a new scene using the variable MainView, which is where all our
         * content is held. The scene is then set in the stage and shown.
         * @param stage stage where everything is set
         * @throws Exception
         */
        @Override
        public void start(Stage stage) throws Exception {

                myMainview = new MainView();
                myScene = new Scene(myMainview, 740, 570);
                stage.setScene(myScene);
                stage.setTitle("Simulation");
                stage.show();

        }

        /**
         * Runs the program
         * @param args runs the program
         */
        public static void main(String[] args) {launch();}
}
