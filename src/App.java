import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.stage.Stage;

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

    private int dx = 1; //X direction of the snake
    private int dy = 0; //Y direction of the snake


    @Override
    public void start(Stage stage) throws Exception {
        
        Pane root = new Pane();
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
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start(); //Start the animation timer

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Snake!");
        stage.show();
    }

    public void update(){
        
}

    //A function that moves the snake
    private void move(String direction) {
        switch(direction) {
            case "R":
                dx = 1;
                dy = 0;
                break;
            case "L":
                dx = -1;
                dy = 0;
                break;
            case "U":
                dy = -1;
                dx = 0;
                break;
            case "D":
                dy = 1;
                dy = 0;
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
