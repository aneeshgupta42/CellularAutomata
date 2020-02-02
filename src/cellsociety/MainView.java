package cellsociety;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainView extends VBox {

    private Button playbutton;
    private Button btnStart;
    private Button btnStop;
    private ComboBox switchSimulation;
    private Canvas canvas;
    private Affine affine;
    private AnimationTimer timer;
    private Label lblTime;
    private int seconds;
    private InfoBar infobar;
    private Grid gridmap;

    public MainView() {

        this.canvas = new Canvas(500,500);
        this.gridmap = new Grid();
        gridmap.populateGridCells(500,500,0);

        timer();
        Toolbar Toolbar = new Toolbar(this);
        this.infobar = new InfoBar();
        this.getChildren().addAll(Toolbar, this.canvas, this.lblTime);

    }

    public void draw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0,0,400,400);

        g.setFill(Color.BLACK);
        for (int x = 0; x <  500; x++) {
            for (int y = 0; y < 500; y++) {
                g.fillRect(x, y, 1, 1);
            }
        }

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05f);
    }

    public void loadGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(3);
        gridPane.setHgap(3);
        this.getChildren().add(gridPane);


    }

    public void timer() {
        this.lblTime = new Label("0 s");
        this.timer = new AnimationTimer() {

            private long lastTime = 0;
            @Override
            public void handle(long now) {
                if (lastTime != 0) {
                    if (now > lastTime + 1_000_000_000) {
                        seconds++;
                        lblTime.setText(Integer.toString(seconds) + " s");
                        lastTime = now;
                    }
                } else {
                    lastTime = now;
                }
            }
            @Override
            public void stop() {
                super.stop();
                lastTime = 0;
                seconds = 0;
            }
        };
    }

    public void actionButtons() {
//        this.playbutton = new Button("play");
//        playbutton.setOnAction(e ->
//        {
//            lblTime.setText("0 .s");
//            timer.start();
//        });
//        this.btnStop = new Button("STOP");
//        btnStop.setOnAction(e -> timer.stop());

    }

    public AnimationTimer getTimer(){
        return this.timer;
    }

    public Label getLbltime() {
        return this.lblTime;
    }


//    public Simulation getSimulation() {
//        return this.simulation;
//    }
}
