module com.huy.appnoithat {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires uribuilder;
    requires lombok;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires org.apache.poi.ooxml;
    requires org.apache.poi.poi;
    requires org.apache.logging.log4j;
    requires java.desktop;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires org.apache.commons.lang3;

    exports com.huy.appnoithat.Controller;
    opens com.huy.appnoithat.Controller to javafx.fxml;
    exports com.huy.appnoithat.DataModel.Entity;
    opens com.huy.appnoithat.DataModel.Entity to javafx.fxml;
    opens com.huy.appnoithat to javafx.fxml;
    exports com.huy.appnoithat;
    exports com.huy.appnoithat.Controller.LuaChonNoiThat;
    opens com.huy.appnoithat.Controller.LuaChonNoiThat to javafx.fxml;

    exports com.huy.appnoithat.Controller.DatabaseModify;
    opens com.huy.appnoithat.Controller.DatabaseModify to javafx.fxml;

    exports com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;
    opens com.huy.appnoithat.Controller.LuaChonNoiThat.Cell to javafx.fxml;

    exports com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel;
    opens com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel to javafx.fxml;

    exports com.huy.appnoithat.DataModel.Yaml;
    opens com.huy.appnoithat.DataModel.Yaml to javafx.fxml;

    exports com.huy.appnoithat.DataModel.SaveFile;
    exports com.huy.appnoithat.DataModel;
    exports com.huy.appnoithat.Controller.UserManagement;
    exports com.huy.appnoithat.Controller.UserManagement.DataModel;
    opens com.huy.appnoithat.Controller.UserManagement to javafx.fxml;
    exports com.huy.appnoithat.Controller.FileNoiThatExplorer;
    opens com.huy.appnoithat.Controller.FileNoiThatExplorer to javafx.fxml;
    exports com.huy.appnoithat.Controller.NewTab;
    opens com.huy.appnoithat.Controller.NewTab to javafx.fxml;
    exports com.huy.appnoithat.Controller.LuaChonNoiThat.Common;
    opens com.huy.appnoithat.Controller.LuaChonNoiThat.Common to javafx.fxml;
    exports com.huy.appnoithat.Controller.Register;
    opens com.huy.appnoithat.Controller.Register to javafx.fxml;
    exports com.huy.appnoithat.DataModel.Session;

    exports com.huy.appnoithat.Controller.LuaChonNoiThat.Constant;
    opens com.huy.appnoithat.Controller.LuaChonNoiThat.Constant to javafx.fxml;
    opens com.huy.appnoithat.DataModel.SaveFile to javafx.fxml;
    opens com.huy.appnoithat.DataModel to javafx.fxml;
}