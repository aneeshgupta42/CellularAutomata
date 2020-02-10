package cellsociety.view;

import cellsociety.model.Grid;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

public class Configpanel extends VBox {

    private MainView myMainView;
    private int numofRows;
    private int numofCols;
    private TextField inputRows;
    private TextField inputCols;
    private Button submit;
    private boolean submitbuttonstatus = false;
    private Toolbar toolBar;
    private LineChart<Number,Number> myLinechart;
    private XYChart.Series<Number, Number> myseries;
    private Grid currentGrid;
    private ResourceBundle configBundle;
    private static final int WIDTH = 50;
    private static final int SPACING = 10;

    /**
     * The Configuration Panel Displayed in Mainview and some inital settings
     * @param mainView the borderpane that this will be put in
     */
    public Configpanel(MainView mainView) {

        myMainView = mainView;
        currentGrid = myMainView.getDisplayGrid();
        configBundle = ResourceBundle.getBundle("cellsociety/resources/Configtext");

        Label Rows = new Label(configBundle.getString("Rowbutton"));
        this.inputRows= new TextField();
        HBox rowBox = new HBox();
        inputRows.setPromptText(configBundle.getString("Entertextrow"));
        inputRows.setPrefWidth(WIDTH);
        rowBox.getChildren().addAll(Rows, inputRows);
        rowBox.setSpacing(SPACING);

        Label Cols = new Label(configBundle.getString("Columnbutton"));
        this.inputCols= new TextField();
        HBox colbox = new HBox();
        inputCols.setPromptText(configBundle.getString("Entertextcol"));
        inputCols.setPrefWidth(WIDTH);
        colbox.getChildren().addAll(Cols, inputCols);
        colbox.setSpacing(SPACING);


        submission();
        makeNewGraph();
        this.getChildren().addAll(rowBox,colbox,submit, myLinechart);
    }

    /**
     * Configures the submit button to read the numbers input in all the buttons in the Config Panel
     */
    public void submission() {
        toolBar = new Toolbar(myMainView);
        this.submit = new Button(configBundle.getString("Submitbutton"));
        submit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                submitbuttonstatus = true;
                if ((inputRows.getText() != null && !inputRows.getText().isEmpty())) {
                    numofRows = Integer.parseInt(inputRows.getText().trim());
                    System.out.println(numofRows);
                }
                if ((inputCols.getText() != null && !inputCols.getText().isEmpty())) {
                    numofCols = Integer.parseInt(inputCols.getText().trim());
                    System.out.println(numofCols);
                }
                toolBar.choosingNewSim(toolBar.getMyChoice());
                System.out.println(submitbuttonstatus);
            }
        });
        submit.setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Get Rows
     * @return int representing number of rows
     */
    public int getNewRows() {
        return numofRows;
    }

    /**
     * Get Columns
     * @return int representing number of columns
     */
    public int getNewCols() {
        return numofCols;
    }

    /**
     * Get the submission status
     * @return boolean representing if the submit button worked
     */
    public boolean getsubmitstatus() {
        return submitbuttonstatus;
    }

    /**
     * Makes the graph being displayed in the OCnfiguration Panel
     */
    public void makeNewGraph() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(configBundle.getString("Time"));
        xAxis.setAnimated(false);
        yAxis.setLabel(configBundle.getString("Yaxis"));
        yAxis.setAnimated(false);

        this.myLinechart = new LineChart<Number, Number>(xAxis, yAxis);
        myLinechart.setTitle(configBundle.getString("GraphTitle"));
        myLinechart.setAnimated(false); // disable animations

        myseries= new XYChart.Series<>();
        myseries.setName(configBundle.getString("Alive"));
        myLinechart.getData().add(myseries);
    }

    /**
     * Adds data to graph
     * @param timernumber int representing time elapsed for the x-axis
     */
    public void addDataToGraph(int timernumber) {
        myseries.getData().add(new XYChart.Data<>(timernumber,1));
    }
}
