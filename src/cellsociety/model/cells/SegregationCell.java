package cellsociety.model.cells;

import cellsociety.model.Grid;
import cellsociety.model.cells.Cell;
import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * SegregationCell class based on Segregation simulation. Users can choose the segregation simulation and then cells are created
 * based on this type of simulation. Thus, SegregationCell is an subclass
 * Purpose: This creates the segregation cells that will populate the grid. This class is now a subclass. We made this design decision because
 *  not all simulations have the same rules. Having extended cells classes will allow the segregation cell to have its own rule set while
 *  still implementing basic cell functions.
 * Assumptions: The class will work assuming all dependencies are functioning.
 * Dependencies: This class relies on the Grid class to instantiate it correctly and the Cell class to properly override its methods
 * Example: Choose a simulation and then the program will correctly instantiate the segregation cells.
 * @author Shruthi Kumar, Chris Warren, Aneesh Gupta
 */
public class SegregationCell extends Cell {

  private int state;
  private int myNextState;
  private static final int VACANT = 0;
  private static final int AGENT1 = 1;
  private static final int AGENT2 = 2;
  private static final int ZERO = 0;
  private static final String RED = "red";
  private static final String BLUE = "blue";
  private static final String WHITE= "white";

  private double THRESHOLD;
  private String cellColor;
  private List<Point> vacantCells;



  /**
   * Constructor for the FireCell object
   * @param row: width of grid
   * @param col: height of grid
   * @param state: current state of the cell
   * @param thresh : threshold for moving groups
   */
  public SegregationCell(int row, int col, int state, double thresh, int neighborhoodChoice) {
    super(row, col, state,  neighborhoodChoice);
    this.state = state;
    this.myNextState = state;
    this.THRESHOLD = thresh;
    this.setCellColor();
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
    getVacantCells(cellGrid, width, height);
    Collections.shuffle(vacantCells);
    double checkThreshold = ((double) getNeighborCount(cellGrid, row, col, cellGrid.getCell(row, col).getState())) / getNotVacantNeighborCount(
        cellGrid, row, col);

    if(checkThreshold < THRESHOLD && state!= VACANT) {
      int tempState = state;
      myNextState = VACANT;
      if (vacantCells.size() > 0) {
        Point targetPt = vacantCells.get(ZERO);
        cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY()).setMyNextState(tempState);
        vacantCells.remove(ZERO);
      }
    }
    else if(checkThreshold >= THRESHOLD && state!=VACANT){
      this.myNextState = state;
    }
    return myNextState;
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
   * Sets the color of the cell
   * @param myState : state of the cell
   */
  @Override
  public void setState(int myState){
    state = myNextState;
    myNextState = VACANT;
  }

  /**
   * Sets the color of the cell
   * @param nextState : next state of the cell
   */
  @Override
  public void setMyNextState(int nextState){
    this.myNextState = nextState;
  }

  /**
   * Returns the next state of the cell
   * @return next state of the cell
   */
  @Override
  public int getNextState(){
    return this.myNextState;
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
    if(state == VACANT) {
      cellColor = WHITE;
    }
    else if(state == AGENT1) {
      cellColor = BLUE;
    }
    else {
      cellColor = RED;
    }
  }

  private void getVacantCells(Grid grid, int width, int height) {
    vacantCells  = this.getNeighbors().getVacantCells(grid, width, height, VACANT); //new ArrayList<>();
  }

  private boolean checkNextState(HashMap<Point, Cell> cellHashMap, int row, int k, int nextState) {
    return cellHashMap.get(new Point(row, k)).getNextState() == nextState;
  }


  private int getNotVacantNeighborCount(Grid cellGrid, int row, int col) {
    int count = ZERO;

    int[] rowDelta = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] colDelta = {-1, 0, 1, -1, 1, -1, 0, 1};

    for(int i = 0; i < rowDelta.length; i++) {
      if(cellGrid.gridContainsCell(row + rowDelta[i], col + colDelta[i])
          && checkNotVacant(cellGrid, row + rowDelta[i], col + colDelta[i])) {
        count++;
      }
    }

    return count;
  }

  private boolean checkNotVacant(Grid cellGrid, int row, int col) {
    return cellGrid.getCell(row, col).getState() != VACANT;
  }

}
