module com.huy.appnoithat {
    requires javafx.controls;
    requires javafx.fxml;
    requires poi;
    requires poi-ooxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;

    opens com.huy.appnoithat to javafx.fxml;
    exports com.huy.appnoithat;
}