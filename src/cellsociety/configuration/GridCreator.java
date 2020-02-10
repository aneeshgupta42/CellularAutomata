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
    private static final int FIRE = 4;
    private static final int SEGREGATION = 2;
    private static final int RPS = 5;
    private Game myGame;

    public Grid defaultGridSelector(int choice){
        String[] choices = {"data/gameOfLife.xml", "data/percolation.xml", "data/segregation.xml", "data/predator.xml", "data/fire.xml", "data/rps.xml", "data/sugarScape.xml"};
        XMLReader reader = new XMLReader("media");
        Game game = reader.getGame(choices[choice]);
        myGame = game;
        Grid myGrid;
        if(choice == FIRE){
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), game.getMyProb(), game.getMyLayout(), game.getIsLayout(), game.getMyShape());
        }
        else if(choice == SEGREGATION){
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), game.getMyThreshold(), game.getMyLayout(), game.getIsLayout(), game.getMyShape());
        }
        else if(choice == RPS){
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), (int) game.getMyThreshold(), game.getMyLayout(), game.getIsLayout(), game.getMyShape());
        }
        else{
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), game.getMyLayout(), game.getIsLayout(), game.getMyShape());
        }
        return myGrid;
    }

    public Grid newGridSelector (Game game){
        myGame = game;
        int choice = myGame.getMyChoice();
        Grid myGrid;
        if(choice == FIRE){
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), game.getMyProb(), game.getMyLayout(), game.getIsLayout(), game.getMyShape());
        }
        else if(choice == SEGREGATION){
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), game.getMyThreshold(), game.getMyLayout(), game.getIsLayout(), game.getMyShape());
        }
        else if(choice == RPS){
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), (int) game.getMyThreshold(), game.getMyLayout(), game.getIsLayout(), game.getMyShape());
        }
        else{
            myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), game.getMyLayout(), game.getIsLayout(), game.getMyShape());
        }
        return myGrid;
    }

    public Game getMyGame() {
        return myGame;
    }
}
