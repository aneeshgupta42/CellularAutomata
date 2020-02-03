package cellsociety.Model;

import javafx.scene.paint.Color;
import java.awt.Point;
import java.util.HashMap;

public class PercolationCell extends Cell {

  private int state;
  private static final int BLOCKED = 0;
  private static final int OPEN = 1;
  private static final int PERCOLATED = 2;
  private Color cellColor;

  public PercolationCell(int width, int height, int state) {
    super(width, height, state);
    this.state = state;
    this.setCellColor();
  }


  @Override
  public int updateCell(HashMap<Point, Cell> cellHashMap,
      int row, int col, int width, int height) {
    if(getNeighborCount(cellHashMap, row, col) >= 1 && checkState(cellHashMap, row, col, OPEN)) {
      return PERCOLATED;
    }
    else{
      return state;
    }
  }

  @Override
  public int getState() {
    return state;
  }

  @Override
  public Color getCellColor() {
    return cellColor;
  }

  public void setState(int state) {
    this.state = state;
  }

  @Override
  protected void setCellColor() {
    if(state == BLOCKED) {
      cellColor = Color.BLACK;
    }
    else if(state == OPEN) {
      cellColor = Color.WHITE;
    }
    else if(state == PERCOLATED) {
      cellColor = Color.BLUE;
    }
    else {
      setState(OPEN);
      cellColor = Color.WHITE;
    }
  }

  private int getNeighborCount(HashMap<Point, Cell> cellHashMap, int row, int col) {
    int count = 0;
    int delta = 1;

    int[] rowDelta = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] colDelta = {-1, 0, 1, -1, 1, -1, 0, 1};

    for(int i = 0; i < rowDelta.length; i++) {
      if(mapContainsNeighbor(cellHashMap, row + rowDelta[i], col + colDelta[i]) && checkState(cellHashMap, row + rowDelta[i], col + colDelta[i], PERCOLATED)) {
        count++;
      }
    }
    return count;
  }

  private boolean mapContainsNeighbor(HashMap<Point, Cell> cellHashMap, int row, int col) {
    return cellHashMap.containsKey(new Point(row, col));
  }

  private boolean checkState(HashMap<Point, Cell> cellHashMap, int row, int col, int currState) {
    return cellHashMap.get(new Point(row, col)).getState() == currState;
  }

}
