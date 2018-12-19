package com.soft1841.oop.week2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ButtonApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = getClass().getResource("/fxml/button.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root,543,824);
        scene.getStylesheets().add(ButtonApp.class.getResource("/css/style.css").toExternalForm());
        primaryStage.setTitle("知乎");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

