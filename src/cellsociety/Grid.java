package cellsociety;

import Model.Cell;
// import Model.FireCell;
import Model.GameCell;
import Model.GameCell.GameState;
// import Model.PercolationCell;
//import Model.PredatorPreyCell;
// import Model.SegregationCell;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;

public class Grid {
  HashMap<Point, Cell> cellGrid = new HashMap<Point, Cell>();

  public void populateGridCells(int width, int height, int choice) {
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        cellGrid.put(new Point(i, j), getSimulation(width, height, choice));
      }
    }
  }

  public void updateGrid(int width, int height, Grid grid, Cell[] neighborCells, Cell currentCell) {
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        cellGrid.get(new Point(i, j)).updateCell();
      }
    }
  }
  public void step() {

  }

  public Cell[] getNeighbors() {
    return new Cell[0];
  }

  /*
  public Image[] getImageRoot() {

  }

   */

  private Cell getSimulation(int width, int height, int choice) {
    if(choice == 0) {
      return new GameCell(width, height);
    }
//    else if(choice == 1) {
//      return new PercolationCell();
//    }
//    else if(choice == 2) {
//      return new SegregationCell();
//    }
//    else if(choice == 3) {
//      return new PredatorPreyCell();
//    }
//    else {
//      return new FireCell();
//    }
      return null;
  }

}
