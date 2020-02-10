package cellsociety.configuration;

import cellsociety.model.Grid;

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
     */
    private static final int FIRE = 4;
    private static final int SEGREGATION = 2;
    private static final int RPS = 5;
    private Game myGame;

    /***
     * Easy dropdown select some standard simulations
     * Have hardcoded files in
     * @param choice: choice code for a sim
     * @return grid: Grid for that sim
     */
    public Grid defaultGridSelector(int choice){
        String[] defaultChoices = {"data/gameOfLife.xml", "data/percolation.xml", "data/segregation.xml", "data/predator.xml", "data/fire.xml", "data/rps.xml", "data/sugarScape.xml"};
        XMLReader reader = new XMLReader("media");
        Game game = reader.getGame(defaultChoices[choice]);
        myGame = game;
        return gridChooser(game, choice);
    }

    /***
     * Pass in a custom game to receive the grid for it
     * As opposed to taking in one from the default easy options
     * @param game
     * @return
     */
    public Grid newGridSelector (Game game){
        myGame = game;
        int choice = myGame.getMyChoice();
        return gridChooser(game, choice);
    }

    private Grid gridChooser(Game game, int choice){
        if(choice == FIRE){
            return new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), game.getMyProb(), game.getMyLayout(), game.getIsLayout(), game.getMyShape());
        }
        else if(choice == SEGREGATION){
            return new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), game.getMyThreshold(), game.getMyLayout(), game.getIsLayout(), game.getMyShape());
        }
        else if(choice == RPS){
            return new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), (int) game.getMyThreshold(), game.getMyLayout(), game.getIsLayout(), game.getMyShape());
        }
        else{
            return new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice(), game.getMyLayout(), game.getIsLayout(), game.getMyShape());
        }
    }

    /***
     * get the game selected/passed on
     * @return
     */
    public Game getMyGame() {
        return myGame;
    }
}
