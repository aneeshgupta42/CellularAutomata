package cellsociety.Model;

import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.HashMap;

public class PredatorPreyCell extends Cell {

  public PredatorPreyCell() {

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
