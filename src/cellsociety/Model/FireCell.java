package cellsociety.Model;

import javafx.scene.paint.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Random;

public class FireCell extends Cell {

  private float probCatch;
  private final static int EMPTY = 0;
  private final static int TREE = 1;
  private final static int BURNING = 2;
  private Color cellColor;
  private int state;
  private Random numChooser = new Random();

  public FireCell(int row, int col, int mystate, float prob) {
    super(row, col, mystate);
    this.state = mystate;
    this.setCellColor();
    this.probCatch = prob;
  }


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

  private boolean checkState(HashMap<Point, Cell> cellHashMap, int row, int col, int currState) {
    return cellHashMap.get(new Point(row, col)).getState() == currState;
  }


  @Override
  public int getState() {
    return this.state;
  }

  @Override
  public Color getCellColor() {
    return cellColor;
  }


  private int getNeighborCount(HashMap<Point, Cell> cellHashMap, int row, int col) {
    int count = 0;
    int delta = 1;

    //increments count if top, left, right, or bottom neighbor is burning
    if((mapContainsNeighbor(cellHashMap, col, row - delta) && checkState(cellHashMap, row - delta, col, BURNING))
      || (mapContainsNeighbor(cellHashMap, col - delta, row) && checkState(cellHashMap, row,col - delta, BURNING))
      || (mapContainsNeighbor(cellHashMap, col + delta, row) && checkState(cellHashMap, row,col + delta, BURNING))
      || (mapContainsNeighbor(cellHashMap, col, row + delta) && checkState(cellHashMap, row + delta, col, BURNING))) {
      count++;
    }

    return count;
  }

  private boolean mapContainsNeighbor(HashMap<Point, Cell> cellHashMap, int colDelta, int rowDelta) {
    return cellHashMap.containsKey(new Point(rowDelta, colDelta));
  }

  @Override
  protected void setCellColor() {
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

  public void setState(int state) {
    this.state = state;
  }

}
