package cellsociety;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

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

    public MainView() {

        this.switchSimulation = new ComboBox();
       this.switchSimulation.getItems().addAll("Game of life", "Percolation", "Segregation", "Predator-Prey",
               "fire");

        this.canvas = new Canvas(400,400);
        this.affine = new Affine();
        this.affine.appendScale(400/10f,400/10f);

        timer();
        Toolbar Toolbar = new Toolbar(this);
        this.getChildren().addAll(Toolbar, this.canvas, this.switchSimulation, this.lblTime);

    }

    public void draw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0,0,450,450);

        g.setFill(Color.BLACK);
        for (int x = 0; x <  400; x++) {
            for (int y = 0; y < 400; y++) {
                g.fillRect(x, y, 1, 1);
            }
        }

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05f);
        for (int x = 0; x < 400; x++) {
            g.strokeLine(x,0,x,10);
        }
        for (int y = 0; y < 400; y++) {
            g.strokeLine(0,y,10,y);
        }
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
}
