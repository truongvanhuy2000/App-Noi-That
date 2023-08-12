package com.huy.appnoithat.Controller.DatabaseModify;


import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyHangMucScene;
import com.huy.appnoithat.Scene.LoginScene;
import com.huy.appnoithat.Scene.UserManagementScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DatabaseModifyPhongCachController implements Initializable {

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
    private Button nextScreenButton;

    @FXML
    private TextField txtPhongCach;


    @FXML
    void AddNewPhongCach(ActionEvent event) {
        try {
            Dialog dialog = new Dialog();
            String txt = txtPhongCach.getText().trim().toUpperCase();
            boolean hasDuplicate = false;
            if(!txt.isEmpty()){
                List<String> array = listViewPhongCach.getItems().stream().filter(e->e.equals(txt)).collect(Collectors.toList());
                for (int i = 0; i < array.size(); i++) {
                    if(array.get(i).trim().equals(txt.toUpperCase())){
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
                    listViewPhongCach.getItems().add(txt);
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
            Alert deleteDialog = new Alert(Alert.AlertType.CONFIRMATION);
            Dialog dialog = new Dialog();
            if(selectIndex>=0){
                deleteDialog.setTitle("Delete Confirmation");
                deleteDialog.setHeaderText("Are you sure you want to delete this item?");
                deleteDialog.setContentText("This action cannot be undone.");
                // Add "Yes" button
                deleteDialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
                deleteDialog.getDialogPane().getButtonTypes().remove(ButtonType.OK);
                // Show the dialog and wait for user interaction
                ButtonType result = deleteDialog.showAndWait().orElse(ButtonType.CANCEL);
                // Handle the user's choice
                if (result == ButtonType.YES) {
                    listViewPhongCach.getItems().remove(selectIndex);
                }
            }else{
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
            Dialog dialog = new Dialog();

            String txt = txtPhongCach.getText().trim().toUpperCase();
            int selectIndex = listViewPhongCach.getSelectionModel().getSelectedIndex();
            if(selectIndex<=0){
                dialog.setTitle("EDIT ERROR");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.getDialogPane().setContentText("Please select one item to edit !!!");
                dialog.show();
            }else if(txt.isEmpty()){
                dialog.setTitle("EDIT ERROR");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.getDialogPane().setContentText("Cannot edit with text empty !!!");
                dialog.show();
            }else{
                listViewPhongCach.getItems().set(selectIndex, txtPhongCach.getText());
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
        Scene scene = null;
        Stage stage = null;
        Object source = event.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == nextScreenButton){
            scene = DatabaseModifyHangMucScene.getInstance().getScene();
        }else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewPhongCach.getItems().addAll(items);
    }


}
