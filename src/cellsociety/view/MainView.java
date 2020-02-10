package cellsociety.view;

import cellsociety.configuration.Game;
import cellsociety.model.*;
import cellsociety.model.neighbors.HexagonNeighbor;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane theGrid;
    //private GridPane theGrid;
    private Configpanel myPanel;


    private static final int SIZEOFGRID = 500;
    private static final int HALF = 2;
    private static final int ZERO = 0;
    private static final int SQUARE = 0;
    private static final int TRIANGLE = 1;
    private static final int HEXAGON = 2;


    /**
     * Sets instance variables myToolbar, displayGrid, and the grid and adds myToolbar and theGrid to the mainView
     */
    public MainView(Display display) {

        myGame = display.getMyGame();
        myToolbar = new Toolbar(this);
        myPanel = new Configpanel(this);
        displayGrid = display.getDisplayGrid();
        this.theGrid = displayGrid(displayGrid);
        //this.theGrid.setAlignment(Pos.TOP_LEFT);
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
        AnchorPane newGrid = displayGrid(displayGrid);
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
    public AnchorPane displayGrid(Grid myGrid) {
        AnchorPane gridPane = new AnchorPane();
        this.rows = myGrid.getMyHeight();
        this.cols = myGrid.getMyWidth();

        for(int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                Color tempColor = Color.web(myGrid.getPointColor(i,j));
                System.out.println("");
                double rowSize = (double) (SIZEOFGRID / rows - 1);
                double colSize = (double) (SIZEOFGRID/cols - 1);
                double halfSize = (double) (SIZEOFGRID / rows - 1) / 2;
                double fullSize = (double) (SIZEOFGRID / rows - 1);
                if(myGrid.getMyNeighborhoodChoice() == SQUARE) {
                    handleSquare(gridPane, tempColor, rowSize, colSize, i, j);
                }
                else if(myGrid.getMyNeighborhoodChoice() == TRIANGLE) {
                    handleTriangle(gridPane, tempColor, halfSize, i, j);
                } else {
                    handleHexagon(gridPane, tempColor, halfSize, fullSize, i, j);
                }
                /*


                double halfSize = (double) (SIZEOFGRID / rows - 1) / 2;
                double fullSize = (double) (SIZEOFGRID / rows - 1);

                /*
                Polygon triangle = new Polygon();
                triangle.setFill(tempColor);
                if(checkDownwardFacing(i, j)) {
                    triangle.getPoints().addAll(new Double[]{
                        ((double) (SIZEOFGRID / rows - 1) / 2), (double) SIZEOFGRID/rows - 1,
                        0.0, 0.0,
                        ((double) SIZEOFGRID / rows) - 1, 0.0});
                    AnchorPane.setLeftAnchor(triangle, (double) + j * (SIZEOFGRID/rows) - (halfSize*j));
                } else {
                    triangle.getPoints().addAll(new Double[]{
                        (double) (SIZEOFGRID / rows - 1) / 2, 0.0,
                        0.0, (double) (SIZEOFGRID / cols - 1),
                        (double) SIZEOFGRID / rows - 1, (double) SIZEOFGRID / cols - 1});
                    AnchorPane.setLeftAnchor(triangle, (double) + j * (SIZEOFGRID/rows) - (halfSize*j));
                }
                int finalI = i;
                int finalJ = j;
                gridPane.getChildren().add(triangle);

                AnchorPane.setTopAnchor(triangle, (double) + i * (SIZEOFGRID/rows));


                Polygon hexagon = new Polygon();
                hexagon.setFill(tempColor);
                if(j % 2 == ZERO) {
                    hexagon.getPoints().addAll(new Double[]{
                        halfSize / 2, 0.0,
                        fullSize - halfSize / 2, 0.0,
                        fullSize, fullSize / 2,
                        fullSize - halfSize / 2, fullSize,
                        halfSize / 2, fullSize,
                        0.0, fullSize / 2
                    });

                    AnchorPane.setTopAnchor(hexagon,(double) i * fullSize + halfSize - 1);
                    AnchorPane.setLeftAnchor(hexagon, (double) + j * (SIZEOFGRID/rows));

                    //AnchorPane.setTopAnchor(hexagon, (double) +\\ i * (SIZEOFGRID/rows) + j*(fullSize/2));

                }
                else {
                    hexagon.getPoints().addAll(new Double[]{
                        halfSize / 2, 0.0,
                        fullSize - halfSize / 2, 0.0,
                        fullSize, fullSize / 2,
                        fullSize - halfSize / 2, fullSize,
                        halfSize / 2, fullSize,
                        0.0, fullSize / 2
                    });
                    AnchorPane.setLeftAnchor(hexagon, (double) + j * (SIZEOFGRID/rows));
                    AnchorPane.setTopAnchor(hexagon, (double) i * (SIZEOFGRID/rows));
                   // AnchorPane.setTopAnchor(hexagon, (double) + i * (SIZEOFGRID/rows) + j*(fullSize));
                }
                gridPane.getChildren().add(hexagon);



*/

            }
        }


        return gridPane;
    }

    private boolean checkDownwardFacing(int row, int col) {
        return (row % HALF) == ZERO && (col % HALF) != ZERO
            || (row % HALF) != ZERO && (col % HALF) == ZERO;
    }

    private void handleSquare(AnchorPane gridPane, Color tempColor, double rowSize, double colSize, int row, int col) {
        Rectangle rect = new Rectangle(rowSize - 1,colSize - 1, tempColor);
        gridPane.getChildren().add(rect);
        AnchorPane.setLeftAnchor(rect, colSize * col);
        AnchorPane.setTopAnchor(rect, rowSize*row);
    }

    private void handleTriangle(AnchorPane gridPane, Color tempColor, double halfSize, int row, int col) {
        Polygon triangle = new Polygon();
        triangle.setFill(tempColor);
        if(checkDownwardFacing(row, col)) {
            triangle.getPoints().addAll(new Double[]{
                ((double) (SIZEOFGRID / rows - 1) / 2), (double) SIZEOFGRID/rows - 1,
                0.0, 0.0,
                ((double) SIZEOFGRID / rows) - 1, 0.0});
            AnchorPane.setLeftAnchor(triangle, (double) + col * (SIZEOFGRID/rows) - (halfSize*col));
        } else {
            triangle.getPoints().addAll(new Double[]{
                (double) (SIZEOFGRID / rows - 1) / 2, 0.0,
                0.0, (double) (SIZEOFGRID / cols - 1),
                (double) SIZEOFGRID / rows - 1, (double) SIZEOFGRID / cols - 1});
            AnchorPane.setLeftAnchor(triangle, (double) + col * (SIZEOFGRID/rows) - (halfSize*col));
        }
        gridPane.getChildren().add(triangle);
        AnchorPane.setTopAnchor(triangle, (double) + row * (SIZEOFGRID/rows));
    }

    private void handleHexagon(AnchorPane gridPane, Color tempColor, double halfSize, double fullSize, int row, int col) {
        Polygon hexagon = new Polygon();
        hexagon.setFill(tempColor);
        hexagon.getPoints().addAll(new Double[]{
            halfSize / 2, 0.0,
            fullSize - halfSize / 2, 0.0,
            fullSize, fullSize / 2,
            fullSize - halfSize / 2, fullSize,
            halfSize / 2, fullSize,
            0.0, fullSize / 2
        });
        if(col % 2 == ZERO) {
            AnchorPane.setTopAnchor(hexagon,(double) row * fullSize + halfSize - 1);
            AnchorPane.setLeftAnchor(hexagon, (double) + col * (SIZEOFGRID/rows));
        }
        else {
            AnchorPane.setLeftAnchor(hexagon, (double) + col * (SIZEOFGRID/rows));
            AnchorPane.setTopAnchor(hexagon, (double) row * (SIZEOFGRID/rows));
        }
        gridPane.getChildren().add(hexagon);
    }

    /**
     * Replaces the current grid with a new one
     * @param newgrid the new grid being displayed
     */
    public void replaceGrid(AnchorPane newgrid) {
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
