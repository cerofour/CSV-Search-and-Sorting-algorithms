package pe.edu.utp.csvsorterandsearcher.Controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pe.edu.utp.csvsorterandsearcher.CSV.CSVHeader;
import pe.edu.utp.csvsorterandsearcher.CSV.CSVIntermediateRepresentation;
import pe.edu.utp.csvsorterandsearcher.CSV.CSVReader;
import pe.edu.utp.csvsorterandsearcher.CSVSorterAndSearcher;
import pe.edu.utp.csvsorterandsearcher.Utilities.ExportModes;
import pe.edu.utp.csvsorterandsearcher.Utilities.TableV;
import pe.edu.utp.csvsorterandsearcher.Utilities.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CSVSorterAndSearcherController {

    @FXML
    private Label labelTime;

    @FXML
    private Label labelTextSortBy;

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
    private Menu menuSortBy;

    @FXML
    private Menu menuAlgorithm;

    @FXML
    private MenuItem menuItemExport;

    @FXML
    private MenuItem menuItemExportAs;

    private Stage stageExecutionTimeLog = new Stage();

    @FXML
    private Label labelTextDataView;


    private File file;
    private CSVIntermediateRepresentation ir;
    public static boolean sort_orderBy = true;
    public static boolean rawDataView = true;
    public static int columnIndexToSelectedInSort = -1;
    public static int columnIndexToSelectedInSearch = -1;
    public TableV tableV;
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
        tableV = new TableV(tableView);


    }

    @FXML
    protected void openFile(){
        file = fileChooserOpen.showOpenDialog(null);
        if (file == null){
            return;
        }
        resetSettings();
        Utilities.writeRecordRecentFiles(file.getPath());
        updateRecentFiles();
        startCSVReader(true);
        disableExportButtons(false); // esta linea deberia ponerse cuando se detectó un ordenamiento o busqueda
        disableAllMenuButtons(false);
    }

    private void openRecentFile(String pathRecentFile){
        file = new File(pathRecentFile);
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
        resetSettings();
        startCSVReader(true);
        disableExportButtons(false); // esta linea deberia ponerse cuando se detectó un ordenamiento o busqueda
        // falta mas codigo
        disableAllMenuButtons(false);
    }

    private void startCSVReader(boolean useFileHeaders){
        CSVReader reader = new CSVReader(file);
        if(!useFileHeaders){
            reader.useAutoGeneratedHeaders();
        }
        ir = reader.readWholeFile();
        tableV.clear(tableView);
        tableV.setHeaders(tableView, ir.getHeaders());
        tableV.setItems(ir.getColumns());
        setColumnsToChoose();
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

    private void setColumnsToChoose(){
        /*
        - This method adds the columns to select in the Sort by menu
         */
        if(menuSortBy.getItems() != null){
            menuSortBy.getItems().clear();
        }
        CSVHeader[] columns = ir.getHeaders();
        for (int i = 0; i < columns.length; i++) {
            MenuItem column = new MenuItem(columns[i].name);
            int finalI = i;
            column.setOnAction(event -> sort_selectSortBy(finalI));
            menuSortBy.getItems().add(column);
        }
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
        menuSortBy.getItems().clear();
        columnIndexToSelectedInSort = -1;
        labelTextSortBy.setText("None");

        // reset View
        rawDataView = true;
        labelTextDataView.setText("Raw data");
        ExecutionTimeLogC.clear();

        // reset generateCXHeaders
        checkMenuItemOptHeaders.setSelected(false);
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
        startCSVReader(!checkMenuItemOptHeaders.isSelected());
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
    }

    private void sort_selectSortBy(int index){
        columnIndexToSelectedInSort = index;
        labelTextSortBy.setText(ir.getHeaders()[index].name);
    }

    @FXML
    private void export(){
        /*
        - ir.getColumns() needs to be changed for the data processed and not read directly
          from the file, this will be changed later, for the moment ir.getColumns is used for
          program testing
         */
        File fileExport = fileChooserExport.showSaveDialog(null);
        if (fileExport == null){
            return;
        }
        ExportModes.ExportAsCSV(ir.getHeaders(), ir.getColumns(), fileExport.getPath());
    }

    @FXML
    protected void exportAs(){
        /*
        - ir.getColumns() needs to be changed for the data processed and not read directly
          from the file, this will be changed later, for the moment ir.getColumns is used for
          program testing
         */
        File fileExport = fileChooserExportAs.showSaveDialog(null);
        if (fileExport == null){
            return;
        }
        String extension = fileExport.getName()
                .substring(fileExport.getName().lastIndexOf('.')+1)
                .toUpperCase();
        switch (extension){
            case "CSV":
                ExportModes.ExportAsCSV(ir.getHeaders(), ir.getColumns(), fileExport.getPath());
                break;
            case "TXT":
                break;
            case "JSON":
                ExportModes.ExportAsJSON(ir.getHeaders(), ir.getColumns(), fileExport.getPath());
                break;
            case "XML":
                ExportModes.ExportAsXML(ir.getHeaders(), ir.getColumns(), fileExport.getPath());
                break;
            case "HTML":
                ExportModes.ExportAsHTML(ir.getHeaders(), ir.getColumns(), fileExport.getPath());
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
        System.out.println();
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