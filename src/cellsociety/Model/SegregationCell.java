package cellsociety.Model;

import javafx.scene.paint.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SegregationCell extends Cell {

  private int state;
  private int myNextState;
  private static final int VACANT = 0;
  private static final int AGENT1 = 1;
  private static final int AGENT2 = 2;
  private double THRESHOLD = .3;
  private Color cellColor;
  private List<Point> vacantCells;

  public SegregationCell(int width, int height, int state, double thresh) {
    super(width, height, state);
    this.state = state;
    this.myNextState = VACANT;
    this.THRESHOLD = thresh;
    this.setCellColor();
  }



  @Override
  public int updateCell() {
    return 0;
  }

  @Override


  /*
  @Override
  public void updateCell(HashMap<Point, Cell> cellHashMap, int row, int col) {

  }

   */


//  public void updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height) {
//    if(((double) getNeighborTypeCount(cellHashMap, row, col, cellHashMap.get(new Point(row, col)).getState())) / getNeighborCount(cellHashMap, row, col) < THRESHOLD) {
//      cellHashMap.put(vacantCells.get(0), new SegregationCell(width, height, cellHashMap.get(new Point(row, col)).getState(), THRESHOLD));
//      cellHashMap.remove(new Point(row, col));
//      //should remove random index
//      vacantCells.remove(0);
//    }
//;
//  }

//  @Override
  public int updateCell(HashMap<Point, Cell> cellHashMap,  HashMap<Point, Cell> copycellHashMap, int row, int col, int width, int height) {
    getVacantCells(cellHashMap, width, height);
    Collections.shuffle(vacantCells);
    double checkThreshold = ((double) getNeighborTypeCount(cellHashMap, row, col, cellHashMap.get(new Point(row, col)).getState())) / getNeighborCount(cellHashMap, row, col);
    if(checkThreshold < THRESHOLD && state!= VACANT) {
      int tempState = this.state;
      this.myNextState = VACANT;
      Point targetPt = vacantCells.get(0);
      cellHashMap.put(targetPt, new SegregationCell(width, height, cellHashMap.get(new Point(row, col)).getState(), THRESHOLD));
      cellHashMap.get(targetPt).setMyNextState(tempState);
      //should remove random index
      vacantCells.remove(0);
    }
    return myNextState;
  }


  @Override
  public int getState() {
    return this.state;
  }
  @Override
  public void setState(int myState){
//    if (myNextState!= VACANT){
//      System.out.println("changing vacancy");
//      this.state = myNextState;
//    }
     state = myNextState;
  }

  @Override
  public void setMyNextState(int nextState){
    this.myNextState = nextState;
  }

  @Override
  public int getNextState(){
    return this.myNextState;
  }

  public void setState(int myState, ArrayList<Point> vacantCells){
    this.state = myState;
  }

  @Override
  public javafx.scene.paint.Color getCellColor() {
    return cellColor;
  }


  @Override
  protected void setCellColor() {
    if(state == 0) {
      cellColor = Color.WHITE;
    }
    else if(state == 1) {
      cellColor = Color.BLUE;
    }
    else {
      cellColor = Color.RED;
    }
  }

  public void getVacantCells(HashMap<Point, Cell> cellHashMap, int width, int height) {
    vacantCells  = new ArrayList<>();
    for(int i = 0; i < width; i++) {
      for(int k = 0; k < height; k++) {
        if(cellHashMap.get(new Point(i, k)).getState() == VACANT && cellHashMap.get(new Point(i, k)).getNextState() == VACANT) {
          vacantCells.add(new Point(i, k));
        }
      }
    }
  }

  public int getNeighborCount(HashMap<Point, Cell> cellHashMap, int row, int col) {
    int count = 0;
    int delta = 1;
    //top left diagonal
    if(cellHashMap.containsKey(new Point(row - delta, col - delta)) && cellHashMap.get(new Point(row - delta, col - delta)).getState() != VACANT) {
      count++;
    }
    //top
    if(cellHashMap.containsKey(new Point(row - delta, col)) && cellHashMap.get(new Point(row - delta, col)).getState() != VACANT) {
      count++;
    }
    //top right diagonal
    if(cellHashMap.containsKey(new Point(row - delta, col + delta)) && cellHashMap.get(new Point(row - delta, col + delta)).getState() != VACANT) {
      count++;
    }
    //left
    if(cellHashMap.containsKey(new Point(row, col - delta)) && cellHashMap.get(new Point(row, col - delta)).getState() != VACANT) {
      count++;
    }
    //right
    if(cellHashMap.containsKey(new Point(row, col + delta)) && cellHashMap.get(new Point(row, col + delta)).getState() != VACANT) {
      count++;
    }
    //bottom left diagonal
    if(cellHashMap.containsKey(new Point(row + delta, col - delta)) && cellHashMap.get(new Point(row + delta, col - delta)).getState() != VACANT) {
      count++;
    }
    //bottom
    if(cellHashMap.containsKey(new Point(row + delta, col)) && cellHashMap.get(new Point(row + delta, col)).getState() != VACANT) {
      count++;
    }
    //bottom right
    if(cellHashMap.containsKey(new Point(row + delta, col + delta)) && cellHashMap.get(new Point(row + delta, col + delta)).getState() != VACANT) {
      count++;
    }
    else {}
    return count;
  }

  public int getNeighborTypeCount(HashMap<Point, Cell> cellHashMap, int row, int col, int state) {
    int count = 0;
    int delta = 1;
    //top left diagonal
    if(cellHashMap.containsKey(new Point(row - delta, col - delta)) && cellHashMap.get(new Point(row - delta, col - delta)).getState() == state) {
      count++;
    }
    //top
    if(cellHashMap.containsKey(new Point(row - delta, col)) && cellHashMap.get(new Point(row - delta, col)).getState() == state) {
      count++;
    }
    //top right diagonal
    if(cellHashMap.containsKey(new Point(row - delta, col + delta)) && cellHashMap.get(new Point(row - delta, col + delta)).getState() == state) {
      count++;
    }
    //left
    if(cellHashMap.containsKey(new Point(row, col - delta)) && cellHashMap.get(new Point(row, col - delta)).getState() == state) {
      count++;
    }
    //right
    if(cellHashMap.containsKey(new Point(row, col + delta)) && cellHashMap.get(new Point(row, col + delta)).getState() == state) {
      count++;
    }
    //bottom left diagonal
    if(cellHashMap.containsKey(new Point(row + delta, col - delta)) && cellHashMap.get(new Point(row + delta, col - delta)).getState() == state) {
      count++;
    }
    //bottom
    if(cellHashMap.containsKey(new Point(row + delta, col)) && cellHashMap.get(new Point(row + delta, col)).getState() == state) {
      count++;
    }
    //bottom right
    if(cellHashMap.containsKey(new Point(row + delta, col + delta)) && cellHashMap.get(new Point(row + delta, col + delta)).getState() == state) {
      count++;
    }
    else {}
    return count;

  }

  public enum SegregationStates {
    RED,
  BLUE,
    WHITE
  }
}
