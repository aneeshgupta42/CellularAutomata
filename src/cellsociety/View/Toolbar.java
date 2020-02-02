package cellsociety.View;

import cellsociety.Model.*;
import cellsociety.View.MainView;
import cellsociety.configuration.Game;
import cellsociety.configuration.XMLReader;
import java.awt.event.MouseEvent;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import java.awt.Point;

import javafx.scene.control.ComboBox;

import javafx.scene.control.ToolBar;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class Toolbar extends ToolBar {


    private MainView myMainView;
    private AnimationTimer timer;

    private int seconds;
    private Object nameofGame = " ";
    private Grid currentGrid;


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

        switchSimulation.setOnAction(event -> nameofGame = switchSimulation.getValue());
            XMLReader reader = new XMLReader("media");
            Game game = reader.getGame("data/gameOfLife.xml");
            currentGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice());
            


        this.getItems().addAll(play, stop, step, reset, switchSimulation);
    }

    public Grid getCurrentGrid() {
        return currentGrid;
    }




    private void handlePlay(ActionEvent actionEvent) {
      this.myMainView.step();
    }
    private void handleStop(ActionEvent actionEvent) {
        myMainView.getTimer().stop();
    }

    private void handleStep(ActionEvent actionEvent) {
//      this.mainView.getSimulation().step();
//      this.mainView.draw();
    }
    private void handleReset(ActionEvent actionEvent) {

    }
}
