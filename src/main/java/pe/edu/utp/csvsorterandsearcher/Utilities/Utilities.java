package pe.edu.utp.csvsorterandsearcher.Utilities;

import javafx.scene.control.Alert;
import pe.edu.utp.csvsorterandsearcher.CSVSorterAndSearcher;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Utilities {
    /*
     TODO: POR ALGUN MOTIVO, EL logRecentFiles.log no lo toma de Resource,
      lo toma de target...csvsortedandsearcher/logRecentFiles.log
      no hay ningun inconveniente con que lo tome de ahi, es solo una observación
     */
    private static final String _FILELOG_ =
            Objects.requireNonNull(
                    CSVSorterAndSearcher.class
                            .getResource("logRecentFiles.log"))
                            .getFile()
                            .replaceAll("%20", " ");
            ;
    private static final int _CANTRECENTFILES_ = 5;

    public static ArrayList<String> readRecordRecentFiles(){
        try(BufferedReader fi = new BufferedReader(new FileReader(_FILELOG_))){
            ArrayList<String> data = new ArrayList<>();
            String line;
            while((line = fi.readLine()) != null){
                data.add(line);
            }
            return data;
        }catch (IOException e){
            return new ArrayList<>();
        }
    }

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

    public static void alert(String title, String header, String contextText, Alert.AlertType alertType){
        Alert alerta = new Alert(alertType);
        alerta.setTitle(title);
        alerta.setHeaderText(header);
        alerta.setContentText(contextText);
        alerta.showAndWait();
    }

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
