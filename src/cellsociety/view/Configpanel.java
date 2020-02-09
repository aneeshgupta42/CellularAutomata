package cellsociety.view;

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
    private static String cursorPosFormat = "Cursor (%d, %d)";
    private XYChart.Series<Number, Number> myseries;

    public Configpanel(MainView mainView) {

        myMainView = mainView;

        Label Rows = new Label("Rows:");
        this.inputRows= new TextField();
        HBox rowBox = new HBox();
        inputRows.setPromptText("Enter # of rows");
        inputRows.setPrefWidth(50);
        rowBox.getChildren().addAll(Rows, inputRows);
        rowBox.setSpacing(10);

        Label Cols = new Label("Columns:");
        this.inputCols= new TextField();
        HBox colbox = new HBox();
        inputCols.setPromptText("Enter # of Columns");
        inputCols.setPrefWidth(50);
        colbox.getChildren().addAll(Cols, inputCols);
        colbox.setSpacing(10);


        submission();
        makeNewGraph();
        this.getChildren().addAll(rowBox,colbox,submit, myLinechart);
    }

    public void submission() {
        toolBar = new Toolbar(myMainView);
        this.submit = new Button("Submit");
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


    public int getNewRows() {
        return numofRows;
    }

    public int getNewCols() {
        return numofCols;
    }

    public boolean getsubmitstatus() {
        return submitbuttonstatus;
    }

    public void makeNewGraph() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time/s");
        xAxis.setAnimated(false);
        yAxis.setLabel("Value");
        yAxis.setAnimated(false);

        //creating the line chart with two axis created above
        this.myLinechart = new LineChart<Number, Number>(xAxis, yAxis);
        myLinechart.setTitle("Cell Populations");
        myLinechart.setAnimated(false); // disable animations

        myseries= new XYChart.Series<>();
        myseries.setName("Data Series");

        // add series to chart
    }

    public void addDataToGraph(int timernumber) {
        myseries.getData().add(new XYChart.Data<>(timernumber,1));
        myLinechart.getData().add(myseries);

    }
}
