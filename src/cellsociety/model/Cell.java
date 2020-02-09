package cellsociety.model;

import java.awt.*;

/**
 * Cell class based on multiple simulations that creates cells. Users can choose which simulation they want to run and then cells are created
 * based on the type of simulation. Thus, Cell is an abstract superclass
 * Purpose: This creates the abstract cells that will populate the grids. This class is now a superclass. We made this design decision because
 *  not all simulations have the same rules. Having extended cells classes will allow each type of cell to have a different rules
 *  while still maintaining the basic functions of a cell.
 * Assumptions: The class will work assuming all dependencies are functioning.
 * Dependencies: This class relies on the Grid class to instantiate it correctly and its subclasses to properly override methods
 * Example: Choose a simulation and then the program will correctly instatiate the correct cells.
 * @author Shruthi Kumar, Chris Warren, Aneesh Gupta
 */
public abstract class Cell {
    private int state;
    private int myNextState;
    private Image displayImage;
    Point gridPos;
    private String color;
    private Neighbor neighbors = new SquareNeighbor();
    private int myNeighborhoodChoice;

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

  public int getNeighborCount(Grid grid, int row, int col, int state) {
    return neighbors.getNeighborCount(grid, row, col, state);
  }

  public boolean checkState(Grid grid, int row, int col, int currState) {
      int state = this.state;
    return grid.getCell(row, col).getState() == currState;
  }

  public boolean checkNextState(Grid grid, int row, int col, int nextState) {
    return grid.getCell(row, col).getNextState() == nextState;
  }

}
