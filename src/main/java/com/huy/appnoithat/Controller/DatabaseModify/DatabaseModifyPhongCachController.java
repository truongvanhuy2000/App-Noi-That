package com.huy.appnoithat.Controller.DatabaseModify;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.Array;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DatabaseModifyController implements Initializable {

    String[] items = {"NỘI THẤT PHONG CÁCH HIỆN ĐẠI - CONTEMPORARY STYLE","NỘI THẤT TÂN CỔ ĐIỂN SANG TRỌNG - CONTEMPORATY CLASSIC","NỘI THẤT GỖ SỒI MỸ - AMERICAN OAK EDITION","NỘI THẤT CAO CẤP GỖ ÓC CHÓ PHIÊN BẢN GIỚI HẠN - WALNUT LIMITED EDITION"};
    @FXML
    private ListView<String> listViewPhongCach;

    @FXML
    private Button clearTextbtn;

    @FXML
    private Button EditPhongCachButton;

    @FXML
    private Button addPhongCachButton;

    @FXML
    private Button deletePhongCachButton;

    @FXML
    private TextField txtPhongCach;


    @FXML
    void AddNewPhongCach(ActionEvent event) {
        try {
            Dialog dialog = new Dialog();
            //chua check add Phong cach giong ten nhau thi khong duoc
            String txt = txtPhongCach.getText().trim().toLowerCase();
            boolean hasDuplicate = false;
            if(!txt.isEmpty()){
                List<String> array = listViewPhongCach.getItems().stream().filter(e->e.equals(txt)).collect(Collectors.toList());
                for (int i = 0; i < array.size(); i++) {
                    if(array.get(i).trim().equals(txt.toLowerCase())){
                        hasDuplicate = true;
                    }
                }
                if(hasDuplicate){
                    dialog.setTitle("ADD DUPLICATE ERROR");
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    dialog.getDialogPane().setContentText("cannot add duplicate element !!!");
                    dialog.show();
                }
                if(!hasDuplicate){
                    listViewPhongCach.getItems().add(txtPhongCach.getText());
                    txtPhongCach.setText("");
                }


            }else{

                dialog.setTitle("ADD ERROR");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.getDialogPane().setContentText("please input something !!!");
                dialog.show();
            }

        }catch (Exception e){
            System.out.println("co loi add roi dai ca");
        }

    }

    @FXML
    void DeletePhongCach(ActionEvent event) {
        try {
            int selectIndex = listViewPhongCach.getSelectionModel().getSelectedIndex();
            if(selectIndex>=0){
                listViewPhongCach.getItems().remove(selectIndex);

            }else{
                Dialog dialog = new Dialog();
                dialog.setTitle("DELETE ERROR");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.getDialogPane().setContentText("Please select one item to delete !!!");
                dialog.show();
            }
        }catch (Exception e){
            System.out.println("Something went wrong.");
        }


    }

    @FXML
    void EditPhongCach(ActionEvent event) {
        try {
            int selectIndex = listViewPhongCach.getSelectionModel().getSelectedIndex();
            if(selectIndex>=0){
                listViewPhongCach.getItems().set(selectIndex, txtPhongCach.getText());
            }else{
                Dialog dialog = new Dialog();
                dialog.setTitle("EDIT ERROR");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.getDialogPane().setContentText("Please select one item to edit !!!");
                dialog.show();
            }
        }catch (Exception e){
            System.out.println("co loi roi dai ca oi");
        }

    }


    @FXML
    void ClearText(ActionEvent event) {
        txtPhongCach.setText("");
    }

    @FXML
    void tableClickToSelectItem(MouseEvent event) {
        String selectedItem = listViewPhongCach.getSelectionModel().getSelectedItem();
        txtPhongCach.setText(selectedItem);
    }

    @FXML
    void NextScreen(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewPhongCach.getItems().addAll(items);
    }


}
