package cellsociety.view;

import cellsociety.configuration.Game;
import cellsociety.configuration.XMLException;
import cellsociety.configuration.XMLWriter;
import cellsociety.model.*;
import cellsociety.configuration.GridCreator;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ResourceBundle;

/**
 * Toolbar class where all the functionality is held
 * @author Chris Warren, Aneesh Gupta, Shruthi Kumar
 */
public class Toolbar extends ToolBar {


    private MainView myMainView;
    private static final int FRAMES_PER_SECOND = 60;
    private static final double MILLISECOND_DELAY = 10000/FRAMES_PER_SECOND;
    private int seconds;
    private Grid currentGrid;
    private Game myGame;
    private Timeline animation;
    private Label lblTime;
    private AnimationTimer timer;
    private Slider slider;
    private int myChoice;
    private ComboBox switchSimulation;
    private Configpanel myPanel;
    private boolean submitbuttonstatus;
    private static final int GAMEOFLIFENUM = 0;
    private static final int PERCOLATIONNUM = 1;
    private static final int SEGREGATIONNUM = 2;
    private static final int PREDATORPREYNUM = 3;
    private static final int FIRENUM = 4;
    private static final int RPSNUM = 5;
    private static final int SURGARNUM = 6;
    private static final int SLIDERMINNUM = 0;
    private static final int SLIDERMAXNUM = 10;
    private static final int SLIDERUNIT = 5;
    private int timernumber;

    private ResourceBundle toolbarBundle;
    /**
     * Creates the toolbar with all of the functionality buttons and sets it in the mainView.
     * @param mainView where the toolbar will be displayed
     */
    public Toolbar(MainView mainView) {

        myMainView = mainView;
        myGame = myMainView.getMyGame();
        currentGrid = myMainView.getDisplayGrid();

        toolbarBundle = ResourceBundle.getBundle("cellsociety/resources/Toolbartext");

        Button play = new Button(toolbarBundle.getString("PlayButton"));
        play.setOnAction(this::handlePlay);

        Button stop = new Button(toolbarBundle.getString("StopButton"));
        stop.setOnAction(this::handleStop);

        Button step = new Button(toolbarBundle.getString("StepButton"));
        step.setOnAction(this::handleStep);

        Button reset = new Button(toolbarBundle.getString("ResetButton"));
        reset.setOnAction(this::handleReset);

        Button simUpload = new Button(toolbarBundle.getString("UploadSim"));
        simUpload.setOnAction(this:: uploadNewSim);

        Button writeSim = new Button (toolbarBundle.getString("Downloadsim"));
        writeSim.setOnAction(this:: writeOutSim);

        timer();
        animationFunctions();
        makeSlider();
        switchingSimulation();

        this.getItems().addAll(play, stop, step, reset, switchSimulation, lblTime, slider, simUpload, writeSim);

    }

    /**
     * Method that sets up the animation, in which the myMainview step method is called every second which updates the
     * grid on the screen.
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
     */
    public Grid getCurrentGrid() {
        return currentGrid;
    }

    /**
     * Starts the animation and timer
     * @param actionEvent The play button pressed
     */
    private void handlePlay(ActionEvent actionEvent) {
        animation.play();
        timer.start();
    }

    /**
     * Stops the animation and timer
     * @param actionEvent The stop button pressed
     */
    private void handleStop(ActionEvent actionEvent) {
        animation.stop();
        timer.stop();
    }

    /**
     * Steps the function one iteration forward
     * @param actionEvent the step button is pressed
     */
    private void handleStep(ActionEvent actionEvent) {
        animation.pause();
        myMainView.step();
        timer.stop();
    }

    /**
     * Resets the grid to its original state
     * @param actionEvent the reset button is pressed
     */
    private void handleReset(ActionEvent actionEvent) {
        GridCreator creator = new GridCreator();
        currentGrid = creator.newGridSelector(myMainView.getMyGame());
        myMainView.setDisplayGrid(currentGrid);
        myMainView.setRight(myPanel);
        GridPane newGrid = myMainView.displayGrid(currentGrid);
        myMainView.replaceGrid(newGrid);
        myChoice = currentGrid.getChoice();
        resetTime();
        animation.pause();
    }

    /**
     * The timer is made and displayed in the toolbar. It runs and stops based on the button action events and updates
     * every second.
     */
    public void timer() {
        this.lblTime = new Label(toolbarBundle.getString("InitialTime"));

        this.timer = new AnimationTimer() {
            private long lastTime = 0;
            @Override
            public void handle(long now) {
                if (lastTime != 0) {
                    if (now > lastTime + 1_000_000_000) {
                        seconds++;
                        timernumber = (int) (seconds * animation.getRate());
                        lblTime.setText(toolbarBundle.getString("Elapsedtime") + Integer.toString((int) (seconds * animation.getRate())) + " " +
                                toolbarBundle.getString("Seconds"));
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
     */
    public void makeSlider() {
        this.slider = new Slider();
        slider.setMin(SLIDERMINNUM);
        slider.setMax(SLIDERMAXNUM);
        slider.setValue(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(SLIDERUNIT);
        slider.setBlockIncrement(SLIDERUNIT);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                animation.setRate((Double) new_val);
            }
        });
    }

    /**
     * Resets the timer
     */
    public void resetTime () {
        timer.stop();
        lblTime.setText(toolbarBundle.getString("Elapsedtime") + " "+ 0 + " "+toolbarBundle.getString("Seconds"));
        seconds = 0;
    }

    public void uploadNewSim(ActionEvent actionEvent){
        animation.stop();
        Display tempDisp = new Display();
        currentGrid = tempDisp.uploadNewFile();
        myGame = tempDisp.getMyGame();
        myMainView.setDisplayGrid(currentGrid);
        myMainView.setRight(myPanel);
        myMainView.setMyGame(myGame);
        GridPane newGrid = myMainView.displayGrid(currentGrid);
        myMainView.replaceGrid(newGrid);
        myChoice = currentGrid.getChoice();
        resetTime();
    }

    private void writeOutSim(ActionEvent actionEvent) {
        try{
            animation.stop();
            timer.stop();
            XMLWriter writer = new XMLWriter(myMainView.getDisplayGrid(), myGame);
            writer.outputFile();
        } catch (XMLException | ParserConfigurationException e) {
            throw new XMLException("Couldn't parse");
        } catch (Exception e) {
            throw new XMLException("Couldn't write file");
        }
    }
    /**
     * Based on the Combobox where one selects the type of simulation being displayed, once the option is clicked it
     * switched simulations based on the appropriate choice.
     * @param choice int which determines the which simulation and sets the characteristics of each cell.
     */
    public void choosingNewSim(int choice) {
        GridCreator creator = new GridCreator();
        currentGrid = creator.defaultGridSelector(choice);
        myGame = creator.getMyGame();
        myMainView.setMyGame(myGame);
        myMainView.setDisplayGrid(currentGrid);
        myMainView.setRight(myPanel);
        GridPane newGrid = myMainView.displayGrid(currentGrid);
        myMainView.replaceGrid(newGrid);
        myChoice = choice;
        resetTime();

    }

    public void switchingSimulation() {
        this.switchSimulation = new ComboBox();
        switchSimulation.getItems().addAll(toolbarBundle.getString("GameofLifeSim"), toolbarBundle.getString("PercolationSim"), toolbarBundle.getString("SegregationSim"),
                toolbarBundle.getString("PredatorPreySim"), toolbarBundle.getString("FireSim"), toolbarBundle.getString("RPSSim"),
                toolbarBundle.getString("SugarscapeSim"));

        switchSimulation.setPromptText(toolbarBundle.getString("ChooseSim"));
        switchSimulation.setEditable(true);

        switchSimulation.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            animation.stop();
            if (newValue == toolbarBundle.getString("GameofLifeSim")) {
                choosingNewSim(GAMEOFLIFENUM);
            } else if (newValue == toolbarBundle.getString("PercolationSim")) {
                choosingNewSim(PERCOLATIONNUM);
            }else if (newValue == toolbarBundle.getString("SegregationSim")){
                choosingNewSim(SEGREGATIONNUM);
            } else if (newValue == toolbarBundle.getString("PredatorPreySim")) {
                choosingNewSim(PREDATORPREYNUM);
            } else if (newValue == toolbarBundle.getString("FireSim")) {
                choosingNewSim(FIRENUM);
            } else if (newValue == toolbarBundle.getString("RPSSim")) {
                choosingNewSim(RPSNUM);
            } else if (newValue == toolbarBundle.getString("SugarscapeSim")) {
                choosingNewSim(SURGARNUM);
            }
        });
    }

    public int getMyChoice() {
        return myChoice;
    }

    public boolean getsumbitbutton() {
        return submitbuttonstatus;
    }

    public int getTimeElapsed() {
        return timernumber;
    }
}

