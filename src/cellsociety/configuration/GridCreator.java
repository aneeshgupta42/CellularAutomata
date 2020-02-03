package cellsociety.configuration;
import cellsociety.Model.*;

public class GridCreator {
    private String simulationName;
    private int simulationChoice;

    public GridCreator(){
        XMLReader reader = new XMLReader("media");
        Game game = reader.getGame("data/gameOfLife.xml");
        Grid myGrid = new Grid(game.getMyRows(), game.getMyCols(), game.getMyChoice());
    }
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
