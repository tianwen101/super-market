package oop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PV extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("VIP Login");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/VIP.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        primaryStage.setMaximized(true);
        primaryStage.getIcons().add(new Image("/img/2.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}