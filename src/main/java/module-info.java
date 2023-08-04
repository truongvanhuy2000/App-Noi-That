module com.huy.appnoithat {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    requires org.apache.logging.log4j;
    exports com.huy.appnoithat.Controller;
    opens com.huy.appnoithat.Controller to javafx.fxml;

    opens com.huy.appnoithat to javafx.fxml;
    exports com.huy.appnoithat;
}