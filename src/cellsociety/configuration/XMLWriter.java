package cellsociety.configuration;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import cellsociety.model.Grid;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/***
 * Class for writing out or "Downloading" XML files
 * for the simulations being run
 * Dependencies: Pass in a Grid and Game object
 * @author Aneesh Gupta
 * @author Shruthi Kumar
 * @author Chris Warren
 */
public class XMLWriter {

    private static final String xmlFilePath = "data/savedfiles/";
    private static final double BASE_VALUE = 0.0;
    private String simName, authName, layout;
    private int choice, myRows, myCols, myShape;
    private float myProb;
    private double myThreshold;
    private Grid myGrid;
    private Game myGame;
    private DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
    private Document document = documentBuilder.newDocument();

    /***
     * Constructor for the writer object
     * Intiailizes the writing out mechanism,
     * @param grid: grid with current states
     * @param game: game, with broader things like name, author, etc
     * @throws Exception: writing or parsing exceptions
     */
    public XMLWriter(Grid grid, Game game) throws Exception {
        myGrid = grid;
        myGame = game;
        simName = game.getSimulationName();
        authName = game.getAuthor();
        choice = game.getMyChoice();
        myRows = game.getMyRows();
        myCols = game.getMyCols();
        myShape = game.getMyShape();
        try{
            myProb = game.getMyProb();
        }
        catch(Exception e){
            myProb = (float) BASE_VALUE;
            throw new Exception("Not a fire type simulation");
        }
        try{
            myThreshold = game.getMyThreshold();
        }
        catch(Exception e){
            myThreshold = BASE_VALUE;
            throw new Exception("Not a segregation or threshold type");
        }
        getLayout();
    }

    private void getLayout(){
        layout = "";
        for(int i = 0; i<myGame.getMyRows(); i++){
            for(int j = 0; j<myGame.getMyCols(); j++){
                layout += Integer.toString(myGrid.getCell(i,j).getState());
                layout += " ";
            }
            layout += "\n";
        }
    }

    private void addElements(){
        Element root = document.createElement("data");
        document.appendChild(root);
        Attr attr = document.createAttribute("media");
        attr.setValue("sim");
        root.setAttributeNode(attr);

        Element name = document.createElement("name");
        name.appendChild(document.createTextNode(simName));
        root.appendChild(name);

        Element myChoice = document.createElement("choice");
        myChoice.appendChild(document.createTextNode(Integer.toString(choice)));
        root.appendChild(myChoice);

        Element author = document.createElement("author");
        author.appendChild(document.createTextNode(authName));
        root.appendChild(author);

        Element islayout = document.createElement("islayout");
        islayout.appendChild(document.createTextNode("1"));
        root.appendChild(islayout);

        Element rows = document.createElement("rows");
        rows.appendChild(document.createTextNode(Integer.toString(myRows)));
        root.appendChild(rows);

        Element cols = document.createElement("cols");
        cols.appendChild(document.createTextNode(Integer.toString(myCols)));
        root.appendChild(cols);

        Element shape = document.createElement("shape");
        shape.appendChild(document.createTextNode(Integer.toString(myShape)));
        root.appendChild(shape);

        Element threshold = document.createElement("threshold");
        threshold.appendChild(document.createTextNode(Double.toString(myThreshold)));
        root.appendChild(threshold);

        Element prob = document.createElement("prob");
        prob.appendChild(document.createTextNode(Float.toString(myProb)));
        root.appendChild(prob);

        Element thelayout = document.createElement("layout");
        thelayout.appendChild(document.createTextNode(layout));
        root.appendChild(thelayout);
    }

    private void writeFile(){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath+simName+"_saved.xml"));
            transformer.transform(domSource, streamResult);
            System.out.println("Done creating XML File");
        }
        catch (TransformerException tfe) {
            throw new XMLException("Transformation fault");
        }
    }

    /***
     * Call both the above methods to, first initialize
     * the xml tree structure, and then write it out to a file
     * @throws ParserConfigurationException: exceptions
     */
    public void outputFile() throws ParserConfigurationException {
        addElements();
        writeFile();
    }
}
