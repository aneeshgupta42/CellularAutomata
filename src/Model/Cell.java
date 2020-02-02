package Model;

import java.awt.*;
import java.util.HashMap;

public abstract class Cell {
    private int state;
    private String nextState;
    private Image displayImage;
    Point gridPos;
    private Color color;

    public Cell() {
      this.state = 0;
    }
    public Cell(int row, int col, int state) {
      Point gridPos = new  Point(row, col);
      //this.state = 0;
    }

    public int getRowPos() {
      return (int) gridPos.getX();
    }

    public int getColPos() {
      return (int) gridPos.getY();
    }

    public abstract void updateCell();
    public abstract void updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height);

    public abstract int getState();

    public void setState(int state) {
        this.state = state;
        //displayImage =
    }

   //public abstract void applyRules(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height);

   public Image getImage() {
        return displayImage;
   }

  protected abstract void setCellColor();
}
