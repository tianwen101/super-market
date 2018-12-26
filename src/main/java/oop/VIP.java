package oop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VIP extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/fxml/VIP.fxml"));
//		Parent root = FXMLLoader.load(getClass().getResource("FXMLExample_JS.fxml"));
//		Parent root = FXMLLoader.load(getClass().getResource("FXMLExample_JS2.fxml"));
        Scene scene = new Scene(root, 300, 275);

        stage.setTitle("VIP Welcome");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

