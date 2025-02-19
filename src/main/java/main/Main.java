package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Cow using FXML.
 */
public class Main extends Application {
    private final Cow cow = new Cow("data/cow.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            stage.setTitle("Cow");
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setCow(cow); // inject the Cow instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
