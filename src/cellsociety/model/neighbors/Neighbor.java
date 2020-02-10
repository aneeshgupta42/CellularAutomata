package cellsociety.model.neighbors;

import cellsociety.model.Grid;
import cellsociety.model.cells.SugarScapeCell;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Neighbor class based on different types of shapes a cell could have. Users can choose which shape they want to have in the xml files and then cells are]
 * created based on that shape. Thus, Neighbor is an abstract superclass
 * Purpose: This creates the abstract neighbors that will be used to find neighbors of the cells. This class is now a superclass. We made this design decision
 * because not all shapes ahve the same number or location of cells. Having extended neighbor classes will allow each type of neighbor to have a different
 * locations while still maintaining the basic functions of a cell.
 * Assumptions: The class will work assuming all dependencies are functioning.
 * Dependencies: This class relies on the Grid class to instantiate it correctly and its subclasses to properly override methods
 * Example: Choose a simulation and shape (in the xml files) and then the program will correctly create the shapes
 * @author Shruthi Kumar, Chris Warren, Aneesh Gupta
 */
public abstract class Neighbor {
  private int[] rowDelta;
  private int[] colDelta;
  private int maxState;

  /**
   * Constructor for Hexagon Neighbor object
   */
  public Neighbor() {
    maxState = 0;
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
    int count = 0;
    int delta = 1;

    for(int i = 0; i < rowDelta.length; i++) {
      if(grid.gridContainsCell(row + rowDelta[i], col + colDelta[i]) && checkState(grid, row + rowDelta[i], col + colDelta[i], state)) {
        count++;
      }
    }
    return count;
  }

  /**
   * Gets the state that has the most occurences in a cell's neighbors
   * @param grid : grid of cells
   * @param row : row of the cell
   * @param col : col of the cell
   * @param states : array of states cell could take
   * @return max : state that has the most occurences in a cell's neighbors
   */
  public int getMaxNeighborCount(Grid grid, int row, int col, int[] states) {
    int max = 0;
    int comp = 0;
    for(int temp : states) {
      comp = getNeighborCount(grid, row, col, temp);
      if(max < comp) {
        max = comp;
        maxState = temp;
      }
    }
    return max;
  }

  /**
   * Gets the state of the neighbor with the most sugar
   * @return max state : the state of the neighbor with the most sugar
   */
  public int getMaxNeighborState() {
    return maxState;
  }

  /**
   * Returns the point of the cell that has the most sugar in the Sugar simulation
   * @param grid : grid of cells
   * @param row : row of the cell
   * @param col : col of the cell
   * @param state : state of the cell
   * @return maxSugarPoint : point of sugar patch with most sugar
   */
  public Point getMaxNeighborTypeCount(Grid grid, int row, int col, int state) {
    int maxSugarAmount = 0;
    Point maxSugarPoint = null;
    int compSugar = 0;

    for(int i = 0; i < rowDelta.length; i++) {
      if(grid.gridContainsCell(row + rowDelta[i], col + colDelta[i]) && checkState(grid, row + rowDelta[i], col + colDelta[i], state)
          && checkNextState(grid, row + rowDelta[i], col + colDelta[i], state)) {
        compSugar = ((SugarScapeCell) grid.getCell(row + rowDelta[i], col + colDelta[i])).getMySugarCount();
        if(compSugar > maxSugarAmount) {
          maxSugarAmount = compSugar;
          maxSugarPoint = new Point(row + rowDelta[i], col + colDelta[i]);
        }
      }
    }

    return maxSugarPoint;
  }

  /**
   * Gets the list of cells that are vacant for sharks/fish to move to (only considers neighbors)
   * @param grid : grid of cells
   * @param row : row fo the cell
   * @param col : col of the cell
   * @param state : state of the cell
   * @return vacantCells : the list of vacant cells to move to
   */
  public List<Point> getVacantNeighbors(Grid grid, int row, int col, int state) {
    List<Point> vacantCells = new ArrayList<Point>();

    for(int i = 0; i < rowDelta.length; i++) {
      if(grid.gridContainsCell(row + rowDelta[i], col + colDelta[i])
          && checkState(grid, row + rowDelta[i], col + colDelta[i], state)
          && checkNextState(grid, row + rowDelta[i], col + colDelta[i], state)) {
        vacantCells.add(new Point(row + rowDelta[i], col + colDelta[i]));
      }
    }
    return vacantCells;
  }

  /**
   * Gets the list of cells that are vacant for sharks/fish to move to
   * @param grid : grid of cells
   * @param width : width of grid
   * @param height : height of grid
   * @param state : state of the cell
   * @return vacantCells : the list of fish cells that sharks can eat
   */
  public List<Point> getVacantCells(Grid grid, int width, int height, int state) {
    List<Point> vacantCells  = new ArrayList<>();
    for(int i = 0; i < width; i++) {
      for(int k = 0; k < height; k++) {
        if(checkState(grid, i, k, state)
            && checkNextState(grid, i, k, state)) {
          vacantCells.add(new Point(i, k));
        }
      }
    }
    return vacantCells;
  }

  /**
   * Gets the list of cells that are fish for sharks to eat
   * @param grid : grid of cells
   * @param row : row of the cell
   * @param col : col of the cell
   * @param state : state of the cell
   * @return fishCells : the list of fish cells that sharks can eat
   */
  public List<Point> getTypeNeighbors(Grid grid, int row, int col, int state) {
    List<Point> fishCells = new ArrayList<Point>();

    for(int i = 0; i < rowDelta.length; i++) {
      if(grid.gridContainsCell( row + rowDelta[i], col + colDelta[i])
          && checkNextState(grid, row + rowDelta[i], col + colDelta[i], state)) {
        fishCells.add(new Point(row + rowDelta[i], col + colDelta[i]));
      }
    }

    return fishCells;

  }

  /**
   * Abstract method to set the row and column deltas for the direct neighbors of a square shape grid
   */
  public abstract void setDirectNeighbors();

  /**
   * Abstract method to set the row and column deltas for all the neighbors of a square shape grid
   */
  public abstract void setAllNeighbors();


  /**
   * Sets the row deltas for the neighbors
   * @param rowDeltaNew: new row deltas
   */
  public void setRowDelta(int[] rowDeltaNew) {
    this.rowDelta = rowDeltaNew;
  }

  /**
   * Sets the column deltas for the neighbors
   * @param colDeltaNew: new row deltas
   */
  public void setColDelta(int[] colDeltaNew) {
    this.colDelta = colDeltaNew;
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
  private boolean checkNextState(Grid grid, int row, int col, int nextState) {
    return grid.getCell(row, col).getNextState() == nextState;
  }



}
