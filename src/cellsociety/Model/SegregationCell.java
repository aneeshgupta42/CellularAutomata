package cellsociety.Model;

import javafx.scene.paint.Color;
import java.awt.Point;
import java.util.ArrayList;
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
  private double THRESHOLD;
  private Color cellColor;
  private List<Point> vacantCells;

  /**
   * Constructor for the FireCell object
   * @param width: width of grid
   * @param height: height of grid
   * @param state: current state of the cell
   * @param thresh : threshold for moving groups
   */
  public SegregationCell(int width, int height, int state, double thresh) {
    super(width, height, state);
    this.state = state;
    this.myNextState = state;
    this.THRESHOLD = thresh;
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
    getVacantCells(cellHashMap, width, height);
    Collections.shuffle(vacantCells);
    double checkThreshold = ((double) getNeighborTypeCount(cellHashMap, row, col, cellHashMap.get(new Point(row, col)).getState())) / getNeighborCount(cellHashMap, row, col);
    if(checkThreshold < THRESHOLD && state!= VACANT) {
      int tempState = state;
      myNextState = VACANT;
      Point targetPt = vacantCells.get(0);
      cellHashMap.get(targetPt).setMyNextState(tempState);
      vacantCells.remove(0);
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
  public javafx.scene.paint.Color getCellColor() {
    return cellColor;
  }

  /**
   * Sets the color of the cell
   */
  @Override
  public void setCellColor() {
    if(state == 0) {
      cellColor = Color.WHITE;
    }
    else if(state == 1) {
      cellColor = Color.BLUE;
    }
    else {
      cellColor = Color.RED;
    }
  }

  private void getVacantCells(HashMap<Point, Cell> cellHashMap, int width, int height) {
    vacantCells  = new ArrayList<>();
    for(int i = 0; i < width; i++) {
      for(int k = 0; k < height; k++) {
        if(checkState(cellHashMap, i, k, VACANT)
            && checkNextState(cellHashMap, i, k, VACANT)) {
          vacantCells.add(new Point(i, k));
        }
      }
    }
  }

  private boolean checkNextState(HashMap<Point, Cell> cellHashMap, int row, int k, int nextState) {
    return cellHashMap.get(new Point(row, k)).getNextState() == nextState;
  }

  private boolean checkState(HashMap<Point, Cell> cellHashMap, int i, int k, int vacant) {
    return cellHashMap.get(new Point(i, k)).getState() == vacant;
  }

  private int getNeighborCount(HashMap<Point, Cell> cellHashMap, int row, int col) {
    int count = 0;
    int delta = 1;

    int[] rowDelta = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] colDelta = {-1, 0, 1, -1, 1, -1, 0, 1};

    for(int i = 0; i < rowDelta.length; i++) {
      if(mapContainsNeighbor(cellHashMap, row + rowDelta[i], col + colDelta[i])
          && checkNotVacant(cellHashMap, row + rowDelta[i], col + colDelta[i])) {
        count++;
      }
    }

    return count;
  }

  private boolean mapContainsNeighbor(HashMap<Point, Cell> cellHashMap, int i, int i2) {
    return cellHashMap.containsKey(new Point(i, i2));
  }

  private boolean checkNotVacant(HashMap<Point, Cell> cellHashMap, int row, int col) {
    return cellHashMap.get(new Point(row, col)).getState() != VACANT;
  }

  private int getNeighborTypeCount(HashMap<Point, Cell> cellHashMap, int row, int col, int state) {
    int count = 0;

    int[] rowDelta = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] colDelta = {-1, 0, 1, -1, 1, -1, 0, 1};

    for(int i = 0; i < rowDelta.length; i++) {
      if(mapContainsNeighbor(cellHashMap, row + rowDelta[i], col + colDelta[i])
          && checkState(cellHashMap,row + rowDelta[i], col + colDelta[i], state)) {
        count++;
      }
    }

    return count;

  }

}
