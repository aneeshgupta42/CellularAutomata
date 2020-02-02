package cellsociety.Model;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Random;

public class FireCell extends Cell {

  private double probCatch = .5;
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
    return 0;
  }


  public void updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height) {
      if(cellHashMap.get(new Point(row, col)).getState() == TREE && getNeighborCount(cellHashMap, row, col) >= 1) {
        if(numChooser.nextDouble() < probCatch) {
          cellHashMap.get(new Point(row, col)).setState(BURNING);
        }
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


  private int getNeighborCount(HashMap<Point, Cell> cellHashMap, int row, int col) {
    int count = 0;
    int delta = 1;

    //top
    if(cellHashMap.containsKey(new Point(row - delta, col)) && cellHashMap.get(new Point(row - delta, col)).getState() == BURNING) {
      count++;
    }
    //left
    else if(cellHashMap.containsKey(new Point(row, col - delta)) && cellHashMap.get(new Point(row, col - delta)).getState() == BURNING) {
      count++;
    }
    //right
    else if(cellHashMap.containsKey(new Point(row, col + delta)) && cellHashMap.get(new Point(row, col + delta)).getState() == BURNING) {
      count++;
    }
    //bottom
    else if(cellHashMap.containsKey(new Point(row + delta, col)) && cellHashMap.get(new Point(row + delta, col)).getState() == BURNING) {
      count++;
    }
    else {}
    return count;
  }

  @Override
  protected void setCellColor() {
    if(state == 0) {
      cellColor = Color.YELLOW;
    }
    else if(state == 1) {
      cellColor = Color.GREEN;
    }
    else {
      cellColor = Color.RED;
    }
  }
  
}
