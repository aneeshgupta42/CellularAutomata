package cellsociety.configuration;
import cellsociety.model.*;
import cellsociety.view.Configpanel;
import cellsociety.view.MainView;

/***
 * Choosing a Grid object based on choice integer
 * Used in Display, and set up, to read in a grid
 * from an XML file
 * @author Aneesh Gupta
 * @author Shruthi Kumar
 * @author Chris Warren
 */
public class GridCreator {
    /***
     * Returns a Grid object based on a choice entered
     * takes in an integer choice,
     * and selects the corresponding simulation xml.
     * @param choice
     * @return  Grid object for corresponding simulation
     */

    private Configpanel configpanel;
    private MainView myMainview;

    public Grid GridSelector(int choice){
        String[] choices = {"data/gameOfLife.xml", "data/percolation.xml", "data/segregation.xml", "data/predator.xml", "data/fire.xml", "data/rps.xml", "data/sugarScape.xml"};
        XMLReader reader = new XMLReader("media");
        Game game = reader.getGame(choices[choice]);
        Grid myGrid;
        if(choice == 4){
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), game.getMyProb());
//            if (configpanel.getsubmitstatus() == true) {
////                myGrid = new Grid(configpanel.getNewRows(), configpanel.getNewCols(), game.getMyChoice(), game.getMyProb());
////            }
        }
        else if(choice == 2){
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), game.getMyThreshold());
//            if (configpanel.getsubmitstatus() == true) {
//                myGrid = new Grid(configpanel.getNewRows(), configpanel.getNewCols(), game.getMyChoice(), game.getMyThreshold());
//            }
        }
        else if(choice == 5) {
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), (int) game.getMyThreshold());
        }
        else{
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice());
//            if (configpanel.getsubmitstatus() == true) {
//               myGrid = new Grid(configpanel.getNewRows(), configpanel.getNewCols(), game.getMyChoice());
//            }
        }
        return myGrid;
    }
}
