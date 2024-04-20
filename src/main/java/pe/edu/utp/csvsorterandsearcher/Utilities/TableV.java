package pe.edu.utp.csvsorterandsearcher.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pe.edu.utp.csvsorterandsearcher.CSV.CSVHeader;


public class TableV {
    private final ObservableList<String[]> items = FXCollections.observableArrayList();
    private int headerslength = 0;

    public TableV (TableView<String[]> tableView){
        tableView.setItems(this.items);
    }

    public void setHeaders(TableView<String[]> tableView, CSVHeader[] headers){
        this.headerslength = headers.length;
        deleteColumns(tableView);
        for (int i = 0; i < headerslength; i++) {
            TableColumn<String[], String> column = new TableColumn<>(headers[i].name);
            int finalI = i;
            column.setCellValueFactory(param -> {
                String[] row = param.getValue();
                return row == null || row.length < (finalI + 1) ? null : new javafx.beans.property.SimpleStringProperty(row[finalI]);
            });
            column.setSortable(false);
            tableView.getColumns().add(column);
        }
    }


    public void setItems(String[][] items){
        /*
         - The ObservableList is filled but this time with the data horizontally
         - There is no need to add the tableView.setItems(), since if there is a
           change in this.items this will automatically be reflected in the tableView
         */
        if(headerslength == 0 && items.length == 0){
            return;
        }
        if(!this.items.isEmpty()){
            this.items.clear();
        }

        for(int i = 0; i < items[0].length; i++){
            this.items.add(i, new String[headerslength]);
            for (int j = 0; j < headerslength; j++) {
                this.items.get(i)[j] = items[j][i];
            }

        }

    }

    public void clear(TableView<String[]> tableView){
        deleteItems(tableView);
        deleteColumns(tableView);

    }

    private void deleteItems(TableView<String[]> tableView){
        tableView.getItems().clear();
    }

    private void deleteColumns(TableView<String[]> tableView){
        tableView.getColumns().clear();
    }

}
