package Model;

import cellsociety.Grid;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;

public class FireCell extends Cell {

  private final static double probCatch = .5;


  @Override
  public void updateCell() {

  }

  @Override
  public void updateCell(HashMap<Point, Cell> cellHashMap, int row, int col) {

  }

  @Override
  public int getState() {
    return 0;
  }

  @Override
  public void applyRules(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height) {

  }

  public enum FireStates {
    EMPTY,
    BURNING,
    TREE
  }
}
