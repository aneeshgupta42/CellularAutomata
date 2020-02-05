package cellsociety.model;

import javafx.scene.paint.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Random;

/**
 * Fire Cell class based on Fire simulation. Users can choose the fire simulation and then cells are created
 * based on this type of simulation. Thus, FireCell is an subclass
 * Purpose: This creates the fire cells that will populate the grid. This class is now a subclass. We made this design decision because
 *  not all simulations have the same rules. Having extended cells classes will allow the fire cell to have its own rule set while
 *  still implementing basic cell functions.
 * Assumptions: The class will work assuming all dependencies are functioning.
 * Dependencies: This class relies on the Grid class to instantiate it correctly and the Cell class to properly override its methods
 * Example: Choose a simulation and then the program will correctly instatiate the fire cells.
 * @author Shruthi Kumar, Chris Warren, Aneesh Gupta
 */
public class FireCell extends Cell {

  private float probCatch;
  private final static int EMPTY = 0;
  private final static int TREE = 1;
  private final static int BURNING = 2;
  private Color cellColor;
  private int state;
  private Random numChooser = new Random();

  private Neighbor neighbors = new SquareNeighbor();
  private int neighborhoodChoice;


  /**
   * Constructor for the FireCell object
   * @param row: row number cell is in
   * @param col: column number cell is in
   * @param mystate: current state of the cell
   * @param prob : probability of catching fire
   */
  public FireCell(int row, int col, int mystate, float prob) {
    super(row, col, mystate);
    this.state = mystate;
    this.setCellColor();
    this.probCatch = prob;
    neighborhoodChoice = 0;

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
    if(checkState(cellHashMap, row, col, BURNING)) {
      return EMPTY;
    }
    else if(checkState(cellHashMap, row, col, TREE) && getNeighborCount(cellHashMap, row, col) >= 1) {
      if(numChooser.nextFloat() < this.probCatch) {
        return BURNING;
      }
    }

    return state;
  }

  /**
   * Returns the state of the cell
   * @return state of the cell
   */
  @Override
  public int getState() {
    return this.state;
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
   */
  @Override
  public void setCellColor() {
    if(state == EMPTY) {
      cellColor = Color.YELLOW;
    }
    else if(state == TREE) {
      cellColor = Color.GREEN;
    }
    else {
      cellColor = Color.RED;
    }
  }

  /**
   * Sets the color of the cell
   * @param state : state of the cell
   */
  public void setState(int state) {
    this.state = state;
  }


  private int getNeighborCount(HashMap<Point, Cell> cellHashMap, int row, int col) {
    int[] rowDelta = new int[0];
    int[] colDelta = new int[0];

    if(neighborhoodChoice == 0) {
      rowDelta = new int[]{-1, 0, 0, 1};
      colDelta = new int[]{0, -1, 1, 0};
    }
    else if(neighborhoodChoice == 1) {

    }
    else {

    }

    return neighbors.getNeighborCount(cellHashMap, row, col, rowDelta, colDelta, BURNING);

    /*
    //checks if neighbor is alive in this order: top left diagonal, top, top right diagonal, left, right, bottom left diagonal, bottom, bottom right diagonal
    for(int i = 0; i < rowDelta.length; i++) {
      if(mapContainsNeighbor(cellHashMap, row + rowDelta[i], col + colDelta[i])
          && checkState(cellHashMap,row + rowDelta[i], col + colDelta[i], ALIVE)) {
        count++;
      }
    }

    return count;





    int count = 0;
    int[] rowDelta = {-1, 0, 0, 1};
    int[] colDelta = {0, -1, 1, 0};

    //increments count if top, left, right, or bottom neighbor is burning
    for(int i = 0; i < rowDelta.length; i++) {
      if(mapContainsNeighbor(cellHashMap, col + colDelta[i], row + rowDelta[i]) && checkState(cellHashMap, row + rowDelta[i], col + colDelta[i], BURNING)) {
        count++;
      }
    }

    return count;

     */
  }

  private boolean mapContainsNeighbor(HashMap<Point, Cell> cellHashMap, int colDelta, int rowDelta) {
    return cellHashMap.containsKey(new Point(rowDelta, colDelta));
  }

  private boolean checkState(HashMap<Point, Cell> cellHashMap, int row, int col, int currState) {
    return cellHashMap.get(new Point(row, col)).getState() == currState;
  }

}
