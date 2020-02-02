package cellsociety.Model;

import cellsociety.Model.Cell;
import cellsociety.Model.FireCell;
import cellsociety.Model.GameCell;
//import cellsociety.Model.PercolationCell;
//import cellsociety.Model.PredatorPreyCell;
import cellsociety.Model.SegregationCell;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Grid {
  HashMap<Point, Cell> cellGrid;
  Random numChooser = new Random();

  public Grid(int width, int height, int choice) {
    cellGrid = new HashMap<Point, Cell>();
    populateGridCells(width, height, choice);
  }

  public void populateGridCells(int width, int height, int choice) {
    Cell tempCell;
    for(int i = 0; i < height; i++) {
      for(int j = 0; j < width; j++) {
        if(i == 2 && j == 3) {
          tempCell = getSimulation(i, j, 1, choice);
        }
        else if(i == 3 && j == 4) {
           tempCell = getSimulation(i, j, 1, choice);

        }
        else if(i == 4 && (j == 2 || j == 3 || j == 4)) {
           tempCell = getSimulation(i, j, 1, choice);

        }
        else {
           tempCell = getSimulation(i, j, 0, choice);
        }
        //System.out.println(tempCell);
        //System.out.println(tempCell.getState());
        cellGrid.put(new Point(i, j), tempCell);

//        System.out.println(tempCell.getCellColor().toString());
      }
    }
  }

  public void updateGrid(int width, int height) {
    HashMap<Point, Cell> cellGridClone = copy(cellGrid);

    for(int i = 0; i < height; i++) {
      for(int j = 0; j < width; j++) {
        cellGrid.get(new Point(i, j)).updateCell(cellGrid, cellGridClone, i, j, width, height);
        //System.out.println(cellGrid.get(new Point(i, j)).getState());
      }
    }
  }

  public HashMap<Point, Cell> copy(HashMap<Point, Cell> original) {
    HashMap<Point, Cell> cellGridClone = new HashMap<Point, Cell>();
    for(Map.Entry<Point, Cell> entry: original.entrySet()) {
      cellGridClone.put(entry.getKey(), entry.getValue());
    }
    return cellGridClone;
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

  private Cell getSimulation(int row, int col, int state, int choice) {
    if(choice == 0) {
      return new GameCell(row, col, state);
    }
    else if(choice == 1) {
      return new PercolationCell(row, col, state);
    }
    else if(choice == 2) {
      return new SegregationCell(row, col, state);
    }
    else if(choice == 3) {
      return new PredatorPreyCell();
    }
    else {
      return new FireCell(0, 0, 0);
    }

  }

}
