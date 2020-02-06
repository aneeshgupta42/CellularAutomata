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

    private int mySugarCount; //for agent
    private int mySugarAmount; //for sugar cell
    private int regenerationTime; //amount of time to wait before replenishing sugar
    private static final int MAX_REGENERATION_TIME = 4;
    private static final int MAX_SUGAR_AMOUNT = 17;
    private static final int METABOLISM_RATE = 3; //at every tick, the agents consumes this much sugar
    private static final int MINIMUM_AGE = 60;
    private static final int MAXIMUM_AGE = 100;
    private Random numChooser = new Random();

    private Neighbor neighbors = new SquareNeighbor();
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
        this.setCellColor();
        this.state = mystate;
        this.myNextState = state;
        neighborhoodChoice = 0;
        regenerationTime = 0;
        ticks = 0;
        neighbors.setAllNeighbors();
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
        regenerationTime = 0;
    }

    private void initializeNewAgent(HashMap<Point, Cell> cellHashMap, int width, int heigtht) {
        List<Point> vacantCells = neighbors.getVacantCells(cellHashMap, width, heigtht, SUGAR_PATCH);
        Collections.shuffle(vacantCells);

        if(deathCount >= 1) {
            int tempState = state;
            myNextState = AGENT;
            Point targetPt = vacantCells.get(0);
            cellHashMap.get(targetPt).setMyNextState(tempState);
            ((SugarScapeCell) cellHashMap.get(targetPt)).initializeAgent();
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

    private void handleAgent(HashMap<Point, Cell> cellHashMap, int row, int col) {
        int tempState = state;
        metabolizeAgent();
        Point targetPt = neighbors.getMaxNeighborTypeCount(cellHashMap, row, col, SUGAR_PATCH);
        if(cellHashMap.get(targetPt).getState() == AGENT) {
            if(((SugarScapeCell) cellHashMap.get(targetPt)).getMySugarCount() <= 0
                || myAge > MAXIMUM_AGE) {
                deathCount++;
                handleNextAction(cellHashMap, tempState, SUGAR_PATCH, targetPt);
            }
        }
        else {
            handleNextAction(cellHashMap, tempState, SUGAR_PATCH, targetPt);
        }
    }

    private void handleNextAction(HashMap<Point, Cell> cellHashMap, int tempState, int nextState, Point targetPt) {
        myNextState = nextState;
        cellHashMap.get(targetPt).setMyNextState(tempState);
        SugarScapeCell nextCellType = (SugarScapeCell) cellHashMap.get(targetPt);
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
        mySugarCount -= METABOLISM_RATE;
    }

}
