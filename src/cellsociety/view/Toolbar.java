package cellsociety.view;

import cellsociety.model.*;
import cellsociety.configuration.GridCreator;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * Toolbar class where all the functionality is held
 * @author Chris Warren
 */
public class Toolbar extends ToolBar {


    private MainView myMainView;


    private static final int FRAMES_PER_SECOND = 60;
    private static final double MILLISECOND_DELAY = 10000/FRAMES_PER_SECOND;

    private int seconds;
    private Grid currentGrid;
    private Timeline animation;
    private Label lblTime;
    private AnimationTimer timer;
    private Slider slider;
    private int myChoice;

    /**
     * Creates the toolbar with all of the functionality buttons and sets it in the mainView.
     * @param mainView where the toolbar will be displayed
     * @author Chris Warren
     */
    public Toolbar(MainView mainView) {

        myMainView = mainView;
        myChoice = 0;

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
        });

        GridCreator creator = new GridCreator();
        currentGrid = creator.GridSelector(0);

        timer();
        animationFunctions();
        makeSlider();
        this.getItems().addAll(play, stop, step, reset, switchSimulation, lblTime, slider);

    }

    /**
     * Method that sets up the animation, in which the myMainview step method is called every second which updates the
     * grid on the screen.
     *  @author Chris Warren
     */
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

    /**
     * Getter method to return the current grid
     * @return returns the current grid
     * @author Chris Warren
     */
    public Grid getCurrentGrid() {
        return currentGrid;
    }

    /**
     * Starts the animation and timer
     * @param actionEvent The play button pressed
     * @author Chris Warren
     */
    private void handlePlay(ActionEvent actionEvent) {
        animation.play();
        timer.start();
    }

    /**
     * Stops the animation and timer
     * @param actionEvent The stop button pressed
     * @author Chris Warren
     */
    private void handleStop(ActionEvent actionEvent) {
        animation.stop();
        timer.stop();
    }

    /**
     * Steps the function one iteration forward
     * @param actionEvent the step button is pressed
     * @author Chris Warren
     */
    private void handleStep(ActionEvent actionEvent) {
        animation.pause();
        myMainView.step();
        timer.stop();
    }

    /**
     * Resets the grid to its original state
     * @param actionEvent the reset button is pressed
     * @author Chris Warren
     */
    private void handleReset(ActionEvent actionEvent) {
        choosingNewSim(myChoice);
        animation.pause();

    }

    /**
     * The timer is made and displayed in the toolbar. It runs and stops based on the button action events and updates
     * every second.
     * @author Chris Warren
     */
    public void timer() {
        this.lblTime = new Label("Elapsed time: 0 s");

        this.timer = new AnimationTimer() {
            private long lastTime = 0;
            @Override
            public void handle(long now) {
                if (lastTime != 0) {
                    if (now > lastTime + 1_000_000_000) {
                        seconds++;
                        lblTime.setText("Elapsed time: "+ Integer.toString((int) (seconds * animation.getRate())) + " s");
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

    /**
     * Makes the slider which dictates the speed that the simulation and grid is updating. Also changes the elapsed speed
     * timer to accurately display the elapsed time based on the relative speed.
     * @author Chris Warren
     */
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

    /**
     * Resets the timer
     * @author Chris Warren
     */
    public void resetTime () {
        timer.stop();
        lblTime.setText("Elapsed time: " + 0 + " s");
        seconds = 0;
    }

    /**
     * Based on the Combobox where one selects the type of simulation being displayed, once the option is clicked it
     * switched simulations based on the appropriate choice.
     * @param choice int which determines the which simulation and sets the characteristics of each cell.
     * @author Chris Warren
     */
    public void choosingNewSim(int choice) {
        GridCreator creator = new GridCreator();
        currentGrid = creator.GridSelector(choice);
        myMainView.setDisplayGrid(currentGrid);
        GridPane newGrid = myMainView.displayGrid(currentGrid);
        myMainView.replaceGrid(newGrid);
        myChoice = choice;
        resetTime();
        newGrid.setAlignment(Pos.CENTER);
    }

}
