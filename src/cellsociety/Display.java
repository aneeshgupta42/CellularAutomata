package cellsociety;

import com.sun.tools.javac.Main;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class Display extends Application {


        private Scene myScene;

        @Override
        public void start(Stage stage) throws Exception {

                MainView mainView = new MainView();
                myScene = new Scene(mainView, 640, 500);
                stage.setScene(myScene);
                stage.setTitle("Simulation");
                mainView.draw();
                stage.show();
        }
        public static void main(String[] args) {launch();}
}
