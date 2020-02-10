package cellsociety.model;

import cellsociety.configuration.Game;
import cellsociety.configuration.XMLException;

import javax.sql.rowset.RowSetWarning;
import java.awt.Point;
import java.util.HashMap;
import java.util.Random;

/***
 * Represents the Grid for a CA simulation
 * A grid is represented by a HashMap data structure,
 * containing (coordinate) Point: Cell mapping
 * This Class contains methods for populating the grid
 * Choosing the type of cell to go in
 * Updating the grid by iterating over it in two passes
 * @author Shruti Kumar
 * @author Aneesh Gupta
 * @author Chris Warren
 */
public class Grid {
    private HashMap<Point, Cell> cellGrid;
    private Random numChooser = new Random();
    private float myProb;
    private int myWidth;
    private int myHeight;
    private double myThreshold;
    private int myThresholdRPS;
    private int myChoice;
    private int isLayout;
    private String myLayout;
    private int myNeighborhoodChoice;
    private static final String ROWCOLSDONTMATCH = "Dimensions of layout and specifications don't match";
    private static final int GAMEOFLIFE = 0;
    private static final int PERCOLATION = 1;
    private static final int SEGREGATION = 2;
    private static final int PREDATORPREY = 3;
    private static final int FIRE = 4;
    private static final int RPS = 5;
    private static final int SUGARSCAPE = 6;
    private static int numStates = 2;
    private static final int NUMSTATES = 3;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int DEAD = 0;
    private static final int ALIVE = 1;




    /***
     * Constructs Grid object
     * Used for Game of Life, Percolation, Predator Prey
     * @param width: number of cols
     * @param height: number of rows
     * @param choice: choice of simulations
     */
    public Grid(int width, int height, int choice, String layout, int islayout, int neighborhoodChoice) {
        cellGrid = new HashMap<Point, Cell>();
        myChoice = choice;
        myWidth = width;
        myHeight = height;
        myLayout = layout;
        isLayout = islayout;
        myNeighborhoodChoice = neighborhoodChoice;
        populateGridCells(width, height, choice, myNeighborhoodChoice);
    }

    /***
     * Constructs Grid object for Fire grids
     * (overloaded constructor)
     * @param width: number of cols
     * @param height: number of rows
     * @param choice: choice of simulations
     * @param prob: ProbCatch for fire
     */
    public Grid(int width, int height, int choice, float prob, String layout, int islayout, int neighborhoodChoice) {
        cellGrid = new HashMap<Point, Cell>();
        myProb = prob;
        myChoice = choice;
        myWidth = width;
        myHeight = height;
        myLayout = layout;
        isLayout = islayout;
        myNeighborhoodChoice = neighborhoodChoice;
        populateGridCells(width, height, choice, myNeighborhoodChoice);
    }

    /***
     * Overloaded constructor to get
     * the Grid object for segregation simulation
     * @param width: number of cols
     * @param height: number of rows
     * @param choice: choice of simulation
     * @param thresh: "Satisfaction" threshold for Segregation simulation
     */
    public Grid(int width, int height, int choice, double thresh, String layout, int islayout, int neighborhoodChoice) {
        cellGrid = new HashMap<Point, Cell>();
        myThreshold = thresh;
        myChoice = choice;
        myWidth = width;
        myHeight = height;
        myLayout = layout;
        isLayout = islayout;
        myNeighborhoodChoice = neighborhoodChoice;
        populateGridCells(width, height, choice, myNeighborhoodChoice);
    }

    /***
     * Overloaded constructor to get
     * the Grid object for RPS simulation
     * @param width: number of cols
     * @param height: number of rows
     * @param choice: choice of simulation
     * @param thresh: Threshold for RPS simulation
     */
    public Grid(int width, int height, int choice, int thresh, String layout, int islayout, int neighborhoodChoice) {
        cellGrid = new HashMap<Point, Cell>();
        myThresholdRPS = thresh;
        myChoice = choice;
        myWidth = width;
        myHeight = height;
        myLayout = layout;
        isLayout = islayout;
        myNeighborhoodChoice = neighborhoodChoice;
        populateGridCells(width, height, choice, myNeighborhoodChoice);
    }

    private void populateGridCells(int width, int height, int choice, int neighborhoodChoice) {
        if (isLayout == 1) {
            populateFromLayout(width, height, choice, myLayout, neighborhoodChoice);
        } else {
            defaultPopulateCells(width, height, choice, myNeighborhoodChoice);
        }
    }
    private void populateFromLayout(int width, int height, int choice, String layout, int neighborhoodChoice) {
//        System.out.println(layout);
        String[] rows = layout.split("\n");
//        System.out.println(rows.length);
//        System.out.println(rows[0].trim().split(" ").length);
        if (rows.length != height || rows[0].split(" ").length != width) {
            throw new XMLException(ROWCOLSDONTMATCH, Game.DATA_TYPE);
        }
        Cell tempCell;
        for (int i = 0; i < height; i++) {
            String[] row = rows[i].trim().split(" ");
            for (int j = 0; j < width; j++) {
                tempCell = getSimulation(i, j, Integer.parseInt(row[j]), choice, myNeighborhoodChoice);
                cellGrid.put(new Point(i, j), tempCell);
            }

        }
    }

    private void defaultPopulateCells(int width, int height, int choice, int neighborhoodChoice) {
        Cell tempCell;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (choice == GAMEOFLIFE) {
                    tempCell = makeGlider(i, j, choice);
                } else if (choice == RPS) {
                    numStates = NUMSTATES;
                    tempCell = getSimulation(i, j, numChooser.nextInt(numStates), choice, myNeighborhoodChoice);
                } else if (choice == SUGARSCAPE) {
                    numStates = NUMSTATES - 1;
                    tempCell = getSimulation(i, j, numChooser.nextInt(numStates), choice, myNeighborhoodChoice);
                } else {
                    numStates = NUMSTATES;
                    tempCell = getSimulation(i, j, numChooser.nextInt(numStates), choice, myNeighborhoodChoice);
                }
                cellGrid.put(new Point(i, j), tempCell);
            }
        }
    }

    private Cell makeGlider(int i, int j, int choice) {
        Cell tempCell;
        if (i == TWO && j == THREE) {
            tempCell = getSimulation(i, j, ALIVE, choice, myNeighborhoodChoice);
        } else if (i == THREE && j == FOUR) {
            tempCell = getSimulation(i, j, ALIVE, choice, myNeighborhoodChoice);
        } else if (i == FOUR && (j == TWO || j == THREE || j == FOUR)) {
            tempCell = getSimulation(i, j, ALIVE, choice, myNeighborhoodChoice);
        } else tempCell = getSimulation(i, j, DEAD, choice, myNeighborhoodChoice);
        return tempCell;
    }

    /***
     * Carries out Grid updates,
     * updating the state of the cell according
     * to the simulation rule. A cell's next state is determined by
     * it's current state, and it's neighbours' states
     * We do this in two passes: first to read in the current states
     * And then to set the next states
     * @param width: number of cols in the grid to be updated
     * @param height: number of rows "  "   "
     */
    public void updateGrid(int width, int height) {
        HashMap<Point, Integer> newStateMap = new HashMap<>();
        int tempInitInt = 100;
        for (int i = 0; i < height; i++) { // first pass: get the new states
            for (int j = 0; j < width; j++) {
                newStateMap.put(new Point(i, j), tempInitInt);//initialize the key for new state
                int newState = cellGrid.get(new Point(i, j)).updateCell(this, i, j, width, height);
                newStateMap.put(new Point(i, j), newState); //put new state for a point in
            }
        }
        for (int i = 0; i < height; i++) { //change state from old to new
            for (int j = 0; j < width; j++) {
                cellGrid.get(new Point(i, j)).setState(newStateMap.get(new Point(i, j)));
                cellGrid.get(new Point(i, j)).setCellColor();
            }
        }
    }

    /***
     * Get the data struct holding the Grid
     * Point : Cell  mapping
     * @return cellGrid
     */
    public HashMap<Point, Cell> getCellGrid() {
        return cellGrid;
    }

    public Cell getCell(int row, int col) {
        return cellGrid.get(new Point(row, col));
    }

    public boolean gridContainsCell(int row, int col) {
        return cellGrid.containsKey(new Point(row, col));
    }


    /***
     * Get Height of Grid
     * @return myHeight
     */
    public int getMyHeight() {
        return myHeight;
    }

    /***
     * get width of Grid
     * @return myWidth
     */
    public int getMyWidth() {
        return myWidth;
    }

    /***
     * get the choice of simulation being run
     * @return myChoice
     */
    public int getChoice() {
        return this.myChoice;
    }

    private Cell getSimulation(int row, int col, int state, int choice, int neighborhoodChoice) {
        if (choice == GAMEOFLIFE) {
            return new GameCell(row, col, state, neighborhoodChoice);
        } else if (choice == PERCOLATION) {
            return new PercolationCell(row, col, state, neighborhoodChoice);
        } else if (choice == SEGREGATION) {
            return new SegregationCell(row, col, state, myThreshold, neighborhoodChoice);
        } else if (choice == PREDATORPREY) {
            return new PredatorPreyCell(row, col, state, neighborhoodChoice);
        } else if (choice == FIRE) { //FIRE
            return new FireCell(row, col, state, myProb, neighborhoodChoice);
        } else if (choice == RPS) {
            return new RPSCell(row, col, state, myThresholdRPS, neighborhoodChoice);
        } else {
            return new SugarScapeCell(row, col, state, neighborhoodChoice);
        }
    }

    public String getPointColor(int x, int y) {
        return cellGrid.get(new Point(x, y)).getCellColor();
    }

    public int getNumStates() {
        return numStates;
    }

}
