package cellsociety;

import Model.Cell;
import Model.FireCell;
import Model.GameCell;
import Model.GameCell.GameState;
import Model.PercolationCell;
import Model.PredatorPreyCell;
import Model.SegregationCell;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;

public class Grid {
  HashMap<Point, Cell> cellGrid = new HashMap<Point, Cell>();

  public void populateGridCells(int width, int height, int choice, int state) {
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        cellGrid.put(new Point(i, j), getSimulation(width, height, choice, state));
      }
    }
  }

  public void updateGrid(int width, int height) {
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        cellGrid.get(new Point(i, j)).updateCell(cellGrid, i, j, width, height);
      }
    }
  }

  public HashMap<Point, Cell> getCellGrid() {
    return cellGrid;
  }

  public void step(int width, int height) {
    updateGrid(width, height);

  }

  public Cell[] getNeighbors() {
    return new Cell[0];
  }

  private Cell getSimulation(int width, int height, int state, int choice) {
    if(choice == 0) {
      return new GameCell(width, height, state);
    }
    else if(choice == 1) {
      return new PercolationCell(width, height, state);
    }
    else if(choice == 2) {
      return new SegregationCell(width, height, state);
    }
    else if(choice == 3) {
      return new PredatorPreyCell();
    }
    else {
      return new FireCell();
    }

  }

}
