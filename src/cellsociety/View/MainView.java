package cellsociety.View;

import cellsociety.Model.*;
import cellsociety.Model.Cell;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.awt.*;
import java.util.HashMap;

/**
 * Mainview class which acts as a VBox where the grid and toolbar are held.
 * @author Chris Warren, Aneesh Gupta, Shruthi Kumar
 */
public class MainView extends VBox {

    private Grid displayGrid;
    private int rows;
    private int cols;
    public Toolbar myToolbar;
    private GridPane theGrid;

    /**
     * Sets instance variables myToolbar, displayGrid, and the grid and adds myToolbar and theGrid to the mainView
     */
    public MainView() {
        myToolbar = new Toolbar(this);
        displayGrid = myToolbar.getCurrentGrid();
        this.theGrid = displayGrid(displayGrid);
        this.theGrid.setAlignment(Pos.CENTER);
        this.getChildren().addAll(myToolbar, theGrid);
    }

    /**
     * Updates the grid and removes the old grid and adds the new one. This happens every time the step function is called
     */
    public void step() {
        displayGrid.updateGrid(rows, cols);
        GridPane newGrid = displayGrid(displayGrid);
        newGrid.setAlignment(Pos.CENTER);
        this.getChildren().remove(1);
        this.getChildren().addAll(newGrid);
    }

    /**
     * Creates the Gridpane in which the cells are held in. Sets the rows and columns as well as the gaps between each
     * cell and gets the color of each cell depending on the state and represents the cell in a rectangle.
     * @param myGrid the grid being displayed in which was made in the grid class.
     * @return returns the GridPane to be displayed
     */
    public GridPane displayGrid(Grid myGrid) {
        GridPane gridPane = new GridPane();
        this.rows = myGrid.getMyHeight();
        this.cols = myGrid.getMyWidth();
        HashMap<Point, Cell> myMap = myGrid.getCellGrid();
        gridPane.addColumn(cols);
        gridPane.addRow(rows);
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        int size = 500;
        for(int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                Point tempPt = new Point(i,j);
                Color tempColor = myMap.get(tempPt).getCellColor();
                System.out.println("");
                Rectangle rect = new Rectangle(size/rows,size/cols, tempColor);
                gridPane.add(rect, j, i);
            }
        }
        return gridPane;
    }

    /**
     * Replaces the current grid with a new one
     * @param newgrid the new grid being displayed
     */
    public void replaceGrid(GridPane newgrid) {
        this.getChildren().remove(1);
        this.getChildren().addAll(newgrid);
    }

    /**
     * chooses the grid to be displayed in the toolbar class
     * @param currentGrid current grid that needs to be displayed
     */
    public void setDisplayGrid(Grid currentGrid) {
        displayGrid = currentGrid;
    }
}
