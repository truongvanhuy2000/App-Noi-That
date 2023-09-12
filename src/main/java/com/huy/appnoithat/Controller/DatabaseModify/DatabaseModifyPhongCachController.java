package com.huy.appnoithat.Controller.DatabaseModify;


import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyHangMucScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyNoiThatScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.LoginScene;
import com.huy.appnoithat.Scene.LuaChonNoiThatScene;
import com.huy.appnoithat.Scene.UserManagementScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyPhongCachService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DatabaseModifyPhongCachController {

    List<PhongCachNoiThat> phongCachNoiThatList = new ArrayList<>();

    DatabaseModifyPhongCachService databaseModifyPhongCachService = new DatabaseModifyPhongCachService();
    @FXML
    private ListView<PhongCachNoiThat> listViewPhongCach;

    @FXML
    private Button clearTextbtn;

    @FXML
    private Button EditPhongCachButton;

    @FXML
    private Button addPhongCachButton;

    @FXML
    private Button deletePhongCachButton;
    @FXML
    private Button backButton;
    @FXML
    private Button nextScreenButton;

    @FXML
    private TextArea txtPhongCach;


    @FXML
    void AddNewPhongCach(ActionEvent event) {
        try {
            String txt = txtPhongCach.getText().trim().toUpperCase();
            boolean hasDuplicate = false;
            if(!txt.isEmpty()){
                List<PhongCachNoiThat> array = listViewPhongCach.getItems().stream().filter(e->e.getName().equals(txt)).collect(Collectors.toList());
                for (int i = 0; i < array.size(); i++) {
                    if(array.get(i).equals(txt.toUpperCase())){
                        hasDuplicate = true;
                    }
                }
                if(hasDuplicate){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ADD DUPLICATE ERROR");
                    alert.setHeaderText("look, a error");
                    alert.setContentText("cannot add duplicate element !!!");
                    alert.showAndWait();
                }
                if(!hasDuplicate){
                    PhongCachNoiThat phongCachNoiThat = new PhongCachNoiThat();
                    phongCachNoiThat.setId(0);
                    phongCachNoiThat.setName(txt);
                    phongCachNoiThat.setNoiThatList(null);
//                    listViewPhongCach.getItems().add(phongCachNoiThat);
                    databaseModifyPhongCachService.addNewPhongCach(phongCachNoiThat);
                    txtPhongCach.setText("");
                    phongCachNoiThatList.clear();
                    listViewPhongCach.getItems().clear();
                    listViewPhongCach.refresh();
                    initializePhongCach();
                }

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ADD ERROR");
                alert.setHeaderText("look, a error");
                alert.setContentText("please input something !!!");
                alert.showAndWait();
            }
        }catch (Exception e){
            System.out.println("co loi add roi dai ca");
        }

    }

    @FXML
    void DeletePhongCach(ActionEvent event) {
        try {
            int selectIndex =0;
            Alert alert = new Alert(Alert.AlertType.ERROR);

            if(listViewPhongCach.getSelectionModel().getSelectedItem() ==null){
                alert.setTitle("NEXT ERROR");
                alert.setHeaderText("look, a error to next");
                alert.setContentText("Please choose one item to next !!!");
                alert.showAndWait();
            }else{
                selectIndex = listViewPhongCach.getSelectionModel().getSelectedIndex();
                int deleteID = listViewPhongCach.getSelectionModel().getSelectedItem().getId();
                Alert deleteDialog = new Alert(Alert.AlertType.CONFIRMATION);
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

                        databaseModifyPhongCachService.deletePhongCach(deleteID);
                        listViewPhongCach.getItems().remove(selectIndex);
                        txtPhongCach.clear();
                        listViewPhongCach.refresh();
                    }
                }else{
                    alert.setTitle("DELETE ERROR");
                    alert.setHeaderText("look, a error");
                    alert.setContentText("Please choose one item to delete");
                    alert.showAndWait();
                }
            }
        }catch (Exception e){
            System.out.println("Something went wrong.");
        }


    }

    @FXML
    void EditPhongCach(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            String txt = txtPhongCach.getText().trim().toUpperCase();
            int selectIndex = listViewPhongCach.getSelectionModel().getSelectedIndex();
            if(selectIndex<0){
                alert.setTitle("EDIT ERROR");
                alert.setHeaderText("look, a error to edit");
                alert.setContentText("Please select one item to edit !!!");
                alert.showAndWait();
            }else if(txt.isEmpty()){
                alert.setTitle("EDIT ERROR");
                alert.setHeaderText("look, a error to edit");
                alert.setContentText("Cannot edit with text empty !!!");
                alert.showAndWait();
            }else{
                PhongCachNoiThat phongCachNoiThat = new PhongCachNoiThat(listViewPhongCach.getItems().get(selectIndex).getId(),txt,listViewPhongCach.getItems().get(selectIndex).getNoiThatList());;
                databaseModifyPhongCachService.EditPhongCach(phongCachNoiThat);
                databaseModifyPhongCachService.findAllPhongCach();
                listViewPhongCach.getItems().set(selectIndex,phongCachNoiThat);
                listViewPhongCach.refresh();
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
        try {
            if(listViewPhongCach.getSelectionModel().getSelectedItem() !=null){
                PhongCachNoiThat selectedItem = listViewPhongCach.getSelectionModel().getSelectedItem();
                txtPhongCach.setText(selectedItem.getName());
            }
        }catch (Exception e){
            System.out.println("loi roi");
        }

    }

    @FXML
    void NextScreen(ActionEvent event) {
        int selectID =0;
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if(listViewPhongCach.getSelectionModel().getSelectedItem() ==null){
            alert.setTitle("NEXT ERROR");
            alert.setHeaderText("look, a error to next");
            alert.setContentText("Please choose one item to next !!!");
            alert.showAndWait();
        }else{
             selectID = listViewPhongCach.getSelectionModel().getSelectedItem().getId();
            Scene scene = null;
            Stage stage = null;
            Object source = event.getSource();
            stage = (Stage) ((Node)source).getScene().getWindow();
            if (source == nextScreenButton){
                scene = DatabaseModifyNoiThatScene.getInstance().getScene();
                DatabaseModifyNoiThatScene.getInstance().getController().initializeNoiThat(selectID);
            }else {
                return;
            }
            stage.setScene(scene);
            stage.show();
        }

    }


    public void initializePhongCach() {
        phongCachNoiThatList.clear();
        phongCachNoiThatList = databaseModifyPhongCachService.findAllPhongCach();
        for (PhongCachNoiThat pc : phongCachNoiThatList) {
            listViewPhongCach.getItems().add(new PhongCachNoiThat(pc.getId(),pc.getName(),pc.getNoiThatList()));
        }

    }

    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == backButton){
            scene = HomeScene.getInstance().getScene();
            phongCachNoiThatList.clear();
            listViewPhongCach.getItems().clear();
        }
        else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }
}
