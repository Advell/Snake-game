import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.stage.Stage;

public class App extends Application{

    //Width and height of the window
    private final int WIDTH = 800;
    private final int HEIGHT = 800;

    List<Rectangle> snakeBody = new ArrayList<>(); //A list of rectangles to represent the snake's body 

    @Override
    public void start(Stage stage) throws Exception {
        
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.LIGHTGREEN);

        //Create the rectangles that represent the snake
        for (int i = 0; i < 3; i++) {
            Rectangle rectangle = new Rectangle(40, 40);
            rectangle.setFill(Color.DARKKHAKI);
            snakeBody.add(rectangle);
            root.getChildren().add(rectangle);
        }
        //Create the snake's head
        Rectangle snakeHead = snakeBody.get(0);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Snake!");
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
