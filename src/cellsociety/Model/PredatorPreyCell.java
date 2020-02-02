package cellsociety.Model;

import java.awt.Point;
import java.util.HashMap;

public class PredatorPreyCell extends Cell {
  private int state;
  private static final int EMPTY = 0;
  private static final int FISH = 1;
  private static final int SHARK = 2;


  public PredatorPreyCell(int width, int height, int state) {
    super(width, height, state);
  }

  @Override
  public void updateCell() {

  }

  @Override
  public void updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height) {

  }

  @Override
  public int getState() {
    return 0;
  }



  @Override
  protected void setCellColor() {

  }

}
