import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    private final int updateInterval = 150; // 100 milliseconds

    //Food variables
    int appleX;
    int appleY;
    String appleImage = "/image/apple.png";
    private Image foodImage;


    @Override
    public void start(Stage stage) throws Exception {
        
        Pane root = new Pane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.LIGHTGREEN);

        root.setOnKeyPressed(event -> {
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
        generateApple();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Snake!");
        stage.show();
    }

    public void update(GraphicsContext gc){
        move();
        drawBackground(gc);
        drawSnake(gc);
        placeAppleImage(gc);
        eatApple();
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

    //This function generates an apple at a random location on the canvas
    public void generateApple(){
        appleX = (int) (Math.random() * COLUMNS);
        appleY = (int) (Math.random() * ROWS);
        foodImage = new Image(appleImage);
    }

    /*
    A function that draws the apple. This is a separate function so that it can be called from the update method continuously without
    generating a new apple at a different location every frame.*/
    public void placeAppleImage(GraphicsContext gc){
        gc.drawImage(foodImage, appleX * BODY_SIZE, appleY * BODY_SIZE, BODY_SIZE, BODY_SIZE);
    }

    public void drawSnake(GraphicsContext gc){
        //For loop to draw the snake
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
    
    public void eatApple(){
        if(x[0] == appleX * BODY_SIZE && y[0] == appleY * BODY_SIZE){
            snakeBody++;
            generateApple();
        }
    }
    
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
