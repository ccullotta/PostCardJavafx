package org.cfcdoom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URISyntaxException;

public class Main extends Application {
    private static Stage stage;
    static File myFile = new File("data.obj");



    @Override
    public void start(Stage primaryStage) throws Exception{
//        myFile.createNewFile(); doesnt appear to do anything?
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("post-card calculator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public  static void  setScene(Parent scene) {
        stage.setScene(new Scene(scene));
    }


    public static void main(String[] args) {
        launch(args);
    }
}

