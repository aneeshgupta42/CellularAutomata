package cellsociety.View;

import cellsociety.Controller.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Display extends Application {


        private Scene myScene;
        private static final int FRAMES_PER_SECOND = 60;
        private static final double MILLISECOND_DELAY = 10000/FRAMES_PER_SECOND;

        @Override
        public void start(Stage stage) throws Exception {

                MainView mainView = new MainView();
                myScene = new Scene(mainView, 740, 600);
                stage.setScene(myScene);
                stage.setTitle("Simulation");
                stage.show();

                KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
                        try {
                                mainView.step();
                        } catch (Exception ex) {
                                ex.printStackTrace();
                        }
                });
                Timeline animation = new Timeline();
                animation.setCycleCount(Timeline.INDEFINITE);
                animation.getKeyFrames().add(frame);
                animation.play();
//
//                KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), event -> mainView.draw());
//                Timeline animation = new Timeline();
//                animation.setCycleCount(Timeline.INDEFINITE);
//                animation.getKeyFrames().add(frame);
//                animation.play();
        }
        public static void main(String[] args) {launch();}
}
