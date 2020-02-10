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
    private int myAge;
    private int deathCount = 0;
    private int visionDist = 0;
    private int mySugarCount; //for agent
    private int mySugarAmount; //for sugar cell
    private static final int MAX_SUGAR_AMOUNT = 17;
    private static final int METABOLISM_RATE = 3; //at every tick, the agents consumes this much sugar
    private static final int MINIMUM_AGE = 60;
    private static final int MAXIMUM_AGE = 100;
    private Random numChooser = new Random();


    /**
     * Constructor for the Cell object
     *
     * @param row     : row number cell is in
     * @param col     : column number cell is in
     * @param mystate : current state of the cell
     */
    public SugarScapeCell(int row, int col, int mystate,  int neighborhoodChoice) {
        super(row, col, mystate, neighborhoodChoice);
        this.setCellColor();
        this.state = mystate;
        this.myNextState = state;
        setAllNeighbors(row, col);
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
        mySugarAmount = 0;
        visionDist = 1;
        myAge = MINIMUM_AGE + numChooser.nextInt(40);
    }

    private void initializeSugar() {
        mySugarAmount = numChooser.nextInt(MAX_SUGAR_AMOUNT + 1);
    }

    private void initializeNewAgent(Grid cellGrid, int width, int heigtht) {
        List<Point> vacantCells =     this.getNeighbors().getVacantCells(cellGrid, width, heigtht, SUGAR_PATCH);
        Collections.shuffle(vacantCells);

        if(deathCount >= 1) {
            int tempState = state;
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
        if(checkState(cellGrid, row, col, SUGAR_PATCH)) {
            handleSugar();
        }
        else {
            handleAgent(cellGrid, row, col);
        }
        initializeNewAgent(cellGrid, width, height);
        return myNextState;
    }

    private void handleAgent(Grid cellGrid, int row, int col) {
        int tempState = state;
        metabolizeAgent();
        Point targetPt = this.getNeighbors().getMaxNeighborTypeCount(cellGrid, row, col, SUGAR_PATCH);
        if(cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY()).getState() == AGENT) {
            if(((SugarScapeCell) cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY())).getMySugarCount() <= 0
                || myAge > MAXIMUM_AGE) {
                deathCount++;
                handleNextAction(cellGrid, tempState, SUGAR_PATCH, targetPt);
            }
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
        nextCellType.setStateVariables(nextState);
    }

    private void handleSugar() {
        if(mySugarAmount < MAX_SUGAR_AMOUNT) {
            mySugarAmount++;
        }
    }

    @Override
    public int getState() {
        return 0;
    }

    @Override
    public String getCellColor() {
        return cellColor;
    }

    public int getMySugarCount() {
        return mySugarCount;
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

    private void increaseAge() {
        myAge++;
    }


    private void metabolizeAgent() {
        mySugarCount -= METABOLISM_RATE;
    }

}
