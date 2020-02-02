package cellsociety.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
