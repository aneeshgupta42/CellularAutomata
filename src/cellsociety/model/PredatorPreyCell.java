package cellsociety.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.HashMap;

/**
 * PredatorPreyCell class based on Predator Prey WaTor simulation. Users can choose the predator-prey simulation and then cells are created
 * based on this type of simulation. Thus, PredatorPreyCell is an subclass
 * Purpose: This creates the predator-prey cells that will populate the grid. This class is now a subclass. We made this design decision because
 *  not all simulations have the same rules. Having extended cells classes will allow the predator-prey cell to have its own rule set while
 *  still implementing basic cell functions.
 * Assumptions: The class will work assuming all dependencies are functioning.
 * Dependencies: This class relies on the Grid class to instantiate it correctly and the Cell class to properly override its methods
 * Example: Choose a simulation and then the program will correctly instantiate the predator-prey cells.
 * @author Shruthi Kumar, Chris Warren, Aneesh Gupta
 */
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

  private Neighbor neighbors = new SquareNeighbor();
  private int neighborhoodChoice;

  /**
   * Constructor for the FireCell object
   * @param row: row number cell is in
   * @param col: column number cell is in
   * @param myState: current state of the cell
   */
  public PredatorPreyCell(int row, int col, int myState) {
    super(row, col , myState);
    this.state = myState;
    this.myNextState = state;
    this.breedingTime = 0;
    this.energyLevel = 2;
    this.myNextState = state;
    this.setCellColor();
    neighbors.setDirectNeighbors();
    neighborhoodChoice = 0;
  }

  /**
   * Updates the cell based on the rules
   * @param  cellHashMap: grid of cells
   * @param  row: row the cell is in
   * @param  col: column the cell is in
   * @param  width: width of the grid
   * @param  height : height of the grid
   * @return int : the next state integer
   */
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

  /**
   * Returns the state of the cell
   * @return state of the cell
   */
  @Override
  public int getState() {
    return this.state;
  }

  /**
   * Sets the color of the cell
   * @param myState : state of the cell
   */
  @Override
  public void setState(int myState){
    state = myNextState;
    myNextState = VACANT;
  }

  /**
   * Sets the color of the cell
   * @param nextState : next state of the cell
   */
  @Override
  public void setMyNextState(int nextState){
    this.myNextState = nextState;
  }

  /**
   * Returns the next state of the cell
   * @return next state of the cell
   */
  @Override
  public int getNextState(){
    return this.myNextState;
  }

  /**
   * Returns the color of the cell
   * @return color of the cell
   */
  @Override
  public Color getCellColor() {
    return cellColor;
  }

  /**
   * Sets the color of the cell
   */
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
    vacantCells  = neighbors.getVacantNeighbors(cellHashMap, row, col, VACANT);
  }

  private void getFishCells(HashMap<Point, Cell> cellHashMap, int row, int col) {
    fishCells = neighbors.getTypeNeighbors(cellHashMap, row, col,  FISH);
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



}
