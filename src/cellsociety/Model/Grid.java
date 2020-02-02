package cellsociety.Model;

import cellsociety.Model.Cell;
import cellsociety.Model.FireCell;
import cellsociety.Model.GameCell;
//import cellsociety.Model.PercolationCell;
//import cellsociety.Model.PredatorPreyCell;
import cellsociety.Model.SegregationCell;

import java.awt.Point;
import java.util.HashMap;
import java.util.Random;

public class Grid {
  HashMap<Point, Cell> cellGrid;
  Random numChooser = new Random();

  public Grid(int width, int height, int choice) {
    cellGrid = new HashMap<Point, Cell>();
    populateGridCells(width, height, choice);
  }

  public void populateGridCells(int width, int height, int choice) {
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        Cell tempCell = getSimulation(width, height, numChooser.nextInt(2), choice);
        cellGrid.put(new Point(i, j), tempCell);
//        System.out.println(tempCell.getCellColor().toString());
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