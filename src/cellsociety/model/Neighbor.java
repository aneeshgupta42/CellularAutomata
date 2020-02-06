package cellsociety.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Neighbor {
  private int[] rowDelta;
  private int[] colDelta;

  public Neighbor() {
  }

  public int getNeighborCount(HashMap<Point, Cell> cellHashMap, int row, int col, int state) {
    int count = 0;
    int delta = 1;

    for(int i = 0; i < rowDelta.length; i++) {
      if(mapContainsNeighbor(cellHashMap, row + rowDelta[i], col + colDelta[i]) && checkState(cellHashMap, row + rowDelta[i], col + colDelta[i], state)) {
        count++;
      }
    }
    return count;
  }

  public int getMaxNeighborCount(HashMap<Point, Cell> cellHashMap, int row, int col, int[] states) {
    int max = 0;
    int maxState = 0;
    int comp = 0;
    for(int temp : states) {
      comp = getNeighborCount(cellHashMap, row, col, temp);
      if(max < comp) {
        max = comp;
        maxState = temp;
      }
    }
    return maxState;
  }

  public Point getMaxNeighborTypeCount(HashMap<Point, Cell> cellHashMap, int row, int col, int state) {
    int maxSugarAmount = ((SugarScapeCell) cellHashMap.get(new Point(row, col))).getMySugarCount();
    Point maxSugarPoint = new Point(row, col);
    int compSugar = 0;

    for(int i = 0; i < rowDelta.length; i++) {
      if(mapContainsNeighbor(cellHashMap, row + rowDelta[i], col + colDelta[i]) && checkState(cellHashMap, row + rowDelta[i], col + colDelta[i], state)) {
        compSugar = ((SugarScapeCell) cellHashMap.get(new Point(row + rowDelta[i], col + colDelta[i]))).getMySugarCount();
        if(compSugar > maxSugarAmount) {
          maxSugarAmount = compSugar;
          maxSugarPoint = new Point(row + rowDelta[i], col + colDelta[i]);
        }
      }
    }

    return maxSugarPoint;
  }

  public List<Point> getVacantNeighbors(HashMap<Point, Cell> cellHashMap, int row, int col, int state) {
    List<Point> vacantCells = new ArrayList<Point>();

    for(int i = 0; i < rowDelta.length; i++) {
      if(mapContainsNeighbor(cellHashMap, row + rowDelta[i], col + colDelta[i])
          && checkState(cellHashMap, row + rowDelta[i], col + colDelta[i], state)
          && checkNextState(cellHashMap, row + rowDelta[i], col + colDelta[i], state)) {
        vacantCells.add(new Point(row + rowDelta[i], col + colDelta[i]));
      }
    }
    return vacantCells;
  }

  public List<Point> getVacantCells(HashMap<Point, Cell> cellHashMap, int width, int height, int state) {
    List<Point> vacantCells  = new ArrayList<>();
    for(int i = 0; i < width; i++) {
      for(int k = 0; k < height; k++) {
        if(checkState(cellHashMap, i, k, state)
            && checkNextState(cellHashMap, i, k, state)) {
          vacantCells.add(new Point(i, k));
        }
      }
    }
    return vacantCells;
  }

  public List<Point> getTypeNeighbors(HashMap<Point, Cell> cellHashMap, int row, int col, int state) {
    List<Point> fishCells = new ArrayList<Point>();

    for(int i = 0; i < rowDelta.length; i++) {
      if(mapContainsNeighbor(cellHashMap, row + rowDelta[i], col + colDelta[i])
          && checkNextState(cellHashMap, row + rowDelta[i], col + colDelta[i], state)) {
        fishCells.add(new Point(row + rowDelta[i], col + colDelta[i]));
      }
    }

    return fishCells;

  }

  public abstract void updateRowColDeltas(int[] rowDeltaNew, int[] colDeltaNew);

  public abstract void setDirectNeighbors();
  public abstract void setAllNeighbors();

  public void setRowDelta(int[] rowDeltaNew) {
    this.rowDelta = rowDeltaNew;
  }

  public void setColDelta(int[] colDeltaNew) {
    this.colDelta = colDeltaNew;
  }

  public boolean mapContainsNeighbor(HashMap<Point, Cell> cellHashMap, int row, int col) {
    return cellHashMap.containsKey(new Point(row, col));
  }

  public boolean checkState(HashMap<Point, Cell> cellHashMap, int row, int col, int currState) {
    return cellHashMap.get(new Point(row, col)).getState() == currState;
  }

  private boolean checkNextState(HashMap<Point, Cell> cellHashMap, int row, int col, int nextState) {
    return cellHashMap.get(new Point(row, col)).getNextState() == nextState;
  }



}
