package cellsociety.model;

import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.HashMap;

/**
 * GameCell class based on Game of Life simulation. Users can choose the Game simulation and then cells are created
 * based on this type of simulation. Thus, GameCell is an subclass
 * Purpose: This creates the game cells that will populate the grid. This class is now a subclass. We made this design decision because
 *  not all simulations have the same rules. Having extended cells classes will allow the game cell to have its own rule set while
 *  still implementing basic cell functions.
 * Assumptions: The class will work assuming all dependencies are functioning.
 * Dependencies: This class relies on the Grid class to instantiate it correctly and the Cell class to properly override its methods
 * Example: Choose a simulation and then the program will correctly instantiate the game cells.
 * @author Shruthi Kumar, Chris Warren, Aneesh Gupta
 */
public class GameCell extends Cell {
  private int state;
  private static final int DEAD = 0;
  private static final int ALIVE = 1;
  private static final int SQUARE = 0;
  private static final int TRIANGLE = 1;
  private static final int HEXAGONAL = 2;

  private Neighbor neighbors = new SquareNeighbor();

  private int neighborhoodChoice;


  private Color cellColor;

  /**
   * Constructor for the FireCell object
   * @param width: width of grid
   * @param height: height of grid
   * @param mystate: current state of the cell
   */
  public GameCell(int width, int height, int mystate) {
    super(width, height, mystate);
    neighborhoodChoice = 0;
    this.state = mystate;
    neighbors.setAllNeighbors();
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
  public int updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height) {
    if((getNeighborCount(cellHashMap, row, col) > 3 || getNeighborCount(cellHashMap, row, col) < 2) && checkState(
        cellHashMap, row, col, ALIVE)) {
      return DEAD;
    }
    else if(getNeighborCount(cellHashMap, row, col) == 3 && checkState(cellHashMap, row, col, DEAD)) {
      return ALIVE;

    }
    else if(getNeighborCount(cellHashMap, row, col) >= 2 && checkState(cellHashMap, row, col,
        ALIVE)) {
      return ALIVE;

    }
    else {
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
   * Sets the color of the cell
   * @param state : state of the cell
   */
  public void setState(int state) {
    this.state = state;
  }

  /**
   * Returns the color of the cell
   * @return color of the cell
   */
  public Color getCellColor() {
    return cellColor;
  }

  /**
   * Sets the color of the cell
   */
  @Override
  public void setCellColor() {
    if(state == ALIVE) {
      cellColor = Color.BLUE;
    }
    else if(state == DEAD) {
      cellColor = Color.BLACK;
    }
    else {
      cellColor = Color.RED;
    }
  }

  private int getNeighborCount(HashMap<Point, Cell> cellHashMap, int row, int col) {
    return neighbors.getNeighborCount(cellHashMap, row, col, ALIVE);
  }



  private boolean mapContainsNeighbor(HashMap<Point, Cell> cellHashMap, int rowDelta, int colDelta) {
    return cellHashMap.containsKey(new Point(rowDelta, colDelta));
  }

  private boolean checkState(HashMap<Point, Cell> cellHashMap, int row, int col, int currState) {
    return cellHashMap.get(new Point(row, col)).getState() == currState;
  }

}
