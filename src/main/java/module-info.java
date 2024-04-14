module pe.edu.utp.csvsorterandsearcher {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    //requires json.simple;

    opens pe.edu.utp.csvsorterandsearcher to javafx.fxml;
    exports pe.edu.utp.csvsorterandsearcher;
    exports pe.edu.utp.csvsorterandsearcher.Controllers;
    opens pe.edu.utp.csvsorterandsearcher.Controllers to javafx.fxml;
}