package cellsociety.Model;

import java.awt.Color;
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
    //state = 0;
    //currentState = ALIVE;

  }

  @Override
  public void updateCell() {

  }

  @Override
  public void updateCell(HashMap<Point, Cell> cellHashMap, HashMap<Point, Cell> copycellHashMap,
      int row, int col, int width, int height) {

  }



  public void updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height) {
    if((getNeighborCount(cellHashMap, row, col) >= 3 || getNeighborCount(cellHashMap, row, col) < 2) && cellHashMap.get(new Point(row, col)).getState() == PERCOLATED) {
      setState(PERCOLATED);
    }
  }

  @Override
  public int getState() {
    return 0;
  }

  @Override
  public javafx.scene.paint.Color getCellColor() {
    return null;
  }


  @Override
  protected void setCellColor() {
    if(state == 0) {
      cellColor = Color.BLACK;
    }
    else if(state == 1) {
      cellColor = Color.WHITE;
    }
    else {
      cellColor = Color.BLUE;
    }
  }

  private int getNeighborCount(HashMap<Point, Cell> cellHashMap, int row, int col) {
    int count = 0;
    int delta = 1;
    //top left diagonal
    if(cellHashMap.containsKey(new Point(row - delta, col - delta)) && cellHashMap.get(new Point(row - delta, col - delta)).getState() == OPEN) {
      count++;
    }
    //top
    else if(cellHashMap.containsKey(new Point(row - delta, col)) && cellHashMap.get(new Point(row - delta, col)).getState() == OPEN) {
      count++;
    }
    //top right diagonal
    else if(cellHashMap.containsKey(new Point(row - delta, col + delta)) && cellHashMap.get(new Point(row - delta, col + delta)).getState() == OPEN) {
      count++;
    }
    //left
    else if(cellHashMap.containsKey(new Point(row, col - delta)) && cellHashMap.get(new Point(row, col - delta)).getState() == OPEN) {
      count++;
    }
    //right
    else if(cellHashMap.containsKey(new Point(row, col + delta)) && cellHashMap.get(new Point(row, col + delta)).getState() == OPEN) {
      count++;
    }
    //bottom left diagonal
    else if(cellHashMap.containsKey(new Point(row + delta, col - delta)) && cellHashMap.get(new Point(row + delta, col - delta)).getState() == OPEN) {
      count++;
    }
    //bottom
    else if(cellHashMap.containsKey(new Point(row + delta, col)) && cellHashMap.get(new Point(row + delta, col)).getState() == OPEN) {
      count++;
    }
    //bottom right
    else if(cellHashMap.containsKey(new Point(row + delta, col + delta)) && cellHashMap.get(new Point(row + delta, col + delta)).getState() == OPEN) {
      count++;
    }
    else {}
    return count;
  }

  public enum PercolationState {
    BLOCKED,
    OPEN,
    PERCOLATED
  }
}
