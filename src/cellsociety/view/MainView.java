package cellsociety.view;

import cellsociety.configuration.Game;
import cellsociety.model.*;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * Mainview class which acts as a VBox where the grid and toolbar are held.
 * @author Chris Warren, Aneesh Gupta, Shruthi Kumar
 */
public class MainView extends BorderPane {

    private Grid displayGrid;
    private Game myGame;
    private int rows;
    private int cols;
    private Toolbar myToolbar;
    private GridPane theGrid;
    private Configpanel myPanel;

    private static final int SIZEOFGRID = 500;

    /**
     * Sets instance variables myToolbar, displayGrid, and the grid and adds myToolbar and theGrid to the mainView
     */
    public MainView(Display display) {

        myGame = display.getMyGame();
        myToolbar = new Toolbar(this);
        myPanel = new Configpanel(this);
        displayGrid = display.getDisplayGrid();
        this.theGrid = displayGrid(displayGrid);
        this.theGrid.setAlignment(Pos.TOP_LEFT);
        this.setTop(myToolbar);
        this.setCenter(theGrid);
        this.setLeft(null);
        this.myPanel.setAlignment(Pos.TOP_LEFT);
        this.setRight(myPanel);

    }



    /**
     * Updates the grid and removes the old grid and adds the new one. This happens every time the step function is called
     */
    public void step() {
        displayGrid.updateGrid(rows, cols);
        GridPane newGrid = displayGrid(displayGrid);
        this.getChildren().remove(1);
        this.setCenter(newGrid);
        this.setRight(myPanel);
        myPanel.addDataToGraph(myToolbar.getTimeElapsed());
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
                int finalI = i;
                int finalJ = j;
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
        this.setCenter(newgrid);
        this.setRight(myPanel);
    }

    /**
     * chooses the grid to be displayed in the toolbar class
     * @param currentGrid current grid that needs to be displayed
     */
    public void setDisplayGrid(Grid currentGrid) {
        displayGrid = currentGrid;
    }

    public Grid getDisplayGrid() {
        return displayGrid;
    }

    public Game getMyGame() {
        return myGame;
    }
    
    public void setMyGame(Game myGame) {
        this.myGame = myGame;
    }

//    private void upTriangle(Polygon triangle, int row, int col) {
//        double xTip = ((cols+1)*(WIDTH / 2) + (cols+1)*getSpacing());
//        double yTip = rows*HEIGHT + (row+1)*getSpacing();
//        double xLeft = cols*(WIDTH / 2) + (cols+1)*getSpacing();
//        double yLeft = (rows+1)*HEIGHT + (rows+1)*getSpacing();
//        double xRight = (cols+2)*(WIDTH / 2) + (cols+1)*getSpacing();
//        double yRight = (rows+1)*HEIGHT + (rows+1)*getSpacing();
//        triangle.getPoints().addAll(new Double[] {
//                xTip, yTip,
//                xLeft, yLeft,
//                xRight, yRight,
//        });
//    }
}
