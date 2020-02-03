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
  public int updateCell() {
    return 0;
  }

  @Override
  public int updateCell(HashMap<Point, Cell> cellHashMap, HashMap<Point, Cell> copycellHashMap,
      int row, int col, int width, int height) {
    if(cellHashMap.get(new Point(row, col)).getState() == BURNING) {
      return EMPTY;
    }
    else if(cellHashMap.get(new Point(row, col)).getState() == TREE && getNeighborCount(cellHashMap, row, col) >= 1) {
      float chosen = numChooser.nextFloat();
      if(chosen < this.probCatch) {
        return BURNING;
      }
    }

    return state;
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

    //top
    if(cellHashMap.containsKey(new Point(row - delta, col)) && cellHashMap.get(new Point(row - delta, col)).getState() == BURNING) {
      count++;
    }
    //left
    if(cellHashMap.containsKey(new Point(row, col - delta)) && cellHashMap.get(new Point(row, col - delta)).getState() == BURNING) {
      count++;
    }
    //right
    if(cellHashMap.containsKey(new Point(row, col + delta)) && cellHashMap.get(new Point(row, col + delta)).getState() == BURNING) {
      count++;
    }
    //bottom
    if(cellHashMap.containsKey(new Point(row + delta, col)) && cellHashMap.get(new Point(row + delta, col)).getState() == BURNING) {
      count++;
    }
    return count;
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
      //changeToEmpty();
    }
  }


  private void changeToEmpty() {
    cellColor = Color.YELLOW;
  }

  public void setState(int state) {
    this.state = state;
  }

}
