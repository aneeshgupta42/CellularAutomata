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
    private static final int ZERO = 0;
    private static final int MIN_SUGAR_AMOUNT = 0;
    private static final int STARTING_SUGAR_AMOUNT = 0;
    private static final String WHITE = "white";
    private static final String BLACK = "black";

    private static final int ONE = 1;


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
        this.state = mystate;
        this.setCellColor();
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
        mySugarAmount = STARTING_SUGAR_AMOUNT;
        visionDist = ONE;
        myAge = MINIMUM_AGE + numChooser.nextInt(40);
    }

    private void initializeSugar() {
        mySugarAmount = numChooser.nextInt(MAX_SUGAR_AMOUNT + 1);
    }

    private void initializeNewAgent(Grid cellGrid, int width, int height) {
        List<Point> vacantCells =     this.getNeighbors().getVacantCells(cellGrid, width, height, SUGAR_PATCH);
        Collections.shuffle(vacantCells);

        if(deathCount >= ONE && vacantCells.size() > ZERO) {
            int tempState = AGENT;
            myNextState = AGENT;
            Point targetPt = vacantCells.get(ZERO);
            cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY()).setMyNextState(tempState);
            ((SugarScapeCell) cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY())).initializeAgent();
            vacantCells.remove(ZERO);
        }

        deathCount = ZERO;

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
        if(deathCount >= ONE) {
            initializeNewAgent(cellGrid, width, height);
        }
        return myNextState;
    }

    private void handleAgent(Grid cellGrid, int row, int col) {
        int tempState = state;
        metabolizeAgent();
        increaseAge();
        Point targetPt = this.getNeighbors().getMaxNeighborTypeCount(cellGrid, row, col, SUGAR_PATCH);
        SugarScapeCell temp = (SugarScapeCell) cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY());
        ((SugarScapeCell) cellGrid.getCell(row, col)).setMySugarCount(temp.getMySugarCount());

        if((((SugarScapeCell) cellGrid.getCell((int) targetPt.getX(), (int) targetPt.getY()))
            .getMySugarCount() <= MIN_SUGAR_AMOUNT)
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
            cellColor = WHITE;
        }
        else {
            cellColor = BLACK;
        }
    }

    private void increaseAge() {
        myAge++;
    }


    private void metabolizeAgent() {
        mySugarCount -= METABOLISM_RATE;
    }

}
