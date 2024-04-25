package pe.edu.utp.csvsorterandsearcher.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller class for managing execution time
 * logs displayed in a TableView.
 */
public class ExecutionTimeLogController {

    @FXML
    private TableColumn<String[], String> tableColumnAlgorithm;

    @FXML
    private TableColumn<String[], String> tableColumnExecutionTime;

    @FXML
    public TableView<String[]> tableViewExecutionTimeLog;

    private ObservableList<String[]> items = FXCollections.observableArrayList();

    /**
     * Initializes the TableView and sets up cell
     * value factories for table columns.
     */
    public void initialize(){
        tableViewExecutionTimeLog.setItems(items);
        tableColumnAlgorithm.setCellValueFactory(param -> {
            String[] row = param.getValue();
            return row == null || row.length < (1) ? null : new javafx.beans.property.SimpleStringProperty(row[0]);
        });
        tableColumnExecutionTime.setCellValueFactory(param -> {
            String[] row = param.getValue();
            return row == null || row.length < (2) ? null : new javafx.beans.property.SimpleStringProperty(row[1]);
        });
    }

    /**
     * Adds a row to the TableView with the provided algorithm name and execution time.
     * @param algorithmName The name of the algorithm.
     * @param time The execution time in milliseconds.
     */
    public void addRow(String algorithmName, long time){
            items.add(new String[]{algorithmName, String.format("%d ms", time)});
            //tableViewExecutionTimeLog.getItems().addAll(new String[]{algorithmName, String.valueOf(time)});
    }

    /**
     * Clears all rows from the TableView.
     */
    public void clear(){
        items.clear();
        //tableViewExecutionTimeLog.getItems().clear();
    }

    /**
     * Event handler method for deleting data.
     * Clears all rows from the TableView.
     */
    @FXML
    protected void deleteData(){
        clear();
    }

}

