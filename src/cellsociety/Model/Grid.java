package cellsociety.Model;

import cellsociety.Model.Cell;
import cellsociety.Model.FireCell;
import cellsociety.Model.GameCell;
//import cellsociety.Model.PercolationCell;
//import cellsociety.Model.PredatorPreyCell;
import cellsociety.Model.SegregationCell;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Grid {
  public static final int VACANT = 0;
  private HashMap<Point, Cell> cellGrid;
  private Random numChooser = new Random();
  private float myProb;
  private int myWidth;
  private int myHeight;
  private double myThreshold;
  private int myChoice;
  private static final int GAMEOFLIFE = 0;
  private static final int PERCOLATION = 1;
  private static final int SEGREGATION = 2;
  private static final int PREDATORPREY = 3;
  private static final int FIRE = 4;
  private static final int NUMSTATES = 3;



  //Game, Percolation
  public Grid(int width, int height, int choice) {
    cellGrid = new HashMap<Point, Cell>();
    myChoice = choice;
    myWidth = width;
    myHeight = height;
    populateGridCells(width, height, choice);
  }

  //fire
  public Grid(int width, int height, int choice, float prob){
    cellGrid = new HashMap<Point, Cell>();
    myProb = prob;
    myChoice = choice;
    myWidth = width;
    myHeight = height;
    populateGridCells(width, height, choice);
  }

  //segregation
  public Grid(int width, int height, int choice, double thresh){
    cellGrid = new HashMap<Point, Cell>();
    myThreshold = thresh;
    myChoice = choice;
    myWidth = width;
    myHeight = height;
    populateGridCells(width, height, choice);
  }

  public void populateGridCells(int width, int height, int choice) {
    Cell tempCell;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (choice == GAMEOFLIFE) {
          tempCell = makeGlider(i, j, choice);
        } else {
          tempCell = getSimulation(i, j, numChooser.nextInt(NUMSTATES), choice);
        }
        cellGrid.put(new Point(i, j), tempCell);
      }
    }
  }

  public Cell makeGlider(int i, int j, int choice){
    Cell tempCell;
    if (i == 2 && j == 3) {
      tempCell = getSimulation(i, j, 1, choice);
    }
    else if (i == 3 && j == 4) {
      tempCell = getSimulation(i, j, 1, choice);
    }
    else if (i == 4 && (j == 2 || j == 3 || j == 4)) {
      tempCell = getSimulation(i, j, 1, choice);
    }
    else tempCell = getSimulation(i,j,0,choice);
    return tempCell;
  }

  public void updateGrid(int width, int height) {
    //System.out.println("reached");
    HashMap<Point, Integer> newStateMap = new HashMap<>();
    int tempInitInt = 100;
    /*** if choice not segregation or predator***/
    for(int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newStateMap.put(new Point(i,j), tempInitInt);
        int newState = cellGrid.get(new Point(i, j)).updateCell(cellGrid, i, j, width, height);
        newStateMap.put(new Point(i,j), newState);
      }
    }

    for(int i = 0; i < height; i++) {
      for(int j = 0; j < width; j++) {
        cellGrid.get(new Point(i, j)).setState(newStateMap.get(new Point(i, j)));
        cellGrid.get(new Point(i, j)).setCellColor();
      }
    }

  }



  public HashMap<Point, Cell> getCellGrid() {
    return cellGrid;
  }

  public int getMyHeight() {
    return myHeight;
  }

  public int getMyWidth() {
    return myWidth;
  }

  private Cell getSimulation(int row, int col, int state, int choice) {
    if(choice == GAMEOFLIFE) {
      return new GameCell(row, col, state);
    }
    else if(choice == PERCOLATION) {
      return new PercolationCell(row, col, state);
    }
    else if(choice == SEGREGATION) {
      return new SegregationCell(row, col, state, myThreshold);
    }
    else if(choice == PREDATORPREY) {
      return new PredatorPreyCell(row, col, state);
    }
    else {
      return new FireCell(row, col, state, myProb);
    }
  }

  public int getChoice() {
      return this.myChoice;
  }
}
