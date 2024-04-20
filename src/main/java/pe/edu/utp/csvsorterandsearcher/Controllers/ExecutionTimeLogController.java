package pe.edu.utp.csvsorterandsearcher.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private ObservableList<String[]> items = FXCollections.observableArrayList();

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

    public void addRow(String algorithmName, double time){
            items.add(new String[]{algorithmName, String.valueOf(time)});
            //tableViewExecutionTimeLog.getItems().addAll(new String[]{algorithmName, String.valueOf(time)});
    }

    public void clear(){
        items.clear();
        //tableViewExecutionTimeLog.getItems().clear();
    }

}

