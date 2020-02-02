package cellsociety.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Simple immutable value object representing simulation data.
 *
 * @author Robert C. Duvall
 * extended by Aneesh Gupta
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

    //Fire - has probability
    public Game(String simName, String authName, int choice, int size, int rows, int cols, float prob){
        this(simName, authName, choice, size,rows,cols);
        this.myProb = prob;
    }

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

    public int getMyCols() {
        return myCols;
    }

    public int getMyRows() {
        return myRows;
    }

    public int getMySize() {
        return mySize;
    }

    public int getMyChoice() {
        return myChoice;
    }

    public float getMyProb() {
        return myProb;
    }

    public double getMyThreshold() {
        return myThreshold;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString () {
        StringBuilder result = new StringBuilder();
        result.append(DATA_TYPE + " = [\n");
        for (Map.Entry<String, String> e : myDataValues.entrySet()) {
            result.append("  ").append(e.getKey()).append(" = '").append(e.getValue()).append("',\n");
        }
        result.append("]\n");
        return result.toString();
    }
}
