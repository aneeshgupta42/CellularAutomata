package cellsociety.model.cells;

import cellsociety.model.Grid;
import java.util.Collections;
import java.util.List;

import java.awt.Point;

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
  private String cellColor;
  private int myNextState;
  private static final int VACANT = 0;
  private static final int ZERO = 0;
  private static final int FISH = 1;
  private static final int SHARK = 2;
  private static final String WHITE = "white";
  private static final String BLUE = "blue";
  private static final String GRAY = "gray";

  private int energyLevel;
  private int breedingTime;

  private static final int MAX_BREEDING_TIME = 4;
  private static final int MAX_ENERGY_LEVEL = 7;
  private static final int MIN_ENERGY_LEVEL = 0;
  private static final int STARTING_ENERGY_LEVEL = 2;
  private List<Point> vacantCells;
  private List<Point> fishCells;

  /**
   * Constructor for the FireCell object
   * @param row: row number cell is in
   * @param col: column number cell is in
   * @param myState: current state of the cell
   */
  public PredatorPreyCell(int row, int col, int myState,  int neighborhoodChoice) {
    super(row, col , myState, neighborhoodChoice);
    this.state = myState;
    this.myNextState = state;
    this.breedingTime = ZERO;
    this.energyLevel = STARTING_ENERGY_LEVEL;
    this.myNextState = state;
    this.setCellColor();
    setDirectNeighbors(row, col);
  }

  /**
   * Updates the cell based on the rules
   * @param  cellGrid : grid of cells
   * @param  row : row the cell is in
   * @param  col : column the cell is in
   * @param  width : width of the grid
   * @param  height : height of the grid
   * @return int : the next state integer
   */
  @Override
  public int updateCell(Grid cellGrid, int row, int col, int width, int height) {
    getVacantCells(cellGrid, row, col);
    getFishCells(cellGrid, row, col);
    Collections.shuffle(vacantCells);
    Collections.shuffle(fishCells);

    if(checkState(cellGrid, row, col, FISH)) {
      handleFishState(cellGrid);
    }
    else if(checkState(cellGrid, row, col, SHARK)) {
      handleSharkState(cellGrid);
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
  public String getCellColor() {
    return cellColor;
  }

  /**
   * Sets the color of the cell
   */
  @Override
  public void setCellColor() {
    if(state == VACANT) {
      cellColor = WHITE;
    }
    else if(state == FISH) {
      cellColor = BLUE;
    }
    else {
      cellColor = GRAY;
    }
  }

  private void handleSharkState(Grid cellGrid) {
    increaseBreedingTime();
    decreaseEnergyLevel();
    int tempState = state;
    if(energyLevel == MIN_ENERGY_LEVEL) {
      myNextState = VACANT;
      setEnergyLevel(MAX_ENERGY_LEVEL);
    }
    else if(breedingTime > MAX_BREEDING_TIME && vacantCells.size() > ZERO) {
      handleNextAction(cellGrid, tempState, state, 0, vacantCells);
    }
    else if(fishCells.size() > ZERO) {
      increaseEnergyLevel();
      Point targetPt = fishCells.get(ZERO);
      handleNextAction(cellGrid, tempState, SHARK, getBreedingTime(), fishCells);
      ((PredatorPreyCell) cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY())).setEnergyLevel(getEnergyLevel());
    }
    else if(vacantCells.size() > ZERO) {
      Point targetPt = vacantCells.get(ZERO);
      handleNextAction(cellGrid, tempState, VACANT, getBreedingTime(), vacantCells);
      ((PredatorPreyCell) cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY())).setEnergyLevel(getEnergyLevel());
    }
    else {
      this.myNextState = SHARK;
    }
  }

  private void handleFishState(Grid cellGrid) {
    increaseBreedingTime();
    int tempState = state;
    if(breedingTime > MAX_BREEDING_TIME && vacantCells.size() > ZERO) {
      handleNextAction(cellGrid, tempState, state, ZERO, vacantCells);
    }
    else if(vacantCells.size() > ZERO) {
      handleNextAction(cellGrid, tempState, VACANT, getBreedingTime(), vacantCells);
    }
    else {
      this.myNextState = FISH;
    }
  }

  private void handleNextAction(Grid cellGrid, int tempState, int nextState, int newBreedingTime, List<Point> cellList) {
    myNextState = nextState;
    Point targetPt = cellList.get(ZERO);
    cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY()).setMyNextState(tempState);
//    cellHashMap.get(targetPt).setMyNextState(tempState);
    ((PredatorPreyCell) cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY())).setBreedingTime(newBreedingTime);
    setBreedingTime(ZERO);
    cellList.remove(ZERO);
  }

  private void getVacantCells(Grid grid, int row, int col) {
    vacantCells  =     this.getNeighbors().getVacantNeighbors(grid, row, col, VACANT);
  }

  private void getFishCells(Grid grid, int row, int col) {
    fishCells =     this.getNeighbors().getTypeNeighbors(grid, row, col,  FISH);
  }


  private void increaseBreedingTime() {
    breedingTime++;
  }
  private void decreaseEnergyLevel() {
    energyLevel--;
  }
  private void increaseEnergyLevel() {
    energyLevel = energyLevel + STARTING_ENERGY_LEVEL;
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
