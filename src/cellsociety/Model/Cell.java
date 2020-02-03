package cellsociety.Model;

import java.awt.*;
import java.util.HashMap;

public abstract class Cell {
    private int state;
    private int myNextState;
    private Image displayImage;
    Point gridPos;
    private Color color;

    public Cell(int row, int col, int mystate) {
      Point gridPos = new  Point(row, col);
      this.state = mystate;
    }


    public abstract int updateCell(HashMap<Point, Cell> cellHashMap, int row, int col, int width, int height);
    public abstract int getState();
    public abstract javafx.scene.paint.Color getCellColor();
    public abstract void setCellColor();

  public void setState(int state) {
    this.state = state;
  }

    public void setMyNextState(int nextState){
    this.myNextState = nextState;
  }


  public int getNextState(){
        return this.myNextState;
    }





}
