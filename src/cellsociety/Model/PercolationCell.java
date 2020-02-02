package cellsociety.Model;

import javafx.scene.paint.Color;
import java.awt.Point;
import java.util.HashMap;

public class PercolationCell extends Cell {

  private int state;
  private static final int BLOCKED = 0;
  private static final int OPEN = 1;
  private static final int PERCOLATED = 2;
  private javafx.scene.paint.Color cellColor;

  public PercolationCell(int width, int height, int state) {
    super(width, height, state);
    this.state = state;
    this.setCellColor();
    //state = 0;
    //currentState = ALIVE;
  }

  @Override
  public int updateCell() {
    return 0;
  }

  @Override
  public int updateCell(HashMap<Point, Cell> cellHashMap, HashMap<Point, Cell> copyCellHashMap,
      int row, int col, int width, int height) {
    System.out.println("reached");
    if(getNeighborCount(cellHashMap, copyCellHashMap, row, col) >= 1 && cellHashMap.get(new Point(row, col)).getState() == OPEN) {
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

  private int getNeighborCount(HashMap<Point, Cell> cellHashMap, HashMap<Point, Cell> copyCellHashMap, int row, int col) {
    int count = 0;
    int delta = 1;
    //top left diagonal
    if(copyCellHashMap.containsKey(new Point(row - delta, col - delta)) && copyCellHashMap.get(new Point(row - delta, col - delta)).getState() == PERCOLATED) {
      count++;
    }
    //top
    if(copyCellHashMap.containsKey(new Point(row - delta, col)) && copyCellHashMap.get(new Point(row - delta, col)).getState() == PERCOLATED) {
      count++;
    }
    //top right diagonal
    if(copyCellHashMap.containsKey(new Point(row - delta, col + delta)) && copyCellHashMap.get(new Point(row - delta, col + delta)).getState() == PERCOLATED) {
      count++;
    }
    //left
    if(copyCellHashMap.containsKey(new Point(row, col - delta)) && copyCellHashMap.get(new Point(row, col - delta)).getState() == PERCOLATED) {
      count++;
    }
    //right
    if(copyCellHashMap.containsKey(new Point(row, col + delta)) && copyCellHashMap.get(new Point(row, col + delta)).getState() == PERCOLATED) {
      count++;
    }
    //bottom left diagonal
    if(copyCellHashMap.containsKey(new Point(row + delta, col - delta)) && copyCellHashMap.get(new Point(row + delta, col - delta)).getState() == PERCOLATED) {
      count++;
    }
    //bottom
    if(copyCellHashMap.containsKey(new Point(row + delta, col)) && copyCellHashMap.get(new Point(row + delta, col)).getState() == PERCOLATED) {
      count++;
    }
    //bottom right
    if(copyCellHashMap.containsKey(new Point(row + delta, col + delta)) && copyCellHashMap.get(new Point(row + delta, col + delta)).getState() == PERCOLATED) {
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
