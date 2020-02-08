package cellsociety.view;

import cellsociety.model.*;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Mainview class which acts as a VBox where the grid and toolbar are held.
 * @author Chris Warren, Aneesh Gupta, Shruthi Kumar
 */
public class MainView extends VBox {

    private Grid displayGrid;
    private int rows;
    private int cols;
    private Toolbar myToolbar;
    private GridPane theGrid;

    private final int SIZEOFGRID = 500;

    /**
     * Sets instance variables myToolbar, displayGrid, and the grid and adds myToolbar and theGrid to the mainView
     */
    public MainView(Display display) {
        myToolbar = new Toolbar(this);
//        displayGrid = myToolbar.getCurrentGrid();
        displayGrid = display.getDisplayGrid();
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
        gridPane.addColumn(cols);
        gridPane.addRow(rows);
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        for(int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                Color tempColor = Color.web(myGrid.getPointColor(i,j));
                System.out.println("");
                Rectangle rect = new Rectangle(SIZEOFGRID/rows,SIZEOFGRID/cols, tempColor);
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
