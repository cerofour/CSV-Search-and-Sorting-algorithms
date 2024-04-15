package pe.edu.utp.csvsorterandsearcher.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import pe.edu.utp.csvsorterandsearcher.Utilities.Table;

import java.util.Arrays;

public class SortingSettingsController {

    @FXML
    public ComboBox<String> comboBoxSelectColumn;

    public int headerIndex;

    public void initialize(){
        //comboBoxSelectColumn.setItems(FXCollections.observableArrayList(Arrays.asList(Table.headers)));
    }

    @FXML
    protected void selectColumnsToOrder(){
        //headerIndex = comboBoxSelectColumn.getSelectionModel().getSelectedIndex();

    }
    // falta agregar metodos

}
