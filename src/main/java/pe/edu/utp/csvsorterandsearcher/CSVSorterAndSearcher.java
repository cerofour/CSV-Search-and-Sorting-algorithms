package pe.edu.utp.csvsorterandsearcher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Main class for the CSVSorterAndSearcher application.
 */
public class CSVSorterAndSearcher extends Application {

    /**
     * Starts the application by loading the main window.
     * @param stage The primary stage for the application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CSVSorterAndSearcher.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 640);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkTheme.css")).toExternalForm());
        stage.setTitle("CSVSorterAndSearcher");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> Platform.exit());
        stage.show();
    }

    /**
     * Main method to launch the application.
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        launch();
    }
}