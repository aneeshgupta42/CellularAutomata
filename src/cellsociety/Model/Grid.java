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
  ArrayList <Point> vacantCells;

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
    //System.out.println(choice);
    for(int i = 0; i < height; i++) {
      for(int j = 0; j < width; j++) {
//        if(i == 2 && j == 3) {
//          tempCell = getSimulation(i, j, 1, choice);
//        }
//        else if(i == 3 && j == 4) {
//           tempCell = getSimulation(i, j, 1, choice);
//
//        }
//        else if(i == 4 && (j == 2 || j == 3 || j == 4)) {
//           tempCell = getSimulation(i, j, 1, choice);
//
//        }
//        else
          tempCell = getSimulation(i, j, numChooser.nextInt(2), choice);

        cellGrid.put(new Point(i, j), tempCell);
      }
    }
  }

  public void updateGrid(int width, int height) {
    //System.out.println("reached");
    HashMap<Point, Cell> cellGridClone = copy(cellGrid);
    HashMap<Point, Integer> newStateMap = new HashMap<>();
    int tempInitInt = 100;
    vacantCells = getVacantCells(cellGrid, width, height);
    /*** if choice not segregation or predator***/
    for(int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newStateMap.put(new Point(i,j), tempInitInt);
        int newState = cellGrid.get(new Point(i, j)).updateCell(cellGrid, cellGridClone, i, j, width, height);
        newStateMap.put(new Point(i,j), newState);
      }
    }
    for(int i = 0; i < height; i++) {
      for(int j = 0; j < width; j++) {
        cellGrid.get(new Point(i, j)).setState(newStateMap.get(new Point(i, j)));
        cellGrid.get(new Point(i, j)).setCellColor();
        //System.out.println(cellGrid.get(new Point(i, j)).getState());
      }
    }

  }

  public ArrayList<Point> getVacantCells(HashMap<Point, Cell> cellHashMap, int width, int height) {
    ArrayList <Point> vacantCells = new ArrayList<>();
    for(int i = 0; i < width; i++) {
      for(int k = 0; k < height; k++) {
        if(cellHashMap.get(new Point(i, k)).getState() == VACANT) {
          vacantCells.add(new Point(i, k));
        }
      }
    }
    return vacantCells;
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

  public int getMyHeight() {
    return myHeight;
  }

  public int getMyWidth() {
    return myWidth;
  }

  private Cell getSimulation(int row, int col, int state, int choice) {
    if(choice == 0) {
      return new GameCell(row, col, state);
    }
    else if(choice == 1) {
      return new PercolationCell(row, col, state);
    }
    else if(choice == 2) {
      return new SegregationCell(row, col, state, myThreshold);
    }
    else if(choice == 3) {
      return new PredatorPreyCell(row, col, state);
    }
    else {
      return new FireCell(row, col, state, myProb);
    }

  }



}
