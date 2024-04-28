package pe.edu.utp.csvsorterandsearcher.Utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import pe.edu.utp.csvsorterandsearcher.CSVSorterAndSearcher;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class provides a number of useful methods for manipulating
 * files and presenting alerts in the user interface.
 */
public class Utilities {


    private static final String _FILELOG_ =
            Objects.requireNonNull(
                    CSVSorterAndSearcher.class
                            .getResource("logRecentFiles.log"))
                            .getFile()
                            .replaceAll("%20", " ");
            ;
    private static final int _CANTRECENTFILES_ = 5;

    /**
     * Reads recent file records from the log file.
     * @return a list of strings representing the names of recent files
     */
    public static ArrayList<String> readRecordRecentFiles(){
        ArrayList<String> data;
        try(BufferedReader fi = new BufferedReader(new FileReader(_FILELOG_))){
            data = new ArrayList<>();
            String line;
            while((line = fi.readLine()) != null){
                data.add(line);
            }
        }catch (IOException e){
            return new ArrayList<>();
        }
        return data;
    }

    /**
     * Writes a new recent file record to the log file.
     * @param path path of the recent file to log
     */
    public static void writeRecordRecentFiles(String path){
        File filelog = new File(_FILELOG_);
        ArrayList<String> content = Utilities.readRecordRecentFiles();
        if (content.size() > _CANTRECENTFILES_) {
            content.remove(content.size()-1);
        }

        try(FileWriter fileW = new FileWriter(filelog)){
            fileW.write(path+"\n");
            content.removeIf(path_i -> path_i.equals(path));
            fileW.write(String.join("\n", content.toArray(new String[0])));
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Deletes a recent file record from the log file.
     * @param path path of the recent file to delete from the log
     */
    public static void deleteRecentFilesRecord(String path){
        File filelog = new File(_FILELOG_);
        ArrayList<String> content = Utilities.readRecordRecentFiles();
        content.removeIf(path_i -> path_i.equals(path));
        try(FileWriter fileW = new FileWriter(filelog)){
            fileW.write(String.join("\n", content.toArray(new String[0])));
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     *  Displays an alert in the user interface.
     * @param title      title of the alert
     * @param header     header of the alert
     * @param contextText body text of the alert
     * @param alertType  type of the alert
     */
    public static void alert(String title, String header, String contextText, Alert.AlertType alertType){
        Alert alerta = new Alert(alertType);
        alerta.getDialogPane().getStylesheets()
                .add(Objects.requireNonNull(CSVSorterAndSearcher.class.getResource("DarkTheme.css"))
                        .toExternalForm());
        alerta.setTitle(title);
        alerta.setHeaderText(header);
        alerta.setContentText(contextText);
        alerta.showAndWait();
    }

    /**
     * Saves the content to a file at the specified location.
     * @param content content to save
     * @param path path of the file where the content will be saved
     */
    public static void saveData(StringBuilder content, String path){
        try(FileWriter fileW = new FileWriter(path)){
            fileW.write(content.toString());
        }catch (IOException e){
            Utilities.alert(
                    "Error",
                    "An error ocurred",
                    "Could not save data, error:"+e.getMessage(),
                    Alert.AlertType.ERROR
            );
            return;
        }
        Utilities.alert(
                "Information",
                "Information about the saved file",
                "the file was exported successfully",
                Alert.AlertType.CONFIRMATION);
    }

}
