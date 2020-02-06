package cellsociety.model;


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
  private String cellColor;
  private int state;
  private Random numChooser = new Random();

  //private Neighbor neighbors = new SquareNeighbor();
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
    this.getNeighbors().setDirectNeighbors();

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
  @Override
  public int updateCell(Grid cellGrid, int row, int col, int width, int height) {
    if(checkState(cellGrid, row, col, BURNING)) {
      return EMPTY;
    }
    else if(checkState(cellGrid, row, col, TREE) && getNeighborCount(cellGrid, row, col, BURNING) >= 1) {
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
  public String getCellColor() {
    return cellColor;
  }

  /**
   * Sets the color of the cell
   */
  @Override
  public void setCellColor() {
    if(state == EMPTY) {
      cellColor = "yellow";
    }
    else if(state == TREE) {
      cellColor = "green";
    }
    else {
      cellColor = "red";
    }
  }

  /**
   * Sets the color of the cell
   * @param state : state of the cell
   */
  public void setState(int state) {
    this.state = state;
  }




}
