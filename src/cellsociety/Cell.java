package cellsociety;

import java.awt.*;

public class Cell {
    private int currentState;
    private int nextState;
    private Image displayImage;

    public Cell(int currentState, int nextState, Image displayImage) {
        this.currentState = currentState;
        this.nextState = nextState;
        this.displayImage = displayImage;
    }

    public void updateCell() {

    }

    public int getState() {
        return currentState;
    }

    public void setState(int state) {
        this.currentState = nextState;
        //displayImage =
    }

   public void applyRules() {

   }

   public Image getImage() {
        return displayImage;
   }
}
