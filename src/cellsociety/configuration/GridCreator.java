package cellsociety.configuration;
import cellsociety.Model.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/***
 * Choosing a Grid object based on choice integer
 * Used in Display, and set up, to read in a grid
 * from an XML file
 * @author Aneesh Gupta
 */
public class GridCreator {
    /***
     * Returns a Grid object based on a choice entered
     * takes in an integer choice,
     * and selects the corresponding simulation xml.
     * @param choice
     * @return  Grid object for corresponding simulation
     */
    public Grid GridSelector(int choice){
        String[] choices = {"data/gameOfLife.xml", "data/percolation.xml", "data/segregation.xml", "data/predator.xml", "data/fire.xml"};
        XMLReader reader = new XMLReader("media");
        Game game = reader.getGame(choices[choice]);
        Grid myGrid;
        if(choice == 4){
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), game.getMyProb());
        }
        else if(choice == 2){
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), game.getMyThreshold());
        }
        else{
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice());
        }
        return myGrid;
    }
}
