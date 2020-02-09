package cellsociety.model;

public class RPSCell extends Cell {
  private int state;
  private static final int ROCK = 0;
  private static final int PAPER = 1;
  private static final int SCISSORS = 2;
  private int THRESHOLD;
  private String cellColor;

  private int neighborhoodChoice;

  /**
   * Constructor for the Cell object
   *
   * @param row     : row number cell is in
   * @param col     : column number cell is in
   * @param mystate : current state of the cell
   */
  public RPSCell(int row, int col, int mystate, int threshold) {
    super(row, col, mystate);
    neighborhoodChoice = 0;
    this.state = mystate;
    this.setCellColor();
    this.THRESHOLD = threshold;
    this.getNeighbors().setAllNeighbors();
  }

  @Override
  public int updateCell(Grid cellGrid, int row, int col, int width, int height) {
    int[] statesArr = {ROCK, PAPER, SCISSORS};
    int maxNeighborCount = this.getNeighbors().getMaxNeighborCount(cellGrid, row, col, statesArr);
    int compState = this.getNeighbors().getMaxNeighborState();
    int currState = cellGrid.getCell(row, col).getState();

    if(THRESHOLD < maxNeighborCount && stateWinChecks(compState, currState) == 1) {
      return this.getNeighbors().getMaxNeighborState();
    }
    else {
      return state;
    }
  }

  /**
   * Sets the color of the cell
   * @param state : state of the cell
   */
  public void setState(int state) {
    this.state = state;
  }


  @Override
  public int getState() {
    return this.state;
  }

  @Override
  public String getCellColor() {
    return cellColor;
  }

  @Override
  public void setCellColor() {
    if(state == ROCK) {
      cellColor = "gray";
    }
    else if(state == PAPER) {
      cellColor = "white";
    }
    else {
      cellColor = "black";
    }
  }


  public int stateWinChecks(int state1, int state2) {
    if(state1 == ROCK && state2 == PAPER
        || state1 == SCISSORS && state2 == ROCK
        || state1 == PAPER && state2 == SCISSORS) {
        return 0;
    }
    else {
      return 1;
    }
  }


}
