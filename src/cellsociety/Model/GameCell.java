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
  public void updateCell() {

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



  private int getNeighborCount(HashMap<Point, Cell> cellHashMap, HashMap<Point, Cell> copycellHashMap, int row, int col) {
    int count = 0;
    int delta = 1;
    //top left diagonal

    //System.out.println(cellHashMap.get(new Point(row, col)).getState());
    if(copycellHashMap.containsKey(new Point(row - delta, col - delta)) && copycellHashMap.get(new Point(row - delta, col - delta)).getState() == ALIVE) {
      count++;
    }
    //top
    if(copycellHashMap.containsKey(new Point(row - delta, col)) && copycellHashMap.get(new Point(row - delta, col)).getState() == ALIVE) {
      count++;
    }
    //top right diagonal
    if(copycellHashMap.containsKey(new Point(row - delta, col + delta)) && copycellHashMap.get(new Point(row - delta, col + delta)).getState() == ALIVE) {
      count++;
    }
    //left
    if(copycellHashMap.containsKey(new Point(row, col - delta)) && copycellHashMap.get(new Point(row, col - delta)).getState() == ALIVE) {
      count++;
    }
    //right
    if(copycellHashMap.containsKey(new Point(row, col + delta)) && copycellHashMap.get(new Point(row, col + delta)).getState() == ALIVE) {
      count++;
    }
    //bottom left diagonal
    if(copycellHashMap.containsKey(new Point(row + delta, col - delta)) && copycellHashMap.get(new Point(row + delta, col - delta)).getState() == ALIVE) {
      count++;
    }
    //bottom
    if(copycellHashMap.containsKey(new Point(row + delta, col)) && copycellHashMap.get(new Point(row + delta, col)).getState() == ALIVE) {
      count++;
    }
    //bottom right
    if(copycellHashMap.containsKey(new Point(row + delta, col + delta)) && copycellHashMap.get(new Point(row + delta, col + delta)).getState() == ALIVE) {
      count++;
    }
    System.out.println(count);

    return count;
  }


  public void updateCell(HashMap<Point, Cell> cellHashMap, HashMap<Point, Cell> copycellHashMap, int row, int col, int width, int height) {
    //System.out.println("reached");

    if((getNeighborCount(cellHashMap, copycellHashMap, row, col) > 3 || getNeighborCount(cellHashMap, copycellHashMap, row, col) < 2) && copycellHashMap.get(new Point(row, col)).getState() == ALIVE) {
      setState(DEAD);
      //System.out.println("dead");
    }
    else if(getNeighborCount(cellHashMap, copycellHashMap, row, col) == 3 && copycellHashMap.get(new Point(row, col)).getState() == DEAD) {
      setState(ALIVE);
      //System.out.println("alive");

    }
    else if(getNeighborCount(cellHashMap, copycellHashMap, row, col) >= 2 && copycellHashMap.get(new Point(row, col)).getState() == ALIVE) {
      setState(ALIVE);
      //System.out.println("alive");

    }
    else {
      setState(state);
    }
    cellHashMap.get(new Point(row, col)).setCellColor();
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
