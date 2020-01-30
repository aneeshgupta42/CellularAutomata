package cellsociety;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Simple immutable value object representing game product data.
 *
 * @author Robert C. Duvall
 */
public class Game {
    // name in data file that will indicate it represents data for this type of object
    public static final String DATA_TYPE = "Simulation";
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
    private int mySize;
    private int myRows;
    private int myCols;
    // NOTE: keep just as an example for converting toString(), otherwise not used
    private Map<String, String> myDataValues;


    /**
     * Create game data from given data.
     */
    public Game(String simName, String authName, int size, int rows, int cols) {
        simulationName = simName;
        author = authName;
        mySize = size;
        myRows = rows;
        myCols = cols;
        // NOTE: this is useful so our code does not fail due to a NullPointerException
        myDataValues = new HashMap<>();
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
