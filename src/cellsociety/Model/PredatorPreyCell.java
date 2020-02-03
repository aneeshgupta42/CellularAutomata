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
  private int breedingTime;
  private static final int MAX_BREEDING_TIME = 4;
  private static final int MAX_ENERGY_LEVEL = 4;
  private List<Point> vacantCells;
  private List<Point> fishCells;

  public PredatorPreyCell(int row, int col, int myState) {
    super(row, col , myState);
    this.state = myState;
    this.myNextState = state;
    this.breedingTime = 0;
    this.energyLevel = 2;
    this.myNextState = state;
    this.setCellColor();
  }



  private void increaseBreedingTime() {
    breedingTime++;
  }
  private void decreaseEnergyLevel() {
    energyLevel--;
  }
  private void increaseEnergyLevel() {
    energyLevel = energyLevel + 2;
  }

  @Override
  public int updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height) {
    getVacantCells(cellHashMap, row, col);
    getFishCells(cellHashMap, row, col);
    Collections.shuffle(vacantCells);
    Collections.shuffle(fishCells);

    if(cellHashMap.get(new Point(row, col)).getState() == FISH ) {
      increaseBreedingTime();
      int tempState = state;
      if(breedingTime > MAX_BREEDING_TIME && vacantCells.size() > 0) {
        setBreedingTime(0);
        myNextState = state;
        Point targetPt = vacantCells.get(0);
        cellHashMap.get(targetPt).setMyNextState(tempState);
        ((PredatorPreyCell) cellHashMap.get(targetPt)).setBreedingTime(0);
        vacantCells.remove(0);
      }
      else if(vacantCells.size() > 0) {
        myNextState = VACANT;
        Point targetPt = vacantCells.get(0);
        cellHashMap.get(targetPt).setMyNextState(tempState);
        ((PredatorPreyCell) cellHashMap.get(targetPt)).setBreedingTime(getBreedingTime());
        setBreedingTime(0);
        vacantCells.remove(0);
      }
      else {
        this.myNextState = FISH;
      }
    }
    else if(cellHashMap.get(new Point(row, col)).getState() == SHARK) {
      increaseBreedingTime();
      decreaseEnergyLevel();
      int tempState = state;
      if(energyLevel == 0) {
        myNextState = VACANT;
        setEnergyLevel(MAX_ENERGY_LEVEL);
      }
      else if(breedingTime > MAX_BREEDING_TIME && vacantCells.size() > 0) {
        setBreedingTime(0);
        myNextState = state;
        Point targetPt = vacantCells.get(0);
        cellHashMap.get(targetPt).setMyNextState(tempState);
        ((PredatorPreyCell) cellHashMap.get(targetPt)).setBreedingTime(0);
        vacantCells.remove(0);
      }
      else if(fishCells.size() > 0) {
        increaseEnergyLevel();
        myNextState = SHARK;
        Point targetPt = fishCells.get(0);
        cellHashMap.get(targetPt).setMyNextState(tempState);
        ((PredatorPreyCell) cellHashMap.get(targetPt)).setEnergyLevel(getEnergyLevel());
        ((PredatorPreyCell) cellHashMap.get(targetPt)).setBreedingTime(getBreedingTime());
        setBreedingTime(0);
        fishCells.remove(0);
      }
      else if(vacantCells.size() > 0) {
        myNextState = VACANT;
        Point targetPt = vacantCells.get(0);
        cellHashMap.get(targetPt).setMyNextState(tempState);
        ((PredatorPreyCell) cellHashMap.get(targetPt)).setEnergyLevel(getEnergyLevel());
        ((PredatorPreyCell) cellHashMap.get(targetPt)).setBreedingTime(getBreedingTime());
        setBreedingTime(0);
        vacantCells.remove(0);
      }
      else {
        this.myNextState = SHARK;
      }
    }

    return myNextState;
  }

  private void getVacantCells(HashMap<Point, Cell> cellHashMap, int row, int col) {
    vacantCells  = new ArrayList<>();
    int delta = 1;

    if(cellHashMap.containsKey(new Point(row - delta, col)) && cellHashMap.get(new Point(row - delta, col)).getState() == VACANT
    && cellHashMap.get(new Point(row - delta, col)).getNextState() == VACANT) {
      vacantCells.add(new Point(row - delta, col));
    }
    //left
    if(cellHashMap.containsKey(new Point(row, col - delta)) && cellHashMap.get(new Point(row, col - delta)).getState() == VACANT
    && cellHashMap.get(new Point(row, col - delta)).getNextState() == VACANT) {
      vacantCells.add(new Point(row, col - delta));
    }
    //right
    if(cellHashMap.containsKey(new Point(row, col + delta)) && cellHashMap.get(new Point(row, col + delta)).getState() == VACANT
    && cellHashMap.get(new Point(row, col + delta)).getNextState() == VACANT) {
      vacantCells.add(new Point(row, col + delta));
    }
    //bottom
    if(cellHashMap.containsKey(new Point(row + delta, col)) && cellHashMap.get(new Point(row + delta, col)).getState() == VACANT
    && cellHashMap.get(new Point(row + delta, col)).getNextState() == VACANT) {
      vacantCells.add(new Point(row + delta, col));
    }
  }

  private void getFishCells(HashMap<Point, Cell> cellHashMap, int row, int col) {
    fishCells  = new ArrayList<>();
    int delta = 1;

    if(cellHashMap.containsKey(new Point(row - delta, col))
    && cellHashMap.get(new Point(row - delta, col)).getNextState() == FISH) {
      fishCells.add(new Point(row - delta, col));
    }
    //left
    if(cellHashMap.containsKey(new Point(row, col - delta))
        && cellHashMap.get(new Point(row, col - delta)).getNextState() == FISH) {
      fishCells.add(new Point(row, col - delta));
    }
    //right
    if(cellHashMap.containsKey(new Point(row, col + delta))
        && cellHashMap.get(new Point(row, col + delta)).getNextState() == FISH) {
      fishCells.add(new Point(row, col + delta));
    }
    //bottom
    if(cellHashMap.containsKey(new Point(row + delta, col))
        && cellHashMap.get(new Point(row + delta, col)).getNextState() == FISH) {
      fishCells.add(new Point(row + delta, col));
    }
  }



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

  @Override
  public Color getCellColor() {
    return cellColor;
  }

  private void setEnergyLevel(int level) {
    energyLevel = level;
  }

  private void setBreedingTime(int time) {
    breedingTime = time;
  }

  private int getEnergyLevel() {
    return energyLevel;
  }

  private int getBreedingTime(){
    return breedingTime;
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

}
