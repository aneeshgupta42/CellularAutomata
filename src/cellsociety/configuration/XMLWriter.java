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

public class XMLWriter {

    public static final String xmlFilePath = "data/savedfiles/saved.xml";
    private String simName, authName, layout;
    private int choice, rows, cols, isLayout, shape;
    private float prob;
    private double threshold;
    Grid myGrid;
    Game myGame;

    public XMLWriter(Grid grid, Game game) throws Exception {
        myGrid = grid;
        myGame = game;
        simName = game.getSimulationName(); authName = game.getAuthor();
        choice = game.getMyChoice();
        rows = game.getMyRows(); cols = game.getMyCols();
        shape = game.getMyShape();
        isLayout = game.getIsLayout();
        try{
            prob = game.getMyProb();
        }
        catch(Exception e){
            prob = (float) 0.0;
            throw new Exception("Not a fire type simulation");
        }
        try{
            threshold = game.getMyThreshold();
        }
        catch(Exception e){
            threshold = 0.0;
            throw new Exception("Not a segregation or threshold type");
        }
        getLayout();
    }

    private void getLayout(){
        layout = "";
        for(int i = 0; i<myGrid.getMyHeight(); i++){
            for(int j = 0; j<myGrid.getMyWidth(); j++){
                layout += Integer.toString(myGrid.getCell(i,j).getState());
                layout += " ";
            }
            layout += "\n";
        }
    }

    public static void main(String argv[]) {
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("data");
            document.appendChild(root);
            // set an attribute to staff element
            Attr attr = document.createAttribute("media");
            attr.setValue("sim");
            root.setAttributeNode(attr);

            //you can also use staff.setAttribute("id", "1") for this

            // firstname element
            Element name = document.createElement("name");
            name.appendChild(document.createTextNode("Example Sim"));
            root.appendChild(name);

            // lastname element
            Element choice = document.createElement("choice");
            choice.appendChild(document.createTextNode("1"));
            root.appendChild(choice);

            // email element
            Element author = document.createElement("author");
            author.appendChild(document.createTextNode("Aneesh Gupta"));
            root.appendChild(author);

            // department elements
            Element isLayout = document.createElement("islayout");
            isLayout.appendChild(document.createTextNode("0"));
            root.appendChild(isLayout);

            Element rows = document.createElement("rows");
            rows.appendChild(document.createTextNode("15"));
            root.appendChild(rows);

            Element cols = document.createElement("cols");
            cols.appendChild(document.createTextNode("15"));
            root.appendChild(cols);

            Element threshold = document.createElement("threshold");
            threshold.appendChild(document.createTextNode("Thresh"));
            root.appendChild(threshold);

            Element prob = document.createElement("prob");
            prob.appendChild(document.createTextNode("Probability"));
            root.appendChild(prob);

            Element layout = document.createElement("layout");
            layout.appendChild(document.createTextNode("Layout"));
            root.appendChild(layout);


            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
