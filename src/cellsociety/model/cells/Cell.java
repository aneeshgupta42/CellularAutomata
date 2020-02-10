package cellsociety.model.cells;

import cellsociety.model.Grid;
import cellsociety.model.neighbors.HexagonNeighbor;
import cellsociety.model.neighbors.Neighbor;
import cellsociety.model.neighbors.SquareNeighbor;
import cellsociety.model.neighbors.TriangleNeighbor;
import java.awt.*;

/**
 * Cell class based on multiple simulations that creates cells. Users can choose which simulation they want to run and then cells are created
 * based on the type of simulation. Thus, Cell is an abstract superclass
 * Purpose: This creates the abstract cells that will populate the grids. This class is now a superclass. We made this design decision because
 *  not all simulations have the same rules. Having extended cells classes will allow each type of cell to have a different rules
 *  while still maintaining the basic functions of a cell.
 * Assumptions: The class will work assuming all dependencies are functioning.
 * Dependencies: This class relies on the Grid class to instantiate it correctly and its subclasses to properly override methods
 * Example: Choose a simulation and then the program will correctly instantiate the correct cells.
 * @author Shruthi Kumar, Chris Warren, Aneesh Gupta
 */
public abstract class Cell {
    private int state;
    private int myNextState;
    private Image displayImage;
    Point gridPos;
    private String color;
    private Neighbor neighbors; //= new SquareNeighbor();
    private int myNeighborhoodChoice;
  private final static int HALF = 2;
  private final static int ZERO = 0;
  private final static int TRIANGLE_NEIGHBOR = 1;


  /**
   * Constructor for the Cell object
   * @param row: row number cell is in
   * @param col: column number cell is in
   * @param mystate: current state of the cell
   */
    public Cell(int row, int col, int mystate, int neighborhoodChoice) {
      Point gridPos = new  Point(row, col);
      this.state = mystate;
      this.myNeighborhoodChoice = neighborhoodChoice;
      setNeighborHoodShape();

    }

  /**
   * Updates the cell based on the rules
   * @param  cellGrid : grid of cells
   * @param  row : row the cell is in
   * @param  col : column the cell is in
   * @param  width : width of the grid
   * @param  height : height of the grid
   * @return int : the next state integer
   */
    public abstract int updateCell(Grid cellGrid, int row, int col, int width, int height);



  /**
   * Returns the state of the cell
   * @return state of the cell
   */
    public abstract int getState();

  /**
   * Returns the color of the cell
   * @return color of the cell
   */
    public abstract String getCellColor();

  /**
   * Sets the color of the cell
   */
    public abstract void setCellColor();

  /**
   * Gets the neighbor of the cells
   * @return neighbors : neighbors of the cells
   */
    public Neighbor getNeighbors() {
      return neighbors;
    }

  /**
   * Returns the next state of the cell
   * @return next state of the cell
   */
    public int getNextState(){
        return this.myNextState;
    }

  /**
   * Sets the color of the cell
   * @param nextState : next state of the cell
   */
    public void setMyNextState(int nextState){
        this.myNextState = nextState;
    }

  /**
   * Sets the color of the cell
   * @param state : state of the cell
   */
    public void setState(int state) {
    this.state = state;
  }

  /**
   * Gets the number of neighbors
   * @param grid : grid of cells
   * @param row : row of cell
   * @param col : column of the cell
   * @param state : state of the cell
   * @return the number of neighbors
   */
  public int getNeighborCount(Grid grid, int row, int col, int state) {
    return neighbors.getNeighborCount(grid, row, col, state);
  }

  /**
   * Gets the number of neighbors
   * @param grid : grid of cells
   * @param row : row of cell
   * @param col : column of the cell
   * @param currState : current state of the cell
   * @return true/false whether or not the cell matches the current state
   */
  public boolean checkState(Grid grid, int row, int col, int currState) {
    return grid.getCell(row, col).getState() == currState;
  }

  /**
   * Gets the number of neighbors
   * @param grid : grid of cells
   * @param row : row of cell
   * @param col : column of the cell
   * @param nextState : state of the cell
   * @return true/false whether or not the cell matches the next state
   */
  public boolean checkNextState(Grid grid, int row, int col, int nextState) {
    return grid.getCell(row, col).getNextState() == nextState;
  }

  /**
   * Gets the type of neighborhood shape
   * @return myNeighborhoodChoice : neighborhood shape choice
   */
  public int getMyNeighborhoodChoice() {
    return myNeighborhoodChoice;
  }

  /**
   * Sets the row and column deltas that are used to check direct neighbors
   * @param row : row of the cell
   * @param col : column of the cell
   */
  public void setDirectNeighbors(int row, int col) {
    if(this.getMyNeighborhoodChoice() == TRIANGLE_NEIGHBOR) {
      handleTriangleDirectNeighborShape(row, col);
    }
    else {
      this.getNeighbors().setDirectNeighbors();
    }
  }

  /**
   * Sets the row and column deltas that are used to check all neighbors
   * @param row : row of the cell
   * @param col : column of the cell
   */
  public void setAllNeighbors(int row, int col) {
    if(this.getMyNeighborhoodChoice() == TRIANGLE_NEIGHBOR) {
      handleTriangleAllNeighborShape(row, col);
    }
    else {
      this.getNeighbors().setAllNeighbors();
    }

  }

  private void setNeighborHoodShape() {
    if(myNeighborhoodChoice == 0) {
      neighbors = new SquareNeighbor();
    }
    else if(myNeighborhoodChoice == 1) {
      neighbors = new TriangleNeighbor();
    }
    else {
      neighbors = new HexagonNeighbor();
    }
  }

  private void handleTriangleDirectNeighborShape(int row, int col) {
    if(checkDownTri(row, col)) {
      this.getNeighbors().setDirectNeighbors();
    }
    else {
      ((TriangleNeighbor) this.getNeighbors()).setDirectNeighborsUpTri();
    }
  }



  private void handleTriangleAllNeighborShape(int row, int col) {
    if(checkDownTri(row, col)) {
      this.getNeighbors().setAllNeighbors();
    }
    else {
      ((TriangleNeighbor) this.getNeighbors()).setAllNeighborsUpTri();
    }
  }

  private boolean checkDownTri(int row, int col) {
    return (row % HALF) == ZERO && (col % HALF) != ZERO
        || (row % HALF) != ZERO && (col % HALF) == ZERO;
  }


}
