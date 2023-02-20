import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.application.Application;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application{

    //Width and height of the window
    static final int WIDTH = 800;
    static final int HEIGHT = 800;
    static final int BODY_SIZE = 40; 
    static final int MAX_UNITS = (WIDTH * HEIGHT) / BODY_SIZE; //Max size of the snake

    //Arrays of coordinates of the snake
    final int x[] = new int[MAX_UNITS];
    final int y[] = new int[MAX_UNITS];

    //Initial snake size
    int snakeBody = 3;

    //Time variables
    private long lastUpdateTime = 0;
    private final int updateInterval = 100; // 100 milliseconds


    @Override
    public void start(Stage stage) throws Exception {
        
        Pane root = new Pane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawGrid(gc);

        root.getChildren().add(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.LIGHTGREEN);

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                move("R");
            } else if (event.getCode() == KeyCode.LEFT) {
                move("L");
            } else if (event.getCode() == KeyCode.UP) {
                move("U");
            } else if (event.getCode() == KeyCode.DOWN) {
                move("D");
            }
        });
        // Set the focus to the pane to receive key events
        root.requestFocus();

        //Create the animation timer which calls the update method on every frame
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {                              // 1_000_000 nanoseconds
                if (now - lastUpdateTime >= updateInterval * 1_000_000){ 
                    update(gc);
                    lastUpdateTime = now;
                } 
            }
        };
        gameLoop.start(); //Start the animation timer

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Snake!");
        stage.show();
    }

    public void update(GraphicsContext gc){
        drawSnake(gc);
        move("R");
    }

    public void drawSnake(GraphicsContext gc){
        for (int i = 0; i < snakeBody; i++) {
            if(i == 0){
                gc.setFill(Color.BROWN);
                gc.fillRect(x[i], y[i], BODY_SIZE, BODY_SIZE);
            }
            else{
                gc.setFill(Color.DARKKHAKI);
                gc.fillRect(x[i], y[i], BODY_SIZE, BODY_SIZE);
            }
        }
    }
        
    //Draw a grid over the scene, and set the color to black
    public void drawGrid(GraphicsContext gc){
        for(int i = 0; i < HEIGHT / BODY_SIZE; i++){
            gc.strokeLine(i * BODY_SIZE, 0, i * BODY_SIZE, HEIGHT);
            gc.strokeLine(0, i*BODY_SIZE, WIDTH, i*BODY_SIZE);
            gc.setStroke(Color.BLACK);
        }   
    }

    //A function that moves the snake
    private void move(String direction) {
        for (int i = snakeBody; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch(direction) {
            case "R":
                x[0] = x[0] + BODY_SIZE;
                break;
            case "L":
                x[0] = x[0] - BODY_SIZE;
                break;
            case "U":
                y[0] = y[0] - BODY_SIZE;
                break;
            case "D":
                y[0] = y[0] + BODY_SIZE;
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
