package cellsociety.model;

public class SquareNeighbor extends Neighbor {


    private int[] rowDelta; // = {-1, -1, -1, 0, 0, 1, 1, 1};
    private int[] colDelta; // = {-1, 0, 1, -1, 1, -1, 0, 1};


  @Override
  public void updateRowColDeltas(int[] rowDeltaNew, int[] colDeltaNew) {
    this.rowDelta = rowDeltaNew;
    this.colDelta = colDeltaNew;
  }
}
