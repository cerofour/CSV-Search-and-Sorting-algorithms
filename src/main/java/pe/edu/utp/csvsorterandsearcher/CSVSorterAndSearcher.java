package pe.edu.utp.csvsorterandsearcher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CSVSorterAndSearcher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CSVSorterAndSearcher.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 940, 570);
        stage.setTitle("CSVSorterAndSearcher");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}