package cellsociety.model;

public class HexagonNeighbor extends Neighbor {
  private int[] rowDelta;
  private int[] colDelta;

  /**
   * Updates the row and column deltas for the all the neighbors of a hexagon shape grid
   */
  @Override
  public void updateRowColDeltas(int[] rowDeltaNew, int[] colDeltaNew) {
    this.rowDelta = rowDeltaNew;
    this.colDelta = colDeltaNew;
  }

  /**
   * Sets the row and column deltas for the direct neighbors of a hexagon shape grid
   */
  @Override
  public void setDirectNeighbors() {
    setAllNeighbors();
  }

  /**
   * Sets the row and column deltas for all the neighbors of a hexagon shape grid
   */
  @Override
  public void setAllNeighbors() {
    rowDelta = new int[]{-1, 0, 1, 1, 0, -1};
    colDelta = new int[]{-1, -1, -1, 0, 1, 0};
    setRowDelta(rowDelta);
    setColDelta(colDelta);
  }
}
