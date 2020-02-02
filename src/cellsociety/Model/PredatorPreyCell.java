<<<<<<< HEAD:src/Model/PredatorPreyCell.java
//package Model;
//
//import cellsociety.Grid;
//import java.awt.Image;
//import java.awt.Point;
//import java.util.HashMap;

//public class PredatorPreyCell extends Cell {
//
//  public PredatorPreyCell() {
//
//  }
//
//  @Override
//  public void updateCell() {
//
//  }
//
//  @Override
//  public void applyRules(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height) {
//
//  }
//
//  public enum PredatorPreyStates {
//    SHARK,
//    FISH,
//    OPEN
//  }
//}
=======
package cellsociety.Model;

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
  protected void setCellColor() {

  }

  public enum PredatorPreyStates {
    SHARK,
    FISH,
    OPEN
  }
}
>>>>>>> master:src/cellsociety/Model/PredatorPreyCell.java
