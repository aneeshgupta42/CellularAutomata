package cellsociety.View;

import cellsociety.Model.*;
import cellsociety.Model.Cell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.awt.*;
import java.util.HashMap;

public class MainView extends VBox {

    private Grid gridmap;
    private Grid displaygrid;
    private int rows;
    private int cols;
    public Toolbar myToolbar;
    private Grid originalGrid;
    private GridPane theGrid;

    public MainView() {



        myToolbar = new Toolbar(this);


        displaygrid = myToolbar.getCurrentGrid();
        originalGrid = myToolbar.getCurrentGrid();
        originalGrid = displaygrid;


        this.theGrid = displayGrid(displaygrid);
        theGrid.setLayoutX(0); theGrid.setLayoutY(100);
        this.getChildren().addAll(myToolbar, theGrid);
    }
    public void setDisplaygrid(Grid dispGrid){
        displaygrid = dispGrid;
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

    public void getOriginalGrid() {
        this.getChildren().remove(theGrid);
        this.theGrid = displayGrid(originalGrid);
        this.getChildren().addAll(theGrid);
    }

    public void replaceGrid(GridPane newgrid) {
        this.getChildren().remove(1);
        this.getChildren().addAll(newgrid);
    }
}
