package cellsociety;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;



public class Toolbar extends ToolBar {


    private MainView mainView;
    private AnimationTimer timer;

    private int seconds;

    public Toolbar(MainView mainView) {

        Button play = new Button("Play");
        play.setOnAction(this::handlePlay);
        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);
        Button step = new Button("Step");
        step.setOnAction(this::handleStep);
        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);

        this.getItems().addAll(play, stop, step, reset);
    }


    private void handlePlay(ActionEvent actionEvent) {
      mainView.getTimer().start();
    }
    private void handleStop(ActionEvent actionEvent) {
        mainView.getTimer().stop();
    }

    private void handleStep(ActionEvent actionEvent) {

    }

    private void handleReset(ActionEvent actionEvent) {

    }
}
