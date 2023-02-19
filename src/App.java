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
    private final int WIDTH = 800;
    private final int HEIGHT = 800;

    List<Rectangle> snakeBody = new ArrayList<>(); //A list of rectangles to represent the snake's body
    Rectangle snakeHead; //snake's head

    private int dx = 1; //X direction of the snake
    private int dy = 0; //Y direction of the snake


    @Override
    public void start(Stage stage) throws Exception {
        
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.LIGHTGREEN);

        //Create the rectangles that represent the snake
        for (int i = 0; i < 1; i++) {
            Rectangle rectangle = new Rectangle(40, 40);
            rectangle.setFill(Color.DARKKHAKI);
            snakeBody.add(rectangle);
            root.getChildren().add(rectangle);
        }
        //Create the snake's head
        snakeHead = snakeBody.get(0);

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                moveRight();
            } else if (event.getCode() == KeyCode.LEFT) {
                moveLeft();
            } else if (event.getCode() == KeyCode.UP) {
                moveUp();
            } else if (event.getCode() == KeyCode.DOWN) {
                moveDown();
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
        snakeHead.setX(snakeHead.getX() + dx);
        snakeHead.setY(snakeHead.getY() + dy);
}



    private void moveRight() {
        dx = 1;
        dy = 0;
}

    private void moveLeft() {
        dx = -1;
        dy = 0;
}

    private void moveUp() {
        dy = -1;
        dx = 0;
}

    private void moveDown() {
        dy = 1;
        dx = 0;
}

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
