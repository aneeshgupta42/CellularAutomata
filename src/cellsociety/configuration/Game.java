package cellsociety.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Simple immutable value object representing simulation data.
 *
 * @author Robert C. Duvall
 * extended by Aneesh Gupta, Shruthi Kumar, Chris Warren
 */
public class Game {
    // name in data file that will indicate it represents data for this type of object
    public static final String DATA_TYPE = "sim";
    // field names expected to appear in data file holding values for this object
    // NOTE: simple way to create an immutable list
    public static final List<String> DATA_FIELDS = List.of(
        "name",
        "author",
        "size",
        "rows",
        "cols"
    );

    // specific data values for this instance
    private String simulationName;
    private String author;
    private int myChoice;
    private int mySize;
    private int myRows;
    private int myCols;
    private float myProb;
    private double myThreshold;

    // NOTE: keep just as an example for converting toString(), otherwise not used
    private Map<String, String> myDataValues;


    /**
     * Create game data from given data.
     */
    public Game(String simName, String authName, int choice, int size, int rows, int cols) {
        simulationName = simName;
        author = authName;
        myChoice = choice;
        mySize = size;
        myRows = rows;
        myCols = cols;
        // NOTE: this is useful so our code does not fail due to a NullPointerException
        myDataValues = new HashMap<>();
    }

    /***
     * Overloaded version of constructor with param for fire probability
     * @param simName: Name of Simulation
     * @param authName: author of sim file
     * @param choice: choice of sim
     * @param size: size of grid (for future use)
     * @param rows: rows in sim
     * @param cols: cols in sim
     * @param prob: probCatch for Fire
     */
    //Fire - has probability
    public Game(String simName, String authName, int choice, int size, int rows, int cols, float prob){
        this(simName, authName, choice, size,rows,cols);
        this.myProb = prob;
    }
    /***
     * Overloaded version of constructor with param for fire probability
     * @param simName: Name of Simulation
     * @param authName: author of sim file
     * @param choice: choice of sim
     * @param size: size of grid (for future use)
     * @param rows: rows in sim
     * @param cols: cols in sim
     * @param thresh: "Happiness" threshold for segregation
     */
    //Segregation - has threshold
    public Game(String simName, String authName, int choice, int size, int rows, int cols, double thresh){
        this(simName, authName, choice, size,rows,cols);
        this.myThreshold = thresh;
    }

    // NOTE: provides getters, but not setters
    /**
     * Returns title of this game.
     */
    public String getSimulationName() {
        return simulationName;
    }

    /**
     * Returns publisher of this game.
     */
    public String getAuthor() {
        return author;
    }

    /***
     * returns the number of columns in the game grid
     * @return myCols
     */
    public int getMyCols() {
        return myCols;
    }

    /***
     * returns the number of rows in the game grid
     * @return myRows
     */
    public int getMyRows() {
        return myRows;
    }

    /***
     * returns the size of the grid (for future use)
     * @return mySize
     */
    public int getMySize() {
        return mySize;
    }

    /***
     * returns the choice of simulation
     * @return my Choice
     */
    public int getMyChoice() {
        return myChoice;
    }

    /**
     * returns the prob of catching fire for the fire simulation
     */
    public float getMyProb() {
        return myProb;
    }

    /***
     * returns the Thrshold for simulation
     * @return myThreshold
     */
    public double getMyThreshold() {
        return myThreshold;
    }
}
