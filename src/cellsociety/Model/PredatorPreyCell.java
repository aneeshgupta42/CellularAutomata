package cellsociety.Model;

import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.HashMap;

public class PredatorPreyCell extends Cell {

  public PredatorPreyCell() {
    super(0, 0 , 0);

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
    return 0;
  }

  @Override
  public Color getCellColor() {
    return null;
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
