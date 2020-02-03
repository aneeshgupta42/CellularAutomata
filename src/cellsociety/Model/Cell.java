package cellsociety.Model;

import java.awt.*;
import java.util.HashMap;

public abstract class Cell {
    private int state;
    private int myNextState;
    private Image displayImage;
    Point gridPos;
    private Color color;

    /*
    public Cell() {
        this.state = 0;
    }

     */
    public Cell(int row, int col, int mystate) {
      Point gridPos = new  Point(row, col);
      this.state = mystate;
      //System.out.println(state);
    }

    public int getRowPos() {
      return (int) gridPos.getX();
    }

    public int getColPos() {
      return (int) gridPos.getY();
    }

    public abstract int updateCell();
    public abstract int updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height);

    public abstract int getState();
    public abstract javafx.scene.paint.Color getCellColor();

    public void setState(int state) {
        this.state = state;
    }

    public int getNextState(){
        return this.myNextState;
    }

    public void setMyNextState(int nextState){
        this.myNextState = nextState;
    }

    //public abstract void applyRules(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height);

   public Image getImage() {
        return displayImage;
   }

  protected abstract void setCellColor();
}
