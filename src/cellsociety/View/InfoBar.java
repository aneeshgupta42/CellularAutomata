package cellsociety.View;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class InfoBar extends HBox {

    private Label cursor;
    private Label editingTool;
    public InfoBar() {
        this.cursor = new Label("Cursor: (0,0)");
        this.editingTool = new Label("Draw Mode: Drawing");

        this.getChildren().addAll(this.cursor, this.editingTool);
    }
}
