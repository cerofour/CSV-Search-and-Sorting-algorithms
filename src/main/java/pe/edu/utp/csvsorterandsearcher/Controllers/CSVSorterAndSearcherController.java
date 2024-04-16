package pe.edu.utp.csvsorterandsearcher.Controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pe.edu.utp.csvsorterandsearcher.CSVSorterAndSearcher;
import pe.edu.utp.csvsorterandsearcher.Utilities.ExportModes;
import pe.edu.utp.csvsorterandsearcher.Utilities.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CSVSorterAndSearcherController {

    @FXML
    private Label labelTime;
    // este se debe modificar labelTime.setText() con el tiempo
    // de ejecucion de cada algoritmo

    @FXML
    private Label labelTextSortingMode;

    @FXML
    private Label labelTextSortingMethod;

    @FXML
    private Label labelTextSearchMethod;

    @FXML
    private TableView<String[]> tableView;

    @FXML
    private CheckMenuItem checkMenuItemOptHeaders;

    @FXML
    private Menu menuData, menuSort, menuSearch, menuView;

    @FXML
    private Menu menuRecentFiles;

    @FXML
    private MenuItem menuItemSortASC;

    @FXML
    private MenuItem menuItemSortDESC;

    @FXML
    private Menu menuSortOrderBy;

    @FXML
    private Menu menuAlgorithm;

    @FXML
    private MenuItem menuItemExport;

    @FXML
    private MenuItem menuItemExportAs;

    @FXML
    private Stage stageExecutionTimeLog = new Stage();

    @FXML
    private Label labelTextDataView;


    public static boolean sort_orderBy = true;
    public static boolean rawDataView = true;
    private ExecutionTimeLogController ExecutionTimeLogC;
    private FileChooser fileChooserOpen;
    private FileChooser fileChooserExport;
    private FileChooser fileChooserExportAs;

    public void initialize(){
        createSubWindows();
        updateRecentFiles();
        loadDifferentFileChooser();
        disableExportButtons(true);
        disableAllMenuButtons(true);
        //
        menuItemSortASC.setOnAction(event -> {sort_selectOrderBy(true);});
        menuItemSortDESC.setOnAction(event -> {sort_selectOrderBy(false);});
        menuSortOrderBy.getItems().set(0, menuItemSortASC);
        menuSortOrderBy.getItems().set(1, menuItemSortDESC);
        //
        addAlgorithmMethodMenu();
        //


    }

    @FXML
    protected void openFile(){
        File file = fileChooserOpen.showOpenDialog(null);
        if (file == null){
            return;
        }
        Utilities.writeRecordRecentFiles(file.getPath());
        updateRecentFiles();
        disableExportButtons(false); // esta linea deberia ponerse cuando se detectó un ordenamiento o busqueda
        disableAllMenuButtons(false);
    }

    private void openRecentFile(String pathRecentFile){
        File file = new File(pathRecentFile);
        if (!file.exists()){
            Utilities.alert(
                    "Error",
                    "An Error ocurred",
                    "file not found: "+pathRecentFile,
                    Alert.AlertType.ERROR
            );
            Utilities.deleteRecentFilesRecord(pathRecentFile);
            updateRecentFiles();
            return;
        }
        disableExportButtons(false); // esta linea deberia ponerse cuando se detectó un ordenamiento o busqueda
        // falta mas codigo
        disableAllMenuButtons(false);
    }

    private void disableExportButtons(boolean disableExportButtons){
        menuItemExport.setDisable(disableExportButtons);
        menuItemExportAs.setDisable(disableExportButtons);
    }


    private void updateRecentFiles(){
        ArrayList<String> recentFiles = Utilities.readRecordRecentFiles();
        if (menuRecentFiles != null){
            menuRecentFiles.getItems().clear();
        }
        for (String pathRecentFile : recentFiles.toArray(new String[0])){
            String[] arraySplit = pathRecentFile.split("[/\\\\\\\\]");
            String nameFile = arraySplit[arraySplit.length - 1];
            MenuItem menuItemRecentFile = new MenuItem(nameFile);
            menuItemRecentFile.setOnAction(event -> {
                openRecentFile(pathRecentFile);
            });
            menuRecentFiles.getItems().add(menuItemRecentFile);
        }
    }

    private void createSubWindows(){
        FXMLLoader loaderExecutionTimeLog = new FXMLLoader(CSVSorterAndSearcher.class.getResource("ExecutionTimeLog.fxml"));
        try{
            // crea la subventana ExecutionTimeLog
            Scene sceneExecutionTimeLog = new Scene(loaderExecutionTimeLog.load(), 445, 460);
            stageExecutionTimeLog.setTitle("Execution Time Log");
            stageExecutionTimeLog.setScene(sceneExecutionTimeLog);
            stageExecutionTimeLog.setResizable(false);
            stageExecutionTimeLog.setOnCloseRequest(windowEvent -> {
                stageExecutionTimeLog.hide();
                windowEvent.consume();}
            );
            ExecutionTimeLogC = loaderExecutionTimeLog.getController();

        } catch (IOException e){
            Utilities.alert(
                    "Error",
                    "An error ocurred",
                    "Error loading sub windows",
                    Alert.AlertType.ERROR
            );
            Platform.exit();
        }

    }

    private void addAlgorithmMethodMenu(){
        MenuItem QuickSort = new MenuItem("QuickSort");
        QuickSort.setOnAction(actionEvent -> {selectSortingMethod("QuickSort");});
        menuAlgorithm.getItems().add(QuickSort);
    }

    private void loadDifferentFileChooser(){
        // fileChooserOpen
        fileChooserOpen = new FileChooser();
        fileChooserOpen.setTitle("Find File");
        fileChooserOpen.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
        // fileChooserExport
        fileChooserExportAs = new FileChooser();
        fileChooserExportAs.setTitle("Export As");
        fileChooserExportAs.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"),
                new FileChooser.ExtensionFilter("HTML", "*.html"),
                new FileChooser.ExtensionFilter("XML", "*.xml"),
                new FileChooser.ExtensionFilter("JSON", "*.json")

        );
        // fileChooserExport
        fileChooserExport = new FileChooser();
        fileChooserExport.setTitle("Export");
        fileChooserExport.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );

    }

    private void disableAllMenuButtons(boolean disableAllButtons){
        menuData.setDisable(disableAllButtons);
        menuSort.setDisable(disableAllButtons);
        menuSearch.setDisable(disableAllButtons);
        menuView.setDisable(disableAllButtons);
    }

    private void resetSettings(){
        // reset tableView
        tableView.getItems().clear();
        tableView.getColumns().clear();
        // reset Sort settings
        sort_selectOrderBy(true);

        // reset View
        rawDataView = true;
        labelTextDataView.setText("Raw data");
        ExecutionTimeLogC.clear();

        /*
        Por el momento solo se restablece:
        - TableView
        - OrderBy (Sort) (Falta completar mas)
        - View
        NOTA: Falta agregar el restablecimiento de los
        futuros metodos que se agreguen a la aplicacion
         */

    }

    @FXML
    protected void clearTableView(){
        disableExportButtons(true);
        disableAllMenuButtons(true);
        resetSettings();
    }

    @FXML
    protected void generateCXHeaders(){
        if(checkMenuItemOptHeaders.isSelected()){
            //aqui va el codigo para generar las cabeceras
            //solo si el archivo no contiene las cabeceras
            //Modelo de cabeceras C0, C1, C2 ... CN
        }else{
            //aqui va el codigo para regresar las cabeceras
            //a las que trae el archivo por defecto
        }

    }

    @FXML
    protected void quit(){
        Platform.exit();
    }

    @FXML
    protected void showRawData(){
        rawDataView = true;
        labelTextDataView.setText("Raw Data");
    }

    @FXML
    protected void showProcessedData(){
        rawDataView = false;
        labelTextDataView.setText("Processed Data");
    }

    @FXML
    protected void sort_selectOrderBy(boolean optOrderBy){
        sort_orderBy = optOrderBy;
        labelTextSortingMode.setText(optOrderBy ? "ASC" : "DESC");
        /*
        if (optSortingMode){
            labelTextSortingMode.setText("ASC");
        }else {
            labelTextSortingMode.setText("DESC");
        }

         */

    }

    @FXML
    private void export(){
        File file = fileChooserExport.showSaveDialog(null);
        ExportModes.ExportAsCSV();
    }

    @FXML
    protected void exportAs(){
        File file = fileChooserExportAs.showSaveDialog(null);
        if (file == null){
            return;
        }
        String extension = file.getName().substring(file.getName().lastIndexOf('.')+1).toUpperCase();
        switch (extension){
            case "CSV":
                break;
            case "TXT":
                break;
            case "HTML":
                //ExportModes.ExportAsHTML(new String[]{"gol", "ga", "s"}, a, file.getPath());
                break;
        }

    }

    @FXML
    protected void selectSortingMethod(String Algorithm){
        labelTextSortingMethod.setText(Algorithm);
    }

    @FXML
    protected void selectSearchMethod(String SearchMetod){
        labelTextSearchMethod.setText(SearchMetod);
    }

    @FXML
    protected void executeSorting(){
        // ejecuta el ordenamiento
    }

    @FXML
    protected void runExecutionTimeLog(){
        if (!stageExecutionTimeLog.isShowing()){
            stageExecutionTimeLog.show();
        }else{
            stageExecutionTimeLog.hide();
            stageExecutionTimeLog.show();
        }
    }


}