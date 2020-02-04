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
  private static final int MAX_ENERGY_LEVEL = 7;
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

  @Override
  public int updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height) {
    getVacantCells(cellHashMap, row, col);
    getFishCells(cellHashMap, row, col);
    Collections.shuffle(vacantCells);
    Collections.shuffle(fishCells);

    if(checkState(cellHashMap, row, col, FISH)) {
      handleFishState(cellHashMap);
    }
    else if(checkState(cellHashMap, row, col, SHARK)) {
      handleSharkState(cellHashMap);
    }

    return myNextState;
  }

  private void handleSharkState(HashMap<Point, Cell> cellHashMap) {
    increaseBreedingTime();
    decreaseEnergyLevel();
    int tempState = state;
    if(energyLevel == 0) {
      myNextState = VACANT;
      setEnergyLevel(MAX_ENERGY_LEVEL);
    }
    else if(breedingTime > MAX_BREEDING_TIME && vacantCells.size() > 0) {
      handleNextAction(cellHashMap, tempState, state, 0, vacantCells);
    }
    else if(fishCells.size() > 0) {
      increaseEnergyLevel();
      Point targetPt = fishCells.get(0);
      handleNextAction(cellHashMap, tempState, SHARK, getBreedingTime(), fishCells);
      ((PredatorPreyCell) cellHashMap.get(targetPt)).setEnergyLevel(getEnergyLevel());
    }
    else if(vacantCells.size() > 0) {
      Point targetPt = vacantCells.get(0);
      handleNextAction(cellHashMap, tempState, VACANT, getBreedingTime(), vacantCells);
      ((PredatorPreyCell) cellHashMap.get(targetPt)).setEnergyLevel(getEnergyLevel());
    }
    else {
      this.myNextState = SHARK;
    }
  }

  private void handleFishState(HashMap<Point, Cell> cellHashMap) {
    increaseBreedingTime();
    int tempState = state;
    if(breedingTime > MAX_BREEDING_TIME && vacantCells.size() > 0) {
      handleNextAction(cellHashMap, tempState, state, 0, vacantCells);
    }
    else if(vacantCells.size() > 0) {
      handleNextAction(cellHashMap, tempState, VACANT, getBreedingTime(), vacantCells);
    }
    else {
      this.myNextState = FISH;
    }
  }

  private void handleNextAction(HashMap<Point, Cell> cellHashMap, int tempState, int nextState, int newBreedingTime, List<Point> cellList) {
    myNextState = nextState;
    Point targetPt = cellList.get(0);
    cellHashMap.get(targetPt).setMyNextState(tempState);
    ((PredatorPreyCell) cellHashMap.get(targetPt)).setBreedingTime(newBreedingTime);
    setBreedingTime(0);
    cellList.remove(0);
  }

  private void getVacantCells(HashMap<Point, Cell> cellHashMap, int row, int col) {
    vacantCells  = new ArrayList<>();
    int delta = 1;

    int[] rowDelta = {-1, 0, 0, 1};
    int[] colDelta = {0, -1, 1, 0};

    for(int i = 0; i < rowDelta.length; i++) {
      if(mapContainsNeighbor(cellHashMap, row + rowDelta[i], col + colDelta[i])
          && checkState(cellHashMap, row + rowDelta[i], col + colDelta[i], VACANT)
          && checkNextState(cellHashMap, row + rowDelta[i], col + colDelta[i], VACANT)) {
        vacantCells.add(new Point(row + rowDelta[i], col + colDelta[i]));
      }
    }
  }

  private void getFishCells(HashMap<Point, Cell> cellHashMap, int row, int col) {
    fishCells  = new ArrayList<>();
    int delta = 1;

    int[] rowDelta = {-1, 0, 0, 1};
    int[] colDelta = {0, -1, 1, 0};

    for(int i = 0; i < rowDelta.length; i++) {
      if(mapContainsNeighbor(cellHashMap, row + rowDelta[i], col + colDelta[i])
          && checkNextState(cellHashMap, row + rowDelta[i], col + colDelta[i], FISH)) {
        fishCells.add(new Point(row + rowDelta[i], col + colDelta[i]));
      }
    }
  }

  private boolean checkNextState(HashMap<Point, Cell> cellHashMap, int row, int col, int nextState) {
    return cellHashMap.get(new Point(row, col)).getNextState() == nextState;
  }

  private boolean checkState(HashMap<Point, Cell> cellHashMap, int row, int col, int currState) {
    return cellHashMap.get(new Point(row, col)).getState() == currState;
  }

  private boolean mapContainsNeighbor(HashMap<Point, Cell> cellHashMap, int row, int col) {
    return cellHashMap.containsKey(new Point(row, col));
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
  public void setCellColor() {
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
