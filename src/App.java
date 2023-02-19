import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application{

    //Width and height of the window
    private final int WIDTH = 800;
    private final int HEIGHT = 800;

    @Override
    public void start(Stage stage) throws Exception {
        
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.LIGHTGREEN);
        
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Snake!");
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
