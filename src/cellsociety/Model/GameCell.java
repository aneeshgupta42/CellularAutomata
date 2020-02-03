package cellsociety.Model;

import javafx.scene.paint.Color;

import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;


public class GameCell extends Cell {
  private int state;
  private static final int DEAD = 0;
  private static final int ALIVE = 1;

  private Color cellColor;

  public GameCell(int width, int height, int mystate) {
    super(width, height, mystate);
    this.state = mystate;

    this.setCellColor();
  }

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

  @Override
  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public Color getCellColor() {
    return cellColor;
  }

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
    int count = 0;

    int[] rowDelta = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] colDelta = {-1, 0, 1, -1, 1, -1, 0, 1};

    //checks if neighbor is alive in this order: top left diagonal, top, top right diagonal, left, right, bottom left diagonal, bottom, bottom right diagonal
    for(int i = 0; i < rowDelta.length; i++) {
      if(mapContainsNeighbor(cellHashMap, row + rowDelta[i], col + colDelta[i]) && checkState(cellHashMap,row + rowDelta[i], col + colDelta[i], ALIVE)) {
        count++;
      }
    }

    return count;
  }

  private boolean mapContainsNeighbor(HashMap<Point, Cell> cellHashMap, int rowDelta, int colDelta) {
    return cellHashMap.containsKey(new Point(rowDelta, colDelta));
  }

  private boolean checkState(HashMap<Point, Cell> cellHashMap, int row, int col, int currState) {
    return cellHashMap.get(new Point(row, col)).getState() == currState;
  }

}
