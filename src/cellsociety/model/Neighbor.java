package cellsociety.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class Neighbor {
  private int[] rowDelta;
  private int[] colDelta;
  private int maxState = 0;

  public Neighbor() {
  }

  public int getNeighborCount(Grid grid, int row, int col, int state) {
    int count = 0;
    int delta = 1;

    for(int i = 0; i < rowDelta.length; i++) {
      if(grid.gridContainsCell(row + rowDelta[i], col + colDelta[i]) && checkState(grid, row + rowDelta[i], col + colDelta[i], state)) {
        count++;
      }
    }
    return count;
  }

  public int getMaxNeighborCount(Grid grid, int row, int col, int[] states) {
    int max = 0;
    int comp = 0;
    for(int temp : states) {
      comp = getNeighborCount(grid, row, col, temp);
      if(max < comp) {
        max = comp;
        maxState = temp;
      }
    }
    return max;
  }

  public int getMaxNeighborState() {
    return maxState;
  }

  public Point getMaxNeighborTypeCount(Grid grid, int row, int col, int state) {
    int maxSugarAmount = ((SugarScapeCell) grid.getCell(row, col)).getMySugarCount();
    Point maxSugarPoint = new Point(row, col);
    int compSugar = 0;

    for(int i = 0; i < rowDelta.length; i++) {
      if(grid.gridContainsCell(row + rowDelta[i], col + colDelta[i]) && checkState(grid, row + rowDelta[i], col + colDelta[i], state)) {
        compSugar = ((SugarScapeCell) grid.getCell(row + rowDelta[i], col + colDelta[i])).getMySugarCount();
        if(compSugar > maxSugarAmount) {
          maxSugarAmount = compSugar;
          maxSugarPoint = new Point(row + rowDelta[i], col + colDelta[i]);
        }
      }
    }

    return maxSugarPoint;
  }

  public List<Point> getVacantNeighbors(Grid grid, int row, int col, int state) {
    List<Point> vacantCells = new ArrayList<Point>();

    for(int i = 0; i < rowDelta.length; i++) {
      if(grid.gridContainsCell(row + rowDelta[i], col + colDelta[i])
          && checkState(grid, row + rowDelta[i], col + colDelta[i], state)
          && checkNextState(grid, row + rowDelta[i], col + colDelta[i], state)) {
        vacantCells.add(new Point(row + rowDelta[i], col + colDelta[i]));
      }
    }
    return vacantCells;
  }

  public List<Point> getVacantCells(Grid grid, int width, int height, int state) {
    List<Point> vacantCells  = new ArrayList<>();
    for(int i = 0; i < width; i++) {
      for(int k = 0; k < height; k++) {
        if(checkState(grid, i, k, state)
            && checkNextState(grid, i, k, state)) {
          vacantCells.add(new Point(i, k));
        }
      }
    }
    return vacantCells;
  }

  public List<Point> getTypeNeighbors(Grid grid, int row, int col, int state) {
    List<Point> fishCells = new ArrayList<Point>();

    for(int i = 0; i < rowDelta.length; i++) {
      if(grid.gridContainsCell( row + rowDelta[i], col + colDelta[i])
          && checkNextState(grid, row + rowDelta[i], col + colDelta[i], state)) {
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

  public boolean mapContainsNeighbor(Grid grid, int row, int col) {
    return grid.gridContainsCell(row, col);
  }

  public boolean checkState(Grid grid, int row, int col, int currState) {
    return grid.getCell(row, col).getState() == currState;
  }

  private boolean checkNextState(Grid grid, int row, int col, int nextState) {
    return grid.getCell(row, col).getNextState() == nextState;
  }



}
