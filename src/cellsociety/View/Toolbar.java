package cellsociety.View;

import cellsociety.Model.*;
import cellsociety.View.MainView;
import cellsociety.configuration.Game;
import cellsociety.configuration.XMLReader;
import cellsociety.configuration.GridCreator;
import java.awt.event.MouseEvent;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import java.awt.Point;

import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Time;


public class Toolbar extends ToolBar {


    private MainView myMainView;


    private static final int FRAMES_PER_SECOND = 60;
    private static final double MILLISECOND_DELAY = 10000/FRAMES_PER_SECOND;

    private int seconds;
    private Grid currentGrid;
    private Timeline animation;
    private Grid myGrid;
    private Label lblTime;
    private AnimationTimer timer;
    private Slider slider;

    public Toolbar(MainView mainView) {
        myMainView = mainView;
        Button play = new Button("Play");
        play.setOnAction(this::handlePlay);

        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);

        Button step = new Button("Step");
        step.setOnAction(this::handleStep);

        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);

        ComboBox switchSimulation = new ComboBox();
        switchSimulation.getItems().addAll("Game of life", "Percolation", "Segregation", "Predator-Prey",
                "Fire");

        switchSimulation.setPromptText("Choose a Simulation");
        switchSimulation.setEditable(true);

        switchSimulation.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            animation.stop();
                    if (newValue == "Game of life") {

                        choosingNewSim(0);
                    } else if (newValue == "Percolation") {
                        choosingNewSim(1);
                    }else if (newValue == "Segregation") {
                        choosingNewSim(2);
                    } else if (newValue == "Predator-Prey") {
                       choosingNewSim(3);
                    } else if (newValue == "Fire") {
                        choosingNewSim(4);
                    }
                }
        );

        GridCreator creator = new GridCreator();
        currentGrid = creator.GridSelector(0);

        timer();
        animationFunctions();
        makeSlider();
        this.getItems().addAll(play, stop, step, reset, switchSimulation, lblTime, slider);
    }

    public void animationFunctions() {

        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
            try {
                myMainView.step();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    public Grid getCurrentGrid() {
        return currentGrid;
    }

    private void handlePlay(ActionEvent actionEvent) {
        animation.play();
        timer.start();
    }

    private void handleStop(ActionEvent actionEvent) {
        animation.stop();
        timer.stop();
    }

    private void handleStep(ActionEvent actionEvent) {
        animation.pause();
        myMainView.step();
        timer.stop();
    }
    private void handleReset(ActionEvent actionEvent) {
 //       myMainView.displayGrid(myGrid);
        resetTime();
        animation.pause();
//        myMainView.getOriginalGrid();
    }

    public void timer() {
        this.lblTime = new Label("Elapsed time: 0 s");
        this.timer = new AnimationTimer() {

            private long lastTime = 0;
            @Override
            public void handle(long now) {
                if (lastTime != 0) {
                    if (now > lastTime + 1_000_000_000) {
                        seconds++;
                        lblTime.setText("Elapsed time: "+ Integer.toString(seconds) + " s");
                        lastTime = now;
                    }
                } else {
                    lastTime = now;
                }
            }
            @Override
            public void stop() {
                super.stop();
                lastTime = 0;

            }
        };
    }

    public void makeSlider() {
        this.slider = new Slider();
        slider.setMin(0);
        slider.setMax(10);
        slider.setValue(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(5);
        slider.setBlockIncrement(5);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                animation.setRate((Double) new_val);
            }
        });
    }

    public void resetTime () {
        timer.stop();
        lblTime.setText("Elapsed time: " + 0 + " s");
        seconds = 0;
    }

    public void choosingNewSim(int choice) {
        GridCreator creator = new GridCreator();
        currentGrid = creator.GridSelector(choice);
        myMainView.setDisplaygrid(currentGrid);
        GridPane newGrid = myMainView.displayGrid(currentGrid);
        myMainView.replaceGrid(newGrid);
        resetTime();
    }
}
