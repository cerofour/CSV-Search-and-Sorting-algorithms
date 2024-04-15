package pe.edu.utp.csvsorterandsearcher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class ExecutionTimeLogController {

    @FXML
    private TableColumn<String[], String> tableColumnAlgorithm;

    @FXML
    private TableColumn<String[], String> tableColumnExecutionTime;

    @FXML
    public TableView<String[]> tableViewExecutionTimeLog;

    public void initialize(){
        tableColumnAlgorithm.setCellValueFactory(param -> {
            String[] row = param.getValue();
            return row == null || row.length < (1) ? null : new javafx.beans.property.SimpleStringProperty(row[0]);
        });
        tableColumnExecutionTime.setCellValueFactory(param -> {
            String[] row = param.getValue();
            return row == null || row.length < (2) ? null : new javafx.beans.property.SimpleStringProperty(row[1]);
        });
    }

    public void addRow(String algorithmName, double time){
            tableViewExecutionTimeLog.getItems().addAll(new String[]{algorithmName, String.valueOf(time)});
    }

    public void clear(){
        tableViewExecutionTimeLog.getItems().clear();
    }

}

