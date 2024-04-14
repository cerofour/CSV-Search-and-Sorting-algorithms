package pe.edu.utp.csvsorterandsearcher.Controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
    //Aqui esta el tableView, puedes modificarlo

    @FXML
    private CheckMenuItem checkMenuItemOptHeaders;

    @FXML
    private VBox vBox;

    @FXML
    private Menu menuRecentFiles;

    @FXML
    private MenuItem menuItemSortASC;

    @FXML
    private MenuItem menuItemSortDESC;

    @FXML
    private Menu menuSortBy;

    @FXML
    private Menu menuAlgorithm;

    @FXML
    private MenuItem menuItemExport;

    @FXML
    private MenuItem menuItemExportAs;

    @FXML
    private Stage stageSortSettings = new Stage();

    @FXML
    private Label labelTextDataView;



    public static boolean sortingModeASC = true;
    public static boolean rawDataView = true;

    private FileChooser fileChooserOpen;
    private FileChooser fileChooserExport;
    private FileChooser fileChooserExportAs;

    public void initialize(){
        createSubWindows();
        updateRecentFiles();
        loadDifferentFileChooser();
        modifyExportButtons();
        //
        menuItemSortASC.setOnAction(event -> {selectSortingMode(true);});
        menuItemSortDESC.setOnAction(event -> {selectSortingMode(false);});
        menuSortBy.getItems().set(0, menuItemSortASC);
        menuSortBy.getItems().set(1, menuItemSortDESC);
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
        modifyExportButtons(); // esta linea deberia ponerse cuando se detectó un ordenamiento o busqueda


        /*

        try {
            lectorArchivo = new Lector(archivo);
            if(!lectorArchivo.leerArchivo()){
                Utilidades.Alerta(
                        "Error",
                        "Ocurrió un error",
                        "El archivo tiene datos disparejos!",
                        Alert.AlertType.ERROR
                );
                textFieldArchivoSeleccionado.setText("");
                return;
            }

        } catch (Exception e){
            textFieldArchivoSeleccionado.setText("");
            return;
        }

         */
        /*
        tabla = new Tabla(lectorArchivo.getDatos());
        //tabla.establecerDatos(tableView, new int[]{0, 2, 5, 6});
        tabla.establecerDatos(tableView);
        cabeceraTipoDato = Utilidades.DetectarTipoDato(tabla.getDatos().get(0));
        comboBoxCabeceras.setItems(FXCollections.observableArrayList(Arrays.asList(tabla.getCabeceras())));
        comboBoxCabeceras.setDisable(false);
        comboBoxModoCabeceraDatos.setDisable(false);
        comboBoxCabecerasTipoDato.setDisable(false);

         */
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
        modifyExportButtons(); // esta linea deberia ponerse cuando se detectó un ordenamiento o busqueda
        // falta mas codigo
    }

    private void modifyExportButtons(){
        menuItemExport.setDisable(!menuItemExport.isDisable());
        menuItemExportAs.setDisable(!menuItemExportAs.isDisable());
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
        FXMLLoader loaderSortSettings = new FXMLLoader(CSVSorterAndSearcher.class.getResource("SortingSettings.fxml"));
        try{
            // crea la subventana SortSettings
            Scene sceneSortSettings = new Scene(loaderSortSettings.load(), 380, 210);
            stageSortSettings.setTitle("Sorting settings");
            stageSortSettings.setScene(sceneSortSettings);
            stageSortSettings.setResizable(false);
            stageSortSettings.setOnCloseRequest(windowEvent -> {
                stageSortSettings.hide();
                windowEvent.consume();}
            );
            // crea la subventana SearchSettings

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

    @FXML
    protected void clearTableView(){
        tableView.getItems().clear();
        tableView.getColumns().clear();
        if(!menuItemExport.isDisable() || !menuItemExportAs.isDisable()){
            modifyExportButtons();
            // tambien se puede limpiar borrando el contenido del arreglo referenciado
            // en los datos del tableView
        }


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
    protected void selectSortingMode(boolean optSortingMode){
        sortingModeASC = optSortingMode;
        labelTextSortingMode.setText(optSortingMode ? "ASC" : "DESC");
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
    protected void runSortSettings(){
        if (!stageSortSettings.isShowing()){
            stageSortSettings.show();
        }else{
            stageSortSettings.hide();
            stageSortSettings.show();
        }
    }


}