package cellsociety.View;

import cellsociety.Model.*;
import cellsociety.Model.Cell;
import cellsociety.configuration.Game;
import cellsociety.configuration.XMLReader;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;

import java.awt.*;

import java.util.HashMap;

public class MainView extends VBox {

    private Button playbutton;
    private Button btnStart;
    private Button btnStop;
    private ComboBox switchSimulation;
    private Canvas canvas;
    private Affine affine;
    private AnimationTimer timer;
    private Label lblTime;
    private int seconds;
    private InfoBar infobar;
    private Grid gridmap;

    public MainView() {

        this.canvas = new Canvas(500,500);
//        this.gridmap = new Grid();
//        gridmap.populateGridCells(500,500,0);

        timer();
        Toolbar Toolbar = new Toolbar(this);
        this.infobar = new InfoBar();
        GridPane theGrid = displayGrid();
        theGrid.setLayoutX(0); theGrid.setLayoutY(100);
        this.getChildren().addAll(Toolbar, theGrid, this.lblTime);

    }

    public GridPane displayGrid() {
        GridPane gridPane = new GridPane();
        XMLReader reader = new XMLReader("media");
        Game game = reader.getGame("data/gameOfLife.xml");
        int rows = game.getMyRows();
        int cols = game.getMyCols();
        Grid myGrid = new Grid(rows, cols, game.getMyChoice());
        HashMap<Point, Cell> myMap = myGrid.getCellGrid();
        System.out.println(myMap.size());
        gridPane.addColumn(cols);
        gridPane.addRow(rows);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        int size = 10;
        for(int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                Point tempPt = new Point(i,j);
                Color tempColor = myMap.get(tempPt).getCellColor();
                System.out.println("");
                Rectangle rect = new Rectangle(10,10, tempColor);
                gridPane.add(rect, j, i);
            }
        }
        return gridPane;
    }


    public void timer() {
        this.lblTime = new Label("0 s");
        this.timer = new AnimationTimer() {

            private long lastTime = 0;
            @Override
            public void handle(long now) {
                if (lastTime != 0) {
                    if (now > lastTime + 1_000_000_000) {
                        seconds++;
                        lblTime.setText(Integer.toString(seconds) + " s");
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
                seconds = 0;
            }
        };
    }
    public AnimationTimer getTimer(){
        return this.timer;
    }

    public Label getLbltime() {
        return this.lblTime;
    }


//    public Simulation getSimulation() {
//        return this.simulation;
//    }
}
