package cellsociety.View;

import cellsociety.Model.*;
import cellsociety.Model.Cell;
import cellsociety.configuration.Game;
import cellsociety.configuration.GridCreator;
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
    private int seconds;
    private InfoBar infobar;
    private Grid gridmap;
    private Grid displaygrid;
    private int rows;
    private int cols;
    public Toolbar myToolbar;

    public MainView() {

        this.canvas = new Canvas(500,500);

        myToolbar = new Toolbar(this);

        displaygrid = myToolbar.getCurrentGrid();
        this.infobar = new InfoBar();
        GridPane theGrid = displayGrid(displaygrid);
        theGrid.setLayoutX(0); theGrid.setLayoutY(100);
        this.getChildren().addAll(myToolbar, theGrid);
    }

    public void step() {
        //System.out.println("Update");
        displaygrid.updateGrid(rows, cols);
        GridPane newGrid = displayGrid(displaygrid);
        newGrid.setLayoutX(0); newGrid.setLayoutY(100);
        this.getChildren().remove(1);
        this.getChildren().addAll(newGrid);
    }


    public GridPane displayGrid(Grid myGrid) {

        GridPane gridPane = new GridPane();
        this.rows = myGrid.getMyHeight();
        this.cols = myGrid.getMyWidth();
        HashMap<Point, Cell> myMap = myGrid.getCellGrid();
        //System.out.println(myMap.size());
        gridPane.addColumn(cols);
        gridPane.addRow(rows);
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        int size = 10;
        for(int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                Point tempPt = new Point(i,j);
                Color tempColor = myMap.get(tempPt).getCellColor();
                System.out.println("");
                Rectangle rect = new Rectangle(20,20, tempColor);
                gridPane.add(rect, j, i);
            }
        }
        return gridPane;
    }

}
