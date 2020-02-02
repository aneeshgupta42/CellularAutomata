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

}
