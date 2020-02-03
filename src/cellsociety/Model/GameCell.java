package cellsociety.Model;

import javafx.scene.paint.Color;

import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;


public class GameCell extends Cell {
  //HashMap<Point, Cell> cellGrid = new HashMap<Point, Cell>();
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
  public int updateCell() {return 0;
  }

  @Override
  public int getState() {
    return state;
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

  public Color getCellColor() {
    return cellColor;
  }



  private int getNeighborCount(HashMap<Point, Cell> cellHashMap, int row, int col) {
    int count = 0;
    int delta = 1;
    //top left diagonal

    if(cellHashMap.containsKey(new Point(row - delta, col - delta)) && cellHashMap.get(new Point(row - delta, col - delta)).getState() == ALIVE) {
      count++;
    }
    //top
    if(cellHashMap.containsKey(new Point(row - delta, col)) && cellHashMap.get(new Point(row - delta, col)).getState() == ALIVE) {
      count++;
    }
    //top right diagonal
    if(cellHashMap.containsKey(new Point(row - delta, col + delta)) && cellHashMap.get(new Point(row - delta, col + delta)).getState() == ALIVE) {
      count++;
    }
    //left
    if(cellHashMap.containsKey(new Point(row, col - delta)) && cellHashMap.get(new Point(row, col - delta)).getState() == ALIVE) {
      count++;
    }
    //right
    if(cellHashMap.containsKey(new Point(row, col + delta)) && cellHashMap.get(new Point(row, col + delta)).getState() == ALIVE) {
      count++;
    }
    //bottom left diagonal
    if(cellHashMap.containsKey(new Point(row + delta, col - delta)) && cellHashMap.get(new Point(row + delta, col - delta)).getState() == ALIVE) {
      count++;
    }
    //bottom
    if(cellHashMap.containsKey(new Point(row + delta, col)) && cellHashMap.get(new Point(row + delta, col)).getState() == ALIVE) {
      count++;
    }
    //bottom right
    if(cellHashMap.containsKey(new Point(row + delta, col + delta)) && cellHashMap.get(new Point(row + delta, col + delta)).getState() == ALIVE) {
      count++;
    }

    return count;
  }


  public int updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height) {

    if((getNeighborCount(cellHashMap, row, col) > 3 || getNeighborCount(cellHashMap, row, col) < 2) && cellHashMap.get(new Point(row, col)).getState() == ALIVE) {
      return DEAD;
    }
    else if(getNeighborCount(cellHashMap, row, col) == 3 && cellHashMap.get(new Point(row, col)).getState() == DEAD) {
      return ALIVE;

    }
    else if(getNeighborCount(cellHashMap, row, col) >= 2 && cellHashMap.get(new Point(row, col)).getState() == ALIVE) {
      return ALIVE;

    }
    else {
      return state;
    }
  }


  public void setState(int state) {
    this.state = state;
  }

}
