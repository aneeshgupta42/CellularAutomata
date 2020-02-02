package cellsociety;

import com.sun.tools.javac.Main;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionEvent;

public class Display extends Application {


        private Scene myScene;
        private static final int FRAMES_PER_SECOND = 60;
        private static final double MILLISECOND_DELAY = 1000/FRAMES_PER_SECOND;

        @Override
        public void start(Stage stage) throws Exception {

                MainView mainView = new MainView();
                myScene = new Scene(mainView, 740, 600);
                stage.setScene(myScene);
                stage.setTitle("Simulation");
                mainView.draw();
                stage.show();
//
//                KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), event -> mainView.draw());
//                Timeline animation = new Timeline();
//                animation.setCycleCount(Timeline.INDEFINITE);
//                animation.getKeyFrames().add(frame);
//                animation.play();
        }
        public static void main(String[] args) {launch();}
}
