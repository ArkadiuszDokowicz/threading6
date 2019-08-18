package threading6.com.ad.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {
    // A function to implement bubble sort


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        primaryStage.setTitle("MainView");
        primaryStage.setScene(new Scene(root, 1200, 650));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);


    }
}