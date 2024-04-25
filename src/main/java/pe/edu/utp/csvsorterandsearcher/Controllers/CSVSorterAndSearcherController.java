package pe.edu.utp.csvsorterandsearcher.Controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pe.edu.utp.csvsorterandsearcher.Algorithms.OrderedIndexGenerator;
import pe.edu.utp.csvsorterandsearcher.Algorithms.Search.BinarySearch;
import pe.edu.utp.csvsorterandsearcher.Algorithms.Search.InterpolationSearcher;
import pe.edu.utp.csvsorterandsearcher.Algorithms.Search.SearchAlgorithm;
import pe.edu.utp.csvsorterandsearcher.Algorithms.Search.SequentialSearch;
import pe.edu.utp.csvsorterandsearcher.Algorithms.Sort.BubbleSort;
import pe.edu.utp.csvsorterandsearcher.Algorithms.Sort.OptimizedBubbleSort;
import pe.edu.utp.csvsorterandsearcher.Algorithms.Sort.QuickSort;
import pe.edu.utp.csvsorterandsearcher.Algorithms.Sort.SortingAlgorithm;
import pe.edu.utp.csvsorterandsearcher.CSV.*;
import pe.edu.utp.csvsorterandsearcher.CSVSorterAndSearcher;
import pe.edu.utp.csvsorterandsearcher.Utilities.ExportModes;
import pe.edu.utp.csvsorterandsearcher.Utilities.TableV;
import pe.edu.utp.csvsorterandsearcher.Utilities.Utilities;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Controller class for the main application window of CSVSorterAndSearcher.
 */
public class CSVSorterAndSearcherController {

    // FXML components
    @FXML
    private Label labelTextTime, labelTextSortBy, labelTextSearchBy, labelTextSortingMode;

    @FXML
    private Label labelTextSortingMethod, labelTextSearchMethod, labelTextDataView;

    @FXML
    private CheckMenuItem checkMenuItemOptHeaders;

    @FXML
    private Menu menuData, menuSort, menuSearch, menuView, menuRecentFiles;

    @FXML
    private Menu menuSortBy, menuSearchBy, menuSortOrderBy;

    @FXML
    private Menu menuAlgorithm_SO, menuAlgorithm_SE;

    @FXML
    private MenuItem menuItemSortASC, menuItemSortDESC;

    @FXML
    private MenuItem menuItemExport, menuItemExportAs;

    @FXML
    private TextField itemToSearch;

    @FXML
    private TableView<String[]> tableView;


    // Other variables
    private FileChooser fileChooserOpen, fileChooserExport, fileChooserExportAs;
    private File file;
    private ExecutionTimeLogController ExecutionTimeLogC;
    private CSVIntermediateRepresentation ir;
    private final Stage stageExecutionTimeLog = new Stage();
    public TableV tableV;
    private SortingAlgorithm[] so_Algo;
    private SearchAlgorithm[] se_Algo;


    // Other variables
    public static boolean sort_orderBy = true, rawDataView = true;
    public static int columnIndexToSelectedInSort = -1, columnIndexToSelectedInSearch = -1;
    private int selectSortAlgorithmIndex = -1, selectSearchAlgorithmIndex = -1;
    private Integer[] algorithmGeneratedIndexes = null;


    /**
     * Initializes the controller.
     */
    public void initialize(){
        createSubWindows();
        updateRecentFiles();
        loadDifferentFileChooser();
        disableExportButtons(true);
        disableAllMenuButtons(true);
        addAlgorithmMethodMenu();
        menuItemSortASC.setOnAction(event -> {sort_selectOrderBy(true);});
        menuItemSortDESC.setOnAction(event -> {sort_selectOrderBy(false);});
        menuSortOrderBy.getItems().set(0, menuItemSortASC);
        menuSortOrderBy.getItems().set(1, menuItemSortDESC);
        tableV = new TableV(tableView);
    }


    /**
     * Opens a file dialog to select a CSV file and loads it into the application.
     */
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
        disableAllMenuButtons(false);
    }

    /**
     * Opens a recently accessed file based on the selected menu item.
     * @param pathRecentFile The path of the recently accessed file.
     */
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
        disableAllMenuButtons(false);
    }

    /**
     * Updates the list of recent files in the menu.
     */
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


    /**
     * Reads the selected CSV file and loads its contents into the application.
     * @param useFileHeaders Indicates whether to use the headers from the file.
     */
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

    /**
     * Sets the columns available for sorting and searching.
     */
    private void setColumnsToChoose(){
        /*
        - This method adds the columns to select in the Sort and Search by menu
         */
        CSVHeader[] columns = ir.getHeaders();
        Stream.of(menuSortBy, menuSearchBy)
                .forEach(element -> {
                    if (element.getItems() != null)
                        element.getItems().clear();
                });

        for (int i = 0; i < columns.length; i++) {
            MenuItem columnSort = new MenuItem(columns[i].name);
            MenuItem columnSearch = new MenuItem(columns[i].name);
            int finalI = i;
            columnSort.setOnAction(event -> sort_selectSortBy(finalI));
            columnSearch.setOnAction(event -> search_selectSearchBy(finalI));
            menuSortBy.getItems().add(columnSort);
            menuSearchBy.getItems().add(columnSearch);
        }
    }


    /**
     * Disables all menu buttons.
     * @param disableAllButtons Indicates whether to disable all buttons.
     */
    private void disableAllMenuButtons(boolean disableAllButtons){
        menuData.setDisable(disableAllButtons);
        menuSort.setDisable(disableAllButtons);
        menuSearch.setDisable(disableAllButtons);
        menuView.setDisable(disableAllButtons);
        // disable TextField itemToSearch
        itemToSearch.setDisable(disableAllButtons);
    }

    /**
     * Disables export buttons based on a condition.
     * @param disableExportButtons Indicates whether to disable export buttons.
     */
    private void disableExportButtons(boolean disableExportButtons){
        if (!disableExportButtons && !rawDataView){
            menuItemExport.setDisable(false);
            menuItemExportAs.setDisable(false);
        }else{
            menuItemExport.setDisable(true);
            menuItemExportAs.setDisable(true);
        }

    }


    /**
     * Creates sub-windows for the application.
     */
    private void createSubWindows(){
        FXMLLoader loaderExecutionTimeLog = new FXMLLoader(CSVSorterAndSearcher.class.getResource("ExecutionTimeLog.fxml"));
        try{
            // creates the ExecutionTimeLog subwindow
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

    /**
     * Loads different file choosers for opening, exporting, and exporting as.
     */
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

    /**
     * Adds sorting and searching algorithm methods to the menu.
     */
    private void addAlgorithmMethodMenu(){

        String[][] nameAlgorithms = {
                {"QuickSort", "BubbleSort", "OptimizedBubbleSort"},
                {"SequentialSearch", "InterpolationSearch", "BinarySearch"}
        };
        so_Algo = new SortingAlgorithm[]{
                QuickSort::sort,
                BubbleSort::sort,
                OptimizedBubbleSort::sort
        };
        se_Algo = new SearchAlgorithm[]{
                SequentialSearch::search,
                InterpolationSearcher::search,
                BinarySearch::search
        };

        for (int i = 0; i < nameAlgorithms[0].length; i++) {
            MenuItem algo = new MenuItem(nameAlgorithms[0][i]);
            int finalI = i;
            algo.setOnAction(actionEvent -> selectSortingMethod(nameAlgorithms[0][finalI], finalI));
            menuAlgorithm_SO.getItems().add(algo);
        }
        for (int i = 0; i < nameAlgorithms[1].length; i++) {
            MenuItem algo = new MenuItem(nameAlgorithms[1][i]);
            int finalI = i;
            algo.setOnAction(actionEvent -> selectSearchMethod(nameAlgorithms[1][finalI], finalI));
            menuAlgorithm_SE.getItems().add(algo);
        }
    }

    /**
     * Resets settings and clears the table view.
     */
    private void resetSettings(){
        // reset tableView
        tableV.clear(tableView);

        // reset Sort settings
        sort_selectOrderBy(true);
        menuSortBy.getItems().clear();
        columnIndexToSelectedInSort = -1;
        selectSortAlgorithmIndex = -1;
        labelTextSortingMethod.setText("None");
        labelTextSortBy.setText("None");

        // reset Search settings
        columnIndexToSelectedInSearch = -1;
        selectSearchAlgorithmIndex = -1;
        labelTextSearchBy.setText("None");
        labelTextSearchMethod.setText("None");
        itemToSearch.clear();

        // reset execution time
        labelTextTime.setText("0 ms");

        // reset View
        algorithmGeneratedIndexes = null;
        rawDataView = true;
        labelTextDataView.setText("Raw data");
        ExecutionTimeLogC.clear();

        // reset generateCXHeaders
        checkMenuItemOptHeaders.setSelected(false);


    }


    /**
     * Selects a sorting method based on the user's choice.
     * @param SortMethod The selected sorting method.
     * @param index The index of the selected sorting method.
     */
    private void selectSortingMethod(String SortMethod, int index){
        selectSortAlgorithmIndex = index;
        labelTextSortingMethod.setText(SortMethod);
    }

    /**
     * Selects a search method based on the user's choice.
     * @param SearchMethod The selected search method.
     * @param index The index of the selected search method.
     */
    private void selectSearchMethod(String SearchMethod, int index){
        selectSearchAlgorithmIndex = index;
        labelTextSearchMethod.setText(SearchMethod);
    }

    /**
     * Selects a column for searching based on the user's choice.
     * @param index The index of the selected column.
     */
    private void search_selectSearchBy(int index) {
        columnIndexToSelectedInSearch = index;
        labelTextSearchBy.setText(ir.getHeaders()[index].name);
    }

    /**
     * Selects a column for sorting based on the user's choice.
     * @param index The index of the selected column.
     */
    private void sort_selectSortBy(int index){
        columnIndexToSelectedInSort = index;
        labelTextSortBy.setText(ir.getHeaders()[index].name);
    }

    /**
     * Selects the sorting order (ascending or descending) based on user input.
     * @param optOrderBy Indicates whether to sort in ascending order.
     */
    @FXML
    protected void sort_selectOrderBy(boolean optOrderBy){
        sort_orderBy = optOrderBy;
        labelTextSortingMode.setText(optOrderBy ? "ASC" : "DESC");
    }


    /**
     * Executes the sorting process based on the selected sorting method and column.
     */
    @FXML
    protected void executeSorting(){
        if (selectSortAlgorithmIndex < 0 || columnIndexToSelectedInSort < 0){
            Utilities.alert(
                    "Error",
                    "Error executing sort",
                    "No column or algorithm selected.",
                    Alert.AlertType.ERROR
            );
            return;
        }

        String[] columnData = ir.getColumn(columnIndexToSelectedInSort).clone();
        FieldType type = ir.getHeaders()[columnIndexToSelectedInSort].type;

        Object[] convertedModifiedData_ = DataTypeDetector.dataTypeConversion(columnData, type);
        Object[] convertedOriginalData_ = DataTypeDetector.dataTypeConversion(columnData, type);

        convertedOriginalData_ = switch (type){
            case String -> (String[]) convertedOriginalData_;
            case Boolean -> (Integer[]) convertedOriginalData_;
            case Double -> (Double[]) convertedOriginalData_;
            case Date -> (LocalDate[]) convertedOriginalData_;
        };

        Instant start = Instant.now();
        switch (type){
            case String -> so_Algo[selectSortAlgorithmIndex].sort((String[]) convertedModifiedData_);
            case Boolean -> so_Algo[selectSortAlgorithmIndex].sort((Integer[]) convertedModifiedData_);
            case Double -> so_Algo[selectSortAlgorithmIndex].sort((Double[]) convertedModifiedData_);
            case Date -> so_Algo[selectSortAlgorithmIndex].sort((LocalDate[]) convertedModifiedData_);
        }
        Instant end = Instant.now();

        algorithmGeneratedIndexes =
                OrderedIndexGenerator.generateIndices(convertedOriginalData_, convertedModifiedData_);

        if (algorithmGeneratedIndexes == null) {
            Utilities.alert(
                    "Error",
                    "Error getting sorted indexes",
                    "An internal error occurred when obtaining the ordered " +
                            "indexes of two identical arrays (error caused by the detection " +
                            "that the arrays have different lengths).",
                    Alert.AlertType.ERROR
            );
            return;
        }

        if (!sort_orderBy)
            OrderedIndexGenerator.reverseArr(algorithmGeneratedIndexes);

        long duration = Duration.between(start ,end).toMillis();
        labelTextTime.setText(String.format("%d ms", duration));
        ExecutionTimeLogC.addRow(labelTextSortingMethod.getText()+" (Sort)", duration);
        showProcessedData();
    }

    /**
     * Executes the searching process based on the selected search method, column, and item.
     */
    @FXML
    protected void executeSearcher(){
        if (selectSearchAlgorithmIndex < 0 || columnIndexToSelectedInSearch < 0 ||
                itemToSearch.getText().isEmpty()){
            Utilities.alert(
                    "Error",
                    "Error executing search",
                    "No column, algorithm or item to search was selected.",
                    Alert.AlertType.ERROR
            );
            return;
        }

        String[] columnData = ir.getColumn(columnIndexToSelectedInSearch).clone();
        FieldType type = ir.getHeaders()[columnIndexToSelectedInSearch].type;

        Object[] convertedModifiedData_ = DataTypeDetector.dataTypeConversion(columnData, type);

        Object convertedItemToSearch;

        try {
            convertedItemToSearch = DataTypeDetector.dataTypeConversion(itemToSearch.getText(), type);
        }catch (Exception e){
            Utilities.alert(
                    "WARNING",
                    "The element has a different data type!",
                    "The element has a different data type in the column to search",
                    Alert.AlertType.WARNING
            );
            algorithmGeneratedIndexes = null;
            return;
        }

        if (type == FieldType.String){
            //to search regardless of upper or lower case
            convertedModifiedData_ =
                    Stream.of(convertedModifiedData_)
                    .map(i -> i.toString().toLowerCase())
                    .toArray(String[]::new);
            convertedItemToSearch = convertedItemToSearch.toString().toLowerCase();
        }

        Instant start = Instant.now();
        algorithmGeneratedIndexes = new Integer[]{switch (type) {
            case String -> se_Algo[selectSearchAlgorithmIndex].search((String[]) convertedModifiedData_, (String) convertedItemToSearch);
            case Boolean -> se_Algo[selectSearchAlgorithmIndex].search((Integer[]) convertedModifiedData_, (Integer) convertedItemToSearch);
            case Double -> se_Algo[selectSearchAlgorithmIndex].search((Double[]) convertedModifiedData_, (Double) convertedItemToSearch);
            case Date -> se_Algo[selectSearchAlgorithmIndex].search((LocalDate[]) convertedModifiedData_, (LocalDate) convertedItemToSearch);
        }};
        Instant end = Instant.now();

        if ((algorithmGeneratedIndexes == null) ||
                (algorithmGeneratedIndexes.length == 1 && algorithmGeneratedIndexes[0] == -1)){
            Utilities.alert(
                    "WARNING",
                    "Item not found",
                    String.format(
                            "The item you are looking for (%s) was not found in the data provided.",
                            convertedItemToSearch),
                    Alert.AlertType.WARNING
            );
            algorithmGeneratedIndexes = null;
            return;
        }

        long duration = Duration.between(start ,end).toMillis();
        labelTextTime.setText(String.format("%d ms", duration));
        ExecutionTimeLogC.addRow(labelTextSearchMethod.getText()+" (Search)", duration);
        showProcessedData();

    }

    /**
     * Displays the execution time log window.
     */
    @FXML
    protected void runExecutionTimeLog(){
        if (!stageExecutionTimeLog.isShowing()){
            stageExecutionTimeLog.show();
        }else{
            stageExecutionTimeLog.hide();
            stageExecutionTimeLog.show();
        }
    }


    /**
     * Exports the data to a CSV file.
     */
    @FXML
    private void export(){
        File fileExport = fileChooserExport.showSaveDialog(null);
        if (fileExport == null){
            return;
        }
        ExportModes.ExportAsCSV(ir.getHeaders(), tableV.getItems(), fileExport.getPath());
    }

    /**
     * Exports the data to a specified file format.
     */
    @FXML
    protected void exportAs(){
        File fileExport = fileChooserExportAs.showSaveDialog(null);
        if (fileExport == null){
            return;
        }

        String extension = fileExport.getName()
                .substring(fileExport.getName().lastIndexOf('.')+1)
                .toUpperCase();
        switch (extension){
            case "CSV":
                ExportModes.ExportAsCSV(ir.getHeaders(), tableV.getItems(), fileExport.getPath());
                break;
            case "TXT":
                ExportModes.ExportAsTXT(ir.getHeaders(), tableV.getItems(), fileExport.getPath());
                break;
            case "JSON":
                ExportModes.ExportAsJSON(ir.getHeaders(), tableV.getItems(), fileExport.getPath());
                break;
            case "XML":
                ExportModes.ExportAsXML(ir.getHeaders(), tableV.getItems(), fileExport.getPath());
                break;
            case "HTML":
                ExportModes.ExportAsHTML(ir.getHeaders(), tableV.getItems(), fileExport.getPath());
                break;
        }

    }


    /**
     * Displays the raw data view in the table.
     */
    @FXML
    protected void showRawData(){
        rawDataView = true;
        disableExportButtons(true);
        labelTextDataView.setText("Raw Data");
        tableV.setItems(ir.getColumns());
    }

    /**
     * Displays the processed data view in the table.
     */
    @FXML
    protected void showProcessedData(){
        if(algorithmGeneratedIndexes == null)
            return;
        rawDataView = false;
        disableExportButtons(false);
        labelTextDataView.setText("Processed Data");
        tableV.setItems(ir.getColumns(), algorithmGeneratedIndexes);
    }


    /**
     * Clears the table view and resets settings.
     */
    @FXML
    protected void clearTableView(){
        disableExportButtons(true);
        disableAllMenuButtons(true);
        resetSettings();
    }

    /**
     * Generates or removes auto-generated column headers.
     */
    @FXML
    protected void generateCXHeaders(){
        startCSVReader(!checkMenuItemOptHeaders.isSelected());
    }


    /**
     * Exits the application.
     */
    @FXML
    protected void quit(){
        Platform.exit();
    }

}