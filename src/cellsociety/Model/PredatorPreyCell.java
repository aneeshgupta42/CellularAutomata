package cellsociety.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.HashMap;

public class PredatorPreyCell extends Cell {
  private int state;
  private Color cellColor;
  private int myNextState;
  private static final int VACANT = 0;
  private static final int FISH = 1;
  private static final int SHARK = 2;
  private int energyLevel;
  private int breedingState;
  private static final int MAX_BREEDING_TIME = 2;
  private List<Point> vacantCells;
  private List<Point> fishCells;

  public PredatorPreyCell(int row, int col, int myState) {
    super(row, col , myState);
    this.state = myState;
    this.myNextState = state;
    this.breedingState = 0;
    this.energyLevel = 2;
    this.myNextState = state;
    this.setCellColor();
  }

  @Override
  public int updateCell() {
    return 0;
  }

  private void increaseBreedingState() {
    breedingState++;
  }
  private void decreaseEnergyLevel() {
    energyLevel--;
  }
  private void increaseEnergyLevel() {
    energyLevel += 2;
  }

  @Override
  public int updateCell(HashMap<Point, Cell> cellHashMap, HashMap<Point, Cell> copycellHashMap,
      int row, int col, int width, int height) {
    getVacantCells(cellHashMap, row, col);
    Collections.shuffle(vacantCells);
    if(cellHashMap.get(new Point(row, col)).getState() == FISH) {
      increaseBreedingState();
      if(vacantCells.size() > 0) {
        int tempState = state;
        if(breedingState >= MAX_BREEDING_TIME) {
          myNextState = FISH;
          breedingState = 0;
        }
        else {
          myNextState = VACANT;
        }
        Point targetPt = vacantCells.get(0);
        cellHashMap.get(targetPt).setMyNextState(tempState);
        vacantCells.remove(0);
      }
    }
    else if(cellHashMap.get(new Point(row, col)).getState() == SHARK) {
      decreaseEnergyLevel();
      getFishCells(cellHashMap, row, col);
      Collections.shuffle(fishCells);
      if(fishCells.size() > 0) {
        increaseEnergyLevel();
        int tempState = state;
        myNextState = VACANT;
        Point targetPt = fishCells.get(0);
        cellHashMap.get(targetPt).setMyNextState(tempState);
        fishCells.remove(0);
      }
      else if(energyLevel <= 0) {
          myNextState = VACANT;
        }
      else if(vacantCells.size() > 0) {
        int tempState = state;
        myNextState = VACANT;
        Point targetPt = vacantCells.get(0);
        cellHashMap.get(targetPt).setMyNextState(tempState);
        vacantCells.remove(0);
      }

    }
    else if(state == VACANT){
      this.myNextState = state;
    }
    return myNextState;
  }

  private void getVacantCells(HashMap<Point, Cell> cellHashMap, int row, int col) {
    vacantCells  = new ArrayList<>();
    int delta = 1;

    if(cellHashMap.containsKey(new Point(row - delta, col)) && cellHashMap.get(new Point(row - delta, col)).getState() == VACANT) {
      vacantCells.add(new Point(row - delta, col));
    }
    //left
    if(cellHashMap.containsKey(new Point(row, col - delta)) && cellHashMap.get(new Point(row, col - delta)).getState() == VACANT) {
      vacantCells.add(new Point(row, col - delta));
    }
    //right
    if(cellHashMap.containsKey(new Point(row, col + delta)) && cellHashMap.get(new Point(row, col + delta)).getState() == VACANT) {
      vacantCells.add(new Point(row, col + delta));
    }
    //bottom
    if(cellHashMap.containsKey(new Point(row + delta, col)) && cellHashMap.get(new Point(row + delta, col)).getState() == VACANT) {
      vacantCells.add(new Point(row + delta, col));
    }
  }

  private void getFishCells(HashMap<Point, Cell> cellHashMap, int row, int col) {
    fishCells  = new ArrayList<>();
    int delta = 1;

    if(cellHashMap.containsKey(new Point(row - delta, col)) && cellHashMap.get(new Point(row - delta, col)).getState() == FISH) {
      fishCells.add(new Point(row - delta, col));
    }
    //left
    if(cellHashMap.containsKey(new Point(row, col - delta)) && cellHashMap.get(new Point(row, col - delta)).getState() == FISH) {
      fishCells.add(new Point(row, col - delta));
    }
    //right
    if(cellHashMap.containsKey(new Point(row, col + delta)) && cellHashMap.get(new Point(row, col + delta)).getState() == FISH) {
      fishCells.add(new Point(row, col + delta));
    }
    //bottom
    if(cellHashMap.containsKey(new Point(row + delta, col)) && cellHashMap.get(new Point(row + delta, col)).getState() == FISH) {
      fishCells.add(new Point(row + delta, col));
    }
  }

  /*
    public int updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height) {
      return 0;

    }


   */

  @Override
  public int getState() {
    return this.state;
  }
  @Override
  public void setState(int myState){
    state = myNextState;
    myNextState = VACANT;
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
  public Color getCellColor() {
    return cellColor;
  }


  @Override
  protected void setCellColor() {
    if(state == VACANT) {
      cellColor = Color.WHITE;
    }
    else if(state == FISH) {
      cellColor = Color.BLUE;
    }
    else {
      cellColor = Color.GRAY;
    }
  }

  public enum PredatorPreyStates {
    SHARK,
    FISH,
    OPEN
  }
}
