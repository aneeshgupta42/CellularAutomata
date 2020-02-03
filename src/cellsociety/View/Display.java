package cellsociety.View;

import cellsociety.Controller.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Display extends Application {


        private Scene myScene;
        private MainView myMainview;

        @Override
        public void start(Stage stage) throws Exception {

                myMainview = new MainView();
                myScene = new Scene(myMainview, 740, 600);
                stage.setScene(myScene);
                stage.setTitle("Simulation");
                stage.show();

        }


        public static void main(String[] args) {launch();}
}
