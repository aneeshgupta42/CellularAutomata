package cellsociety.model.neighbors;

/**
 * Hexagon Neighbor class based on the hexagon shape for cells. Users can choose the shape they want in the xml files and then cells are shaped
 * based on the chosen value. Thus, HexagonNeighbor is an subclass
 * Purpose: This sets the row and column deltas for the hexagon shape grid. This class is now a subclass. We made this design decision because
 *  not all grids have the same number or location of neighbors. We can set row and column deltas for these classes/shapes to determine which are the
 *  direct neighbors and which are all the neighbors. Having extended neighbor classes will allow the hexgaon shape to have its own way of accessing
 *  neighbors while still implementing basic neighbor functions.
 * Assumptions: The class will work assuming all dependencies are functioning.
 * Dependencies: This class relies on the Cell class to instantiate it correctly and the Neighbor class to properly override its methods
 * Example: Choose a simulation and then the program will correctly instatiate the hexagon shapes.
 * @author Shruthi Kumar
 */
public class HexagonNeighbor extends Neighbor {
  private int[] rowDelta;
  private int[] colDelta;

  /**
   * Constructor for Hexagon Neighbor object
   */
  public HexagonNeighbor() {
    super();
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
