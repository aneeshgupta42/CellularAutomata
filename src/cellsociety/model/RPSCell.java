package cellsociety.model;

public class RPSCell extends Cell {
  private int state;
  private static final int ROCK = 0;
  private static final int PAPER = 1;
  private static final int SCISSORS = 2;
  private int THRESHOLD;
  private String cellColor;


  /**
   * Constructor for the Cell object
   *
   * @param row     : row number cell is in
   * @param col     : column number cell is in
   * @param mystate : current state of the cell
   */
  public RPSCell(int row, int col, int mystate, int threshold,  int neighborhoodChoice) {
    super(row, col, mystate, neighborhoodChoice);
    this.state = mystate;
    this.setCellColor();
    this.THRESHOLD = threshold;
    setAllNeighbors(row, col);
  }

  @Override
  public int updateCell(Grid cellGrid, int row, int col, int width, int height) {
    int[] statesArr = {ROCK, PAPER, SCISSORS};
    int maxNeighborCount = this.getNeighbors().getMaxNeighborCount(cellGrid, row, col, statesArr);
    int compState = this.getNeighbors().getMaxNeighborState();
    int currState = cellGrid.getCell(row, col).getState();

    if(THRESHOLD < maxNeighborCount && stateWinChecks(compState, currState)) {
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


  //fix booleans
  public boolean stateWinChecks(int state1, int state2) {
    return true;
  }


}
