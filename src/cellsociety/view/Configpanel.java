package cellsociety.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Configpanel extends VBox {

    private MainView myMainView;
    private int numofRows;
    private int numofCols;
    private TextField inputRows;
    private TextField inputCols;
    private Button submit;

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
        this.getChildren().addAll(rowBox,colbox,submit);
    }

    public void submission() {

        this.submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if ((inputRows.getText() != null && !inputRows.getText().isEmpty())) {
                    numofRows = Integer.parseInt(inputRows.getText().trim());
                    System.out.println(numofRows);
                }
                if ((inputCols.getText() != null && !inputCols.getText().isEmpty())) {
                    numofCols = Integer.parseInt(inputCols.getText().trim());
                    System.out.println(numofCols);
                }
            }
        });
        submit.setAlignment(Pos.TOP_LEFT);
    }

//    public Configpanel getPanel() {
//        return this.
//    }
}
