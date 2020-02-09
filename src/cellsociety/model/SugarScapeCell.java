package cellsociety.model;

import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SugarScapeCell extends Cell {
    private int state;
    private String cellColor;
    private int myNextState;
    private static final int SUGAR_PATCH = 0;
    private static final int AGENT = 1;
    private int patchSugar; //amount of sugar in a patch
    private int ticks; //number of ticks each cell has gone through
    private int visionDist;
    private int agentSugar; //amount of sugar an agent has
    private int myAge;
    private int deathCount = 0;

    private int mySugarAmount; //for sugar cell
    private int regenerationTime; //amount of time to wait before replenishing sugar
    private static final int MAX_REGENERATION_TIME = 4;
    private static final int MAX_SUGAR_AMOUNT = 10;
    private static final int METABOLISM_RATE = 3; //at every tick, the agents consumes this much sugar
    private static final int MINIMUM_AGE = 60;
    private static final int MAXIMUM_AGE = 100;
    private Random numChooser = new Random();

    //private Neighbor neighbors = new SquareNeighbor();
    private int neighborhoodChoice;

    /**
     * Constructor for the Cell object
     *
     * @param row     : row number cell is in
     * @param col     : column number cell is in
     * @param mystate : current state of the cell
     */
    public SugarScapeCell(int row, int col, int mystate) {
        super(row, col, mystate);
        this.state = mystate;
        this.setCellColor();
        this.myNextState = state;
        neighborhoodChoice = 0;
        regenerationTime = 0;
        ticks = 0;
        this.getNeighbors().setAllNeighbors();
        setStateVariables(mystate);
    }

    private void setStateVariables(int mystate) {
        if(mystate == SUGAR_PATCH) {
            initializeSugar();
        }
        else {
            initializeAgent();
        }
    }

    private void initializeAgent() {
        mySugarAmount = 4;
        visionDist = 1;
        myAge = MINIMUM_AGE + numChooser.nextInt(40);
    }

    private void initializeSugar() {
        mySugarAmount = numChooser.nextInt(MAX_SUGAR_AMOUNT + 1);
        regenerationTime = 0;
    }

    private void initializeNewAgent(Grid cellGrid, int width, int heigtht) {
        List<Point> vacantCells =     this.getNeighbors().getVacantCells(cellGrid, width, heigtht, SUGAR_PATCH);
        Collections.shuffle(vacantCells);

        if(deathCount >= 1) {
            int tempState = AGENT;
            myNextState = AGENT;
            Point targetPt = vacantCells.get(0);
            cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY()).setMyNextState(tempState);
            ((SugarScapeCell) cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY())).initializeAgent();
            vacantCells.remove(0);
        }

        deathCount = 0;
    }

    @Override
    public int updateCell(Grid cellGrid, int row, int col, int width,
                          int height) {
        if(checkState(cellGrid, row, col, SUGAR_PATCH) && checkNextState(cellGrid, row, col, SUGAR_PATCH)) {
            handleSugar();
        }
        else {
            handleAgent(cellGrid, row, col);
        }
        if(deathCount >= 1) {
            initializeNewAgent(cellGrid, width, height);
        }
        return myNextState;
    }

    private void handleAgent(Grid cellGrid, int row, int col) {
        int tempState = state;
        metabolizeAgent();
        Point targetPt = this.getNeighbors().getMaxNeighborTypeCount(cellGrid, row, col, SUGAR_PATCH);
        SugarScapeCell temp = (SugarScapeCell) cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY());
        ((SugarScapeCell) cellGrid.getCell(row, col)).setMySugarCount(temp.getMySugarCount());

        if((((SugarScapeCell) cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY()))
            .getMySugarCount() <= 0)
            || (myAge > MAXIMUM_AGE)) {
                deathCount++;
                handleNextAction(cellGrid, tempState, SUGAR_PATCH, targetPt);
            }

        else {
            handleNextAction(cellGrid, tempState, SUGAR_PATCH, targetPt);
        }
    }

    private void handleNextAction(Grid cellGrid, int tempState, int nextState, Point targetPt) {
        myNextState = nextState;
        cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY()).setMyNextState(tempState);
//        cellHashMap.get(targetPt).setMyNextState(tempState);
        SugarScapeCell nextCellType = (SugarScapeCell) cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY());
        nextCellType.setStateVariables(tempState);
    }

    private void handleSugar() {
        if(mySugarAmount < MAX_SUGAR_AMOUNT) {
            mySugarAmount++;
        }
    }

    @Override
    public int getState() {
        return this.state;
    }

    @Override
    public String getCellColor() {
        return cellColor;
    }

    public void setMySugarCount(int addedSugar) {
        mySugarAmount += addedSugar;
    }

    public int getMySugarCount() {
        return mySugarAmount;
    }

    /**
     * Sets the color of the cell
     * @param state : state of the cell
     */
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void setCellColor() {
        if(state == SUGAR_PATCH) {
            cellColor = "white";
        }
        else {
            cellColor = "black";
        }
    }

    private void setRegenerationTime(int time) {
        regenerationTime = time;
    }

    private void increaseAge() {
        myAge++;
    }


    private void metabolizeAgent() {
        mySugarAmount -= METABOLISM_RATE;
    }

    /**
     * Sets the color of the cell
     * @param nextState : next state of the cell
     */
    @Override
    public void setMyNextState(int nextState){
        this.myNextState = nextState;
    }

}
