import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class App extends Application{

    //Width and height of the window
    static final int WIDTH = 800;
    static final int HEIGHT = 800;
    static final int BODY_SIZE = 40;
    static final int ROWS = 20; 
    static final int COLUMNS = 20; 
    static final int MAX_UNITS = (WIDTH * HEIGHT) / BODY_SIZE; //Max size of the snake

    //Arrays of coordinates of the snake
    final int x[] = new int[MAX_UNITS];
    final int y[] = new int[MAX_UNITS];    

    //Direction of the snake, initial direction is right
    String direction = "R";

    //Initial snake size
    int snakeBody = 3;

    //Time variables
    private long lastUpdateTime = 0;
    private final int updateInterval = 100000000; // 100 milliseconds

    //Food variables
    int appleX;
    int appleY;
    String appleImage = "/image/mouse.png";
    private Image foodImage;

    private boolean gameOver = false;

    private int score = 0;

    @Override
    public void start(Stage stage) throws Exception {
        
        //Parent root = (Parent) FXMLLoader.load(getClass().getResource("Main.fxml"));

        Pane pane = new Pane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        pane.getChildren().add(canvas);
        Scene scene = new Scene(pane, WIDTH, HEIGHT, Color.LIGHTGREEN);

        x[0] = 360;
        y[0] = 360;

        //Create the animation timer which calls the update method on every frame
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdateTime >= updateInterval){ 
                    update(gc);
                    lastUpdateTime = now;
                }      
            }
        };
        gameLoop.start(); //Start the animation timer

        pane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                if(direction != "L"){
                    direction = "R";
                }
            } else if (event.getCode() == KeyCode.LEFT) {
                if(direction != "R"){
                    direction = "L";
                };
            } else if (event.getCode() == KeyCode.UP) {
                if(direction != "D"){
                    direction = "U";
                }
            } else if (event.getCode() == KeyCode.DOWN) {
                if(direction != "U"){
                    direction = "D";
                }
            //Restart the below variables and call the methods to restart the game
            } else if (event.getCode() == KeyCode.R) {
                gameOver = false;
                x[0] = 360;
                y[0] = 360;      
                snakeBody = 3;
                score = 0;
                drawSnake(gc); 
                gameLoop.start(); 
                generateMouse();
                placeMouseImage(gc);            
            }
        });
    
        // Set the focus to the pane to receive key events
        pane.requestFocus();

        generateMouse();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Snake!");
        stage.show();
    }

    public void update(GraphicsContext gc){
        gameOver();
        if(gameOver == false){ 
            move();
            drawBackground(gc);
            drawSnake(gc);
            placeMouseImage(gc);
            eatMouse();
            displayScore(gc);
        } else {
            gc.setFill(Color.WHEAT);
            gc.setFont(new Font("Digital-7", 60));
            gc.fillText("Game Over", 270, 300);
            gc.setFont(new Font("Digital-7", 40));
            gc.fillText("Press R to restart!", 270, 400);
        }
    }

    //A function that moves the snake
    private void move() {
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

    //This function generates a mouse at a random location on the canvas
    public void generateMouse(){
        appleX = (int) (Math.random() * COLUMNS);
        appleY = (int) (Math.random() * ROWS);
        foodImage = new Image(appleImage);
    }

    public void eatMouse(){
        if(x[0] == appleX * BODY_SIZE && y[0] == appleY * BODY_SIZE){
            snakeBody++;
            score ++;
            generateMouse();
        }
    }

    /*
    A function that draws the mouse. This is a separate function so that it can be called from the update method continuously without
    generating a new mouse at a different location every frame.*/
    public void placeMouseImage(GraphicsContext gc){
        gc.drawImage(foodImage, appleX * BODY_SIZE, appleY * BODY_SIZE, BODY_SIZE, BODY_SIZE);
    }

    public void drawSnake(GraphicsContext gc){
        gc.setFill(Color.web("669900"));
        //For loop to draw the snake
        for (int i = 0; i < snakeBody; i++) {
            if(i == 0){
                gc.fillOval(x[i], y[i], BODY_SIZE, BODY_SIZE);
            }
            else{
                gc.fillRoundRect(x[i], y[i], BODY_SIZE, BODY_SIZE, 20, 20);
            }
        }
    }

    public void drawBackground(GraphicsContext gc){
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.GREEN);
                } else {
                    gc.setFill(Color.DARKGREEN);
                }
                gc.fillRect(i * BODY_SIZE, j * BODY_SIZE, BODY_SIZE, BODY_SIZE);
            }
        }
    }
    
    public void displayScore(GraphicsContext gc){
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Digital-7", 35));
        gc.fillText("Score: " + score, 10, 35);
    }

    //Sets the conditions for the end of the game
    public void gameOver() {
        if (x[0] < 0 || y[0] < 0 || x[0] > 760 || y[0] > 760) {
            gameOver = true;
        }
        for(int i = 1; i < x.length; i++) {
            if(x[0] == x[i] && y[0] == y[i]) {
               gameOver = true;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
