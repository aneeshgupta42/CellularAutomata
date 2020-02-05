package cellsociety.model;

import javafx.scene.paint.Color;
import java.awt.Point;
import java.util.HashMap;

/**
 * PercolationCell class based on Percolation simulation. Users can choose the percolation simulation and then cells are created
 * based on this type of simulation. Thus, PercolationCell is an subclass
 * Purpose: This creates the percolation cells that will populate the grid. This class is now a subclass. We made this design decision because
 *  not all simulations have the same rules. Having extended cells classes will allow the percolation cell to have its own rule set while
 *  still implementing basic cell functions.
 * Assumptions: The class will work assuming all dependencies are functioning.
 * Dependencies: This class relies on the Grid class to instantiate it correctly and the Cell class to properly override its methods
 * Example: Choose a simulation and then the program will correctly instantiate the percolation cells.
 * @author Shruthi Kumar, Chris Warren, Aneesh Gupta
 */
public class PercolationCell extends Cell {

  private int state;
  private static final int BLOCKED = 0;
  private static final int OPEN = 1;
  private static final int PERCOLATED = 2;
  private Color cellColor;

  private Neighbor neighbors = new SquareNeighbor();
  private int neighborhoodChoice;


  /**
   * Constructor for the FireCell object
   * @param width: width of grid
   * @param height: height of grid
   * @param state: current state of the cell
  */
  public PercolationCell(int width, int height, int state) {
    super(width, height, state);
    this.state = state;
    this.setCellColor();
  }

  /**
   * Updates the cell based on the rules
   * @param  cellHashMap: grid of cells
   * @param  row: row the cell is in
   * @param  col: column the cell is in
   * @param  width: width of the grid
   * @param  height : height of the grid
   * @return int : the next state integer
   */
  @Override
  public int updateCell(HashMap<Point, Cell> cellHashMap,
      int row, int col, int width, int height) {
    if(getNeighborCount(cellHashMap, row, col) >= 1 && checkState(cellHashMap, row, col, OPEN)) {
      return PERCOLATED;
    }
    else{
      return state;
    }
  }

  /**
   * Returns the state of the cell
   * @return state of the cell
   */
  @Override
  public int getState() {
    return state;
  }

  /**
   * Returns the color of the cell
   * @return color of the cell
   */
  @Override
  public Color getCellColor() {
    return cellColor;
  }

  /**
   * Sets the color of the cell
   * @param state : state of the cell
   */
  public void setState(int state) {
    this.state = state;
  }

  /**
   * Sets the color of the cell
   */
  @Override
  public void setCellColor() {
    if(state == BLOCKED) {
      cellColor = Color.BLACK;
    }
    else if(state == OPEN) {
      cellColor = Color.WHITE;
    }
    else if(state == PERCOLATED) {
      cellColor = Color.BLUE;
    }
    else {
      setState(OPEN);
      cellColor = Color.WHITE;
    }
  }

  private int getNeighborCount(HashMap<Point, Cell> cellHashMap, int row, int col) {
    int[] rowDelta = new int[0];
    int[] colDelta = new int[0];

    if(neighborhoodChoice == 0) {
      rowDelta = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
      colDelta = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
    }
    else if(neighborhoodChoice == 1) {

    }
    else {

    }

    return neighbors.getNeighborCount(cellHashMap, row, col, rowDelta, colDelta, PERCOLATED);

    /*
    int count = 0;
    int delta = 1;

    int[] rowDelta = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] colDelta = {-1, 0, 1, -1, 1, -1, 0, 1};

    for(int i = 0; i < rowDelta.length; i++) {
      if(mapContainsNeighbor(cellHashMap, row + rowDelta[i], col + colDelta[i]) && checkState(cellHashMap, row + rowDelta[i], col + colDelta[i], PERCOLATED)) {
        count++;
      }
    }
    return count;

     */
  }

  private boolean mapContainsNeighbor(HashMap<Point, Cell> cellHashMap, int row, int col) {
    return cellHashMap.containsKey(new Point(row, col));
  }

  private boolean checkState(HashMap<Point, Cell> cellHashMap, int row, int col, int currState) {
    return cellHashMap.get(new Point(row, col)).getState() == currState;
  }

}
