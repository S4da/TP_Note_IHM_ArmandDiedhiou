package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("TP4");
        primaryStage.setScene(new Scene(root, 695, 431));
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
    /**
     * 
     * 
     * Pour réaliser l'architecture MVC de cette application, j'ai créé une classe
     * model, une classe controller et une vue avec sceneBuilder. J'ai structuré l'application en faisant toutes
     * qui permettent de changer la vue dans le controller, et j'ai changé les valeurs qui permettent de modifier la vue 
     * dans le modele.
     * 
     * 
     * 
     */
}