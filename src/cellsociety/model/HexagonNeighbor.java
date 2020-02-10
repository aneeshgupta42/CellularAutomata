package cellsociety.model;

public class HexagonNeighbor extends Neighbor {
  private int[] rowDelta;
  private int[] colDelta;

  @Override
  public void updateRowColDeltas(int[] rowDeltaNew, int[] colDeltaNew) {
    this.rowDelta = rowDeltaNew;
    this.colDelta = colDeltaNew;
  }

  @Override
  public void setDirectNeighbors() {
    setAllNeighbors();
  }

  @Override
  public void setAllNeighbors() {
    rowDelta = new int[]{-1, 0, 1, 1, 0, -1};
    colDelta = new int[]{-1, -1, -1, 0, 1, 0};
    setRowDelta(rowDelta);
    setColDelta(colDelta);
  }
}
