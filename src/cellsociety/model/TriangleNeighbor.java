package cellsociety.model;

public class TriangleNeighbor extends Neighbor {
  private int[] rowDelta; // = {-1, -1, -1, 0, 0, 1, 1, 1};
  private int[] colDelta; // = {-1, 0, 1, -1, 1, -1, 0, 1};


  public TriangleNeighbor() {
  }

  @Override
  public void updateRowColDeltas(int[] rowDeltaNew, int[] colDeltaNew) {
    this.rowDelta = rowDeltaNew;
    this.colDelta = colDeltaNew;
  }

  @Override
  public void setDirectNeighbors() {
    rowDelta = new int[]{0, 0, -1};
    colDelta = new int[]{-1, 1, 0};
    setRowDelta(rowDelta);
    setColDelta(colDelta);
  }

  @Override
  public void setAllNeighbors() {
    rowDelta = new int[]{-1, -1, -1, -1, -1, 0, 0, 0, 0, 1, 1, 1};
    colDelta = new int[]{-2, -1, 0, 1, 2, -2, -1, 1, 2, -1, 0, 1};
    setRowDelta(rowDelta);
    setColDelta(colDelta);
  }

  public void setDirectNeighborsUpTri() {
    rowDelta = new int[]{0, 0, 1};
    colDelta = new int[]{-1, 1, 0};
    setRowDelta(rowDelta);
    setColDelta(colDelta);
  }

  public void setAllNeighborsUpTri() {
    rowDelta = new int[]{-1, -1, -1, 0, 0, 0, 0, 1, 1, 1, 1, 1};
    colDelta = new int[]{-1, 0, 1, -1, 1, -2, 2, -2, -1, 0, 1, 2};
    setRowDelta(rowDelta);
    setColDelta(colDelta);
  }


}
