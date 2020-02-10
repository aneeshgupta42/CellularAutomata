package cellsociety.model.cells;

import cellsociety.model.Grid;
import cellsociety.model.cells.Cell;

/**
 * RPSCell class based on the RPS simulation. Users can choose the RPS simulation and then cells are created
 * based on this type of simulation. Thus, RPSCell is an subclass
 * Purpose: This creates the RPS cells that will populate the grid. This class is now a subclass. We made this design decision because
 *  not all simulations have the same rules. Having extended cells classes will allow the RPS cell to have its own rule set while
 *  still implementing basic cell functions.
 * Assumptions: The class will work assuming all dependencies are functioning.
 * Dependencies: This class relies on the Grid class to instantiate it correctly and the Cell class to properly override its methods
 * Example: Choose a simulation and then the program will correctly instantiate the RPS cells.
 * @author Shruthi Kumar, Chris Warren, Aneesh Gupta
 */
public class RPSCell extends Cell {
  private int state;
  private static final int ROCK = 0;
  private static final int PAPER = 1;
  private static final int SCISSORS = 2;
  private static final int WIN = 1;
  private static final int LOSE = 0;
  private static final String BLACK = "black";
  private static final String WHITE = "white";
  private static final String GRAY = "gray";


  private int THRESHOLD;
  private String cellColor;


  /**
   * Constructor for the Cell object
   *
   * @param row     : row number cell is in
   * @param col     : column number cell is in
   * @param mystate : current state of the cell
   */
  public RPSCell(int row, int col, int mystate, int threshold,  int neighborhoodChoice) {
    super(row, col, mystate, neighborhoodChoice);
    this.state = mystate;
    this.setCellColor();
    this.THRESHOLD = threshold;
    setAllNeighbors(row, col);
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
    int[] statesArr = {ROCK, PAPER, SCISSORS};
    int maxNeighborCount = this.getNeighbors().getMaxNeighborCount(cellGrid, row, col, statesArr);
    int compState = this.getNeighbors().getMaxNeighborState();
    int currState = cellGrid.getCell(row, col).getState();

    if(THRESHOLD < maxNeighborCount && stateWinChecks(compState, currState) == WIN) {
      return this.getNeighbors().getMaxNeighborState();
    }
    else {
      return state;
    }
  }

  /**
   * Sets the color of the cell
   * @param state : state of the cell
   */
  public void setState(int state) {
    this.state = state;
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
    if(state == ROCK) {
      cellColor = GRAY;
    }
    else if(state == PAPER) {
      cellColor = WHITE;
    }
    else {
      cellColor = BLACK;
    }
  }


  /**
   * Returns whether or not the next state wins over the current when
   * @param state1 : state of the first cell
   * @param state2 : state of the second cell
   * @return LOSE/WIN : 0/1 based on whether or not the cell wins the round
   */
  private int stateWinChecks(int state1, int state2) {
    if (state1 == ROCK && state2 == PAPER
        || state1 == SCISSORS && state2 == ROCK
        || state1 == PAPER && state2 == SCISSORS) {
      return LOSE;
    } else {
      return WIN;
    }
  }

}
