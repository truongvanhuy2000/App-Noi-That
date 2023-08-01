package com.huy.appnoithat;

import com.huy.appnoithat.Entity.AccountInformation;
import com.huy.appnoithat.Service.FileExport.ExportXLS;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
    }

    public static void main(String[] args) throws Exception{
//        launch();
        ExportXLS e = new ExportXLS();
        List<AccountInformation> list = new ArrayList<>();
        AccountInformation acc = new AccountInformation(1,"long","nam","ngoclong@gmail.com","ha noi","0123123123");
        list.add(acc);
        e.writeCountryListToFile("template.xlsx",list);
    }
}