package cellsociety.Model;

import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.HashMap;

public class PredatorPreyCell extends Cell {
  private int state;
  private Color cellColor;

  public PredatorPreyCell(int row, int col, int myState) {
    super(row, col , myState);
    this.state = myState;
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


  public int updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height) {
    return 0;

  }

  @Override
  public int getState() {
    return state;
  }

  @Override
  public Color getCellColor() {
    return cellColor;
  }


  @Override
  protected void setCellColor() {

  }

  public enum PredatorPreyStates {
    SHARK,
    FISH,
    OPEN
  }
}
