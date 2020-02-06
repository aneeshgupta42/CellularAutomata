package cellsociety.model;

import java.awt.Point;
import java.util.HashMap;
import javafx.scene.paint.Color;

public class RPSCell extends Cell {
  private int state;
  private static final int ROCK = 0;
  private static final int PAPER = 1;
  private static final int SCISSORS = 0;
  private int THRESHOLD;
  private Color cellColor;

  private Neighbor neighbors = new SquareNeighbor();

  private int neighborhoodChoice;
  private int threshold;

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
    neighbors.setAllNeighbors();
  }

  @Override
  public int updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height) {
    int[] statesArr = {ROCK, PAPER, SCISSORS};
    int maxNeighborCount = neighbors.getMaxNeighborCount(cellHashMap, row, col, statesArr);

    if(threshold < maxNeighborCount) {
      return maxNeighborCount;
    }
    else {
      return state;
    }
  }


  @Override
  public int getState() {
    return this.state;
  }

  @Override
  public Color getCellColor() {
    return cellColor;
  }

  @Override
  public void setCellColor() {
    if(state == ROCK) {
      cellColor = Color.GRAY;
    }
    else if(state == PAPER) {
      cellColor = Color.WHITE;
    }
    else {
      cellColor = Color.BLACK;
    }
  }
}
