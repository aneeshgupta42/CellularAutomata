package Model;

import cellsociety.Grid;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;

public class GameCell extends Cell {
  //HashMap<Point, Cell> cellGrid = new HashMap<Point, Cell>();
  private int state;
  private static final int ALIVE = 1;
  private static final int DEAD = 0;
  //alive = 1, dead = 0
  private Color cellColor;

  public GameCell(int width, int height) {
    super(width, height);
    state = 0;
    //currentState = ALIVE;

  }

  @Override
  public void updateCell() {

  }

  @Override
  public int getState() {
    return 0;
  }

  public void setCellColor() {
    if(state == 0) {
      cellColor = Color.GRAY;
    }
    else if(state == 1) {
      cellColor = Color.BLUE;
    }
    else {
      cellColor = Color.BLACK;
    }
  }



  @Override
  public void applyRules(HashMap<Point, Cell> cellHashMap, int width, int height, int row, int col) {

  }

  private int getNeighborCount(HashMap<Point, Cell> cellHashMap, int row, int col) {
    int count = 0;
    int delta = 1;
    //top left diagonal
    if(cellHashMap.containsKey(new Point(row - delta, col - delta)) && cellHashMap.get(new Point(row - delta, col - delta)).getState() == ALIVE) {
      count++;
    }
    //top
    else if(cellHashMap.containsKey(new Point(row - delta, col)) && cellHashMap.get(new Point(row - delta, col)).getState() == ALIVE) {
      count++;
    }
    //top right diagonal
    else if(cellHashMap.containsKey(new Point(row - delta, col + delta)) && cellHashMap.get(new Point(row - delta, col + delta)).getState() == ALIVE) {
      count++;
    }
    //left
    else if(cellHashMap.containsKey(new Point(row, col - delta)) && cellHashMap.get(new Point(row, col - delta)).getState() == ALIVE) {
      count++;
    }
    //right
    else if(cellHashMap.containsKey(new Point(row, col + delta)) && cellHashMap.get(new Point(row, col + delta)).getState() == ALIVE) {
      count++;
    }
    //bottom left diagonal
    else if(cellHashMap.containsKey(new Point(row + delta, col - delta)) && cellHashMap.get(new Point(row + delta, col - delta)).getState() == ALIVE) {
      count++;
    }
    //bottom
    else if(cellHashMap.containsKey(new Point(row + delta, col)) && cellHashMap.get(new Point(row + delta, col)).getState() == ALIVE) {
      count++;
    }
    //bottom right
    else if(cellHashMap.containsKey(new Point(row + delta, col + delta)) && cellHashMap.get(new Point(row + delta, col + delta)).getState() == ALIVE) {
      count++;
    }
    else {}
    return count;
  }


  public void updateCell(HashMap<Point, Cell> cellHashMap, int row, int col) {
    if((getNeighborCount(cellHashMap, row, col) > 3 || getNeighborCount(cellHashMap, row, col) < 2) && cellHashMap.get(new Point(row, col)).getState() == ALIVE) {
      setState(DEAD);
    }
    else if(getNeighborCount(cellHashMap, row, col) == 3 && cellHashMap.get(new Point(row, col)).getState() == DEAD) {
      setState(ALIVE);
    }
    else if(getNeighborCount(cellHashMap, row, col) >= 2 && cellHashMap.get(new Point(row, col)).getState() == ALIVE) {
      setState(ALIVE);
    }
    else {
      setState(state);
    }
  }

  public Image[] getImageRoot() {
    return new Image[0];
  }

  public int getNumNeighbors() {
    return 0;
  }

  public int getSate() {
    return this.state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public enum GameState {
    ALIVE,
    DEAD
  }
}
