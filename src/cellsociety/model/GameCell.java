package cellsociety.model;

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
  private static final String RED = "red";
  private static final String BLACK = "black";
  private static final String BLUE = "blue";
  private static final int TWO = 2;
  private static final int THREE = 3;


  private String cellColor;

  /**
   * Constructor for the FireCell object
   * @param row: row of grid
   * @param col: column of grid
   * @param mystate: current state of the cell
   */
  public GameCell(int row, int col, int mystate,  int neighborhoodChoice) {
    super(row, col, mystate, neighborhoodChoice);
    this.state = mystate;
    setAllNeighbors(row, col);
    this.setCellColor();
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
    if((getNeighborCount(cellGrid, row, col, ALIVE) > THREE || getNeighborCount(cellGrid, row, col, ALIVE) < TWO) && checkState(
        cellGrid, row, col, ALIVE)) {
      return DEAD;
    }
    else if(getNeighborCount(cellGrid, row, col, ALIVE) == THREE && checkState(cellGrid, row, col, DEAD)) {
      return ALIVE;

    }
    else if(getNeighborCount(cellGrid, row, col, ALIVE) >= TWO && checkState(cellGrid, row, col,
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
  public String getCellColor() {
    return cellColor;
  }

  /**
   * Sets the color of the cell
   */
  @Override
  public void setCellColor() {
    if(state == ALIVE) {
      cellColor = BLUE;
    }
    else if(state == DEAD) {
      cellColor = BLACK;
    }
    else {
      cellColor = RED;
    }
  }

}
