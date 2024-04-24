package pe.edu.utp.csvsorterandsearcher.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pe.edu.utp.csvsorterandsearcher.CSV.CSVHeader;

/**
 * This class helps represent and manage a visual table that can be
 * used to display data in a user interface. Used in conjunction with
 * a JavaFX TableView to display data in a tabular manner.
 */
public class TableV {
    private final ObservableList<String[]> items = FXCollections.observableArrayList();
    private int headerslength = 0;

    /**
     * Constructor of the tableV class.
     * @param tableView TableView (String[]) associated with this TableV instance
     */
    public TableV (TableView<String[]> tableView){
        tableView.setItems(this.items);
    }

    /**
     * Sets table headers
     * @param tableView TableView (String[]) associated with this TableV instance
     * @param headers Array of CSVHeader objects representing table headers
     */
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

    /**
     * Sets the table elements.
     * @param items Two-dimensional array that represents the elements
     *              of the table.
     */
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

    /**
     * Sets the table elements using an array of indexes.
     * @param items Two-dimensional array that represents the elements
     *              of the table.
     * @param indices Index array indicating how to order elements
     */
    public void setItems(String[][] items, Integer[] indices){
        /*
         - This method receives the list of indices that are returned
           by the sorting methods (this is so that it sorts the data based on the given indices)
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

        for(int i = 0; i < indices.length; i++){
            this.items.add(i, new String[headerslength]);
            for (int j = 0; j < headerslength; j++) {
                this.items.get(i)[j] = items[j][indices[i]];
            }

        }

    }

    /**
     * Sets the table elements using an array of indexes.
     * @param items Two-dimensional array that represents the elements
     *              of the table.
     * @param indices Index array indicating how to order elements
     */
    public void setItems(String[][] items, int[] indices){
        /*
         - This method receives the list of indices that are returned
           by the sorting methods (this is so that it sorts the data based on the given indices)
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

        for(int i = 0; i < indices.length; i++){
            this.items.add(i, new String[headerslength]);
            for (int j = 0; j < headerslength; j++) {
                this.items.get(i)[j] = items[j][indices[i]];
            }

        }

    }

    /**
     * This function returns the current items of the observableList in a two-dimensional
     * array of the tableView (whether they are original or modified,
     * it depends on the data when this function is called)
     * @return two-dimensional array with the current data of the tableView
     */
    public String[][] getItems(){
        return items.toArray(String[][]::new);
    }

    /**
     * Cleans the table, removing all elements and columns
     * @param tableView TableView (String[]) associated with this TableV instance
     */
    public void clear(TableView<String[]> tableView){
        deleteItems(tableView);
        deleteColumns(tableView);

    }

    /**
     * Remove the elements from the table view
     * @param tableView TableView (String[]) associated with this TableV instance
     */
    private void deleteItems(TableView<String[]> tableView){
        tableView.getItems().clear();
    }

    /**
     * Remove the columns from the table view
     * @param tableView TableView (String[]) associated with this TableV instance
     */
    private void deleteColumns(TableView<String[]> tableView){
        tableView.getColumns().clear();
    }

}
