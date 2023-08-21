module com.huy.appnoithat {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.logging.log4j;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;

    exports com.huy.appnoithat.Controller;
    opens com.huy.appnoithat.Controller to javafx.fxml;
    exports com.huy.appnoithat.Entity;
    opens com.huy.appnoithat.Entity to javafx.fxml;
    opens com.huy.appnoithat to javafx.fxml;
    exports com.huy.appnoithat;
    exports com.huy.appnoithat.Controller.LuaChonNoiThat;
    opens com.huy.appnoithat.Controller.LuaChonNoiThat to javafx.fxml;

    exports com.huy.appnoithat.Controller.DatabaseModify;
    opens com.huy.appnoithat.Controller.DatabaseModify to javafx.fxml;

    exports com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;
    opens com.huy.appnoithat.Controller.LuaChonNoiThat.Cell to javafx.fxml;

}