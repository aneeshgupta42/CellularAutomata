package cellsociety.model;

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
  private String cellColor;

  //private Neighbor neighbors = new SquareNeighbor();
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
    this.getNeighbors().setAllNeighbors();
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
  public int updateCell(Grid cellGrid,
      int row, int col, int width, int height) {
    if(getNeighborCount(cellGrid, row, col, PERCOLATED) >= 1 && checkState(cellGrid, row, col, OPEN)) {
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
  public String getCellColor() {
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
      cellColor = "black";
    }
    else if(state == OPEN) {
      cellColor = "white";
    }
    else if(state == PERCOLATED) {
      cellColor = "blue";
    }
    else {
      setState(OPEN);
      cellColor = "white";
    }
  }


}
