package pe.edu.utp.csvsorterandsearcher.Utilities;


import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

// TODO: Esta clase es de guia para crear las columnas e ingresar datos (Clase tomada del proyecto antiguo)
public class Table {
    private String[] cabecerasTabla;
    private boolean archivoTieneCabeceras_flag = false;
    private ObservableList<String[]> datos;

    public Table(ObservableList<String[]> datos){
        this.datos = datos;
        setHeaders(true);
    }

    public void setHeaders(boolean archivoTieneCabeceras){
        if (datos.isEmpty()){
            return;
        }
        if (archivoTieneCabeceras && !this.archivoTieneCabeceras_flag) {
            cabecerasTabla = datos.get(0);
            datos.remove(0);
            this.archivoTieneCabeceras_flag = true;
        } else if (!archivoTieneCabeceras && this.archivoTieneCabeceras_flag) {
            int longitudPrimeraArreglo = datos.get(0).length;
            datos.add(0, cabecerasTabla);
            cabecerasTabla = new String[longitudPrimeraArreglo];
            for (int i = 0; i < longitudPrimeraArreglo;i++){
                cabecerasTabla[i] = String.format("C%d", i);
            }
            this.archivoTieneCabeceras_flag = false;
        }

    }

    public void establecerDatos(TableView<String[]> tableView){
        borrarColumnas(tableView);
        establecerCabeceras(tableView);
        tableView.setItems(datos);
    }

    public void establecerDatos(TableView<String[]> tableView, int[] indices){
        borrarColumnas(tableView);
        establecerCabeceras(tableView);
        for(int indice : indices) {
            tableView.getItems().add(datos.get(indice));
        }
    }

    private void establecerCabeceras(TableView<String[]> tableView) {
        for (int i = 0; i < cabecerasTabla.length; i++) {
            TableColumn<String[], String> column = new TableColumn<>(cabecerasTabla[i]);
            int finalI = i;
            column.setCellValueFactory(param -> {
                String[] fila = param.getValue();
                return fila == null || fila.length < (finalI + 1) ? null : new javafx.beans.property.SimpleStringProperty(fila[finalI]);
            });
            column.setSortable(false);
            tableView.getColumns().add(column);
        }

    }

    public void borrarContenido(TableView<String[]> tableView){
        borrarItems(tableView);
        borrarColumnas(tableView);

    }

    private void borrarItems(TableView<String[]> tableView){
        tableView.getItems().clear();
    }

    private void borrarColumnas(TableView<String[]> tableView){
        tableView.getColumns().clear();
    }

    public String[] getCabeceras(){
        return cabecerasTabla;
    }

    public ObservableList<String[]> getDatos(){
        return datos;
    }
}

