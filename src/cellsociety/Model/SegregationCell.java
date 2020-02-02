package cellsociety.Model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SegregationCell extends Cell {

  private int state;
  private static final int VACANT = 0;
  private static final int AGENT1 = 1;
  private static final int AGENT2 = 2;
  private static final double THRESHOLD = .3;
  private Color cellColor;
  private List<Point> vacantCells = new ArrayList<>();

  public SegregationCell(int width, int height, int state) {
    super(width, height, state);
    //state = 0;
    //currentState = ALIVE;

  }



  @Override
  public void updateCell() {

  }

  /*
  @Override
  public void updateCell(HashMap<Point, Cell> cellHashMap, int row, int col) {

  }

   */


  public void updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height) {
    if(((double) getNeighborTypeCount(cellHashMap, row, col, cellHashMap.get(new Point(row, col)).getState())) / getNeighborCount(cellHashMap, row, col) < THRESHOLD) {
      cellHashMap.put(vacantCells.get(0), new SegregationCell(width, height, cellHashMap.get(new Point(row, col)).getState()));
      cellHashMap.remove(new Point(row, col));
      //should remove random index
      vacantCells.remove(0);
    }

  }

  @Override
  public int getState() {
    return 0;
  }


  @Override
  protected void setCellColor() {
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

  public void getVacantCells(HashMap<Point, Cell> cellHashMap, int width, int height) {
    for(int i = 0; i < width; i++) {
      for(int k = 0; k < height; k++) {
        if(cellHashMap.get(new Point(i, k)).getState() == VACANT) {
          vacantCells.add(new Point(i, k));
        }
      }
    }
  }
  public int getNeighborCount(HashMap<Point, Cell> cellHashMap, int row, int col) {
    int count = 0;
    int delta = 1;
    //top left diagonal
    if(cellHashMap.containsKey(new Point(row - delta, col - delta)) && cellHashMap.get(new Point(row - delta, col - delta)).getState() != VACANT) {
      count++;
    }
    //top
    else if(cellHashMap.containsKey(new Point(row - delta, col)) && cellHashMap.get(new Point(row - delta, col)).getState() != VACANT) {
      count++;
    }
    //top right diagonal
    else if(cellHashMap.containsKey(new Point(row - delta, col + delta)) && cellHashMap.get(new Point(row - delta, col + delta)).getState() != VACANT) {
      count++;
    }
    //left
    else if(cellHashMap.containsKey(new Point(row, col - delta)) && cellHashMap.get(new Point(row, col - delta)).getState() != VACANT) {
      count++;
    }
    //right
    else if(cellHashMap.containsKey(new Point(row, col + delta)) && cellHashMap.get(new Point(row, col + delta)).getState() != VACANT) {
      count++;
    }
    //bottom left diagonal
    else if(cellHashMap.containsKey(new Point(row + delta, col - delta)) && cellHashMap.get(new Point(row + delta, col - delta)).getState() != VACANT) {
      count++;
    }
    //bottom
    else if(cellHashMap.containsKey(new Point(row + delta, col)) && cellHashMap.get(new Point(row + delta, col)).getState() != VACANT) {
      count++;
    }
    //bottom right
    else if(cellHashMap.containsKey(new Point(row + delta, col + delta)) && cellHashMap.get(new Point(row + delta, col + delta)).getState() != VACANT) {
      count++;
    }
    else {}
    return count;
  }

  public int getNeighborTypeCount(HashMap<Point, Cell> cellHashMap, int row, int col, int state) {
    int count = 0;
    int delta = 1;
    //top left diagonal
    if(cellHashMap.containsKey(new Point(row - delta, col - delta)) && cellHashMap.get(new Point(row - delta, col - delta)).getState() == state) {
      count++;
    }
    //top
    else if(cellHashMap.containsKey(new Point(row - delta, col)) && cellHashMap.get(new Point(row - delta, col)).getState() == state) {
      count++;
    }
    //top right diagonal
    else if(cellHashMap.containsKey(new Point(row - delta, col + delta)) && cellHashMap.get(new Point(row - delta, col + delta)).getState() == state) {
      count++;
    }
    //left
    else if(cellHashMap.containsKey(new Point(row, col - delta)) && cellHashMap.get(new Point(row, col - delta)).getState() == state) {
      count++;
    }
    //right
    else if(cellHashMap.containsKey(new Point(row, col + delta)) && cellHashMap.get(new Point(row, col + delta)).getState() == state) {
      count++;
    }
    //bottom left diagonal
    else if(cellHashMap.containsKey(new Point(row + delta, col - delta)) && cellHashMap.get(new Point(row + delta, col - delta)).getState() == state) {
      count++;
    }
    //bottom
    else if(cellHashMap.containsKey(new Point(row + delta, col)) && cellHashMap.get(new Point(row + delta, col)).getState() == state) {
      count++;
    }
    //bottom right
    else if(cellHashMap.containsKey(new Point(row + delta, col + delta)) && cellHashMap.get(new Point(row + delta, col + delta)).getState() == state) {
      count++;
    }
    else {}
    return count;

  }

  public enum SegregationStates {
    RED,
  BLUE,
    WHITE
  }
}
