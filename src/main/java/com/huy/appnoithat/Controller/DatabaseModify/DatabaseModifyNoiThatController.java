package com.huy.appnoithat.Controller.DatabaseModify;


import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyHangMucScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyNoiThatService;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyPhongCachService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseModifyNoiThatController {

    List<NoiThat> noiThatList = new ArrayList<>();
    int parentID;

    PhongCachNoiThat parentPhongCach;
    DatabaseModifyNoiThatService databaseModifyNoiThatService = new DatabaseModifyNoiThatService();
    @FXML
    private Button EditNoiThatButton;

    @FXML
    private Button addNoiThatButton;

    @FXML
    private Button backButton;

    @FXML
    private Button clearTextbtn;

    @FXML
    private Button deleteNoiThatButton;

    @FXML
    private ListView<NoiThat> listViewNoiThat;

    @FXML
    private Button nextScreenButton;

    @FXML
    private TextField txtNoiThat;


    @FXML
    void ClearText(ActionEvent event) {
        txtNoiThat.setText("");
    }

    @FXML
    void DeleteNoiThat(ActionEvent event) {
        try {
            int selectIndex = listViewNoiThat.getSelectionModel().getSelectedIndex();
            int deleteID = listViewNoiThat.getSelectionModel().getSelectedItem().getId();
            System.out.println(deleteID);
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

                    databaseModifyNoiThatService.deleteNoiThat(deleteID);
                    listViewNoiThat.getItems().remove(selectIndex);
                    listViewNoiThat.refresh();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("DELETE ERROR");
                alert.setHeaderText("look, a error");
                alert.setContentText("Please choose one item to delete");
                alert.showAndWait();
            }
        }catch (Exception e){
            System.out.println("Something went wrong.");
        }
    }

    @FXML
    void EditNoiThat(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            String txt = txtNoiThat.getText().trim().toUpperCase();
            int selectIndex = listViewNoiThat.getSelectionModel().getSelectedIndex();
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
                // cần phải get list nội thất
                NoiThat noiThat = new NoiThat(listViewNoiThat.getItems().get(selectIndex).getId(),txt,listViewNoiThat.getItems().get(selectIndex).getHangMucList());
                databaseModifyNoiThatService.EditNoiThat(noiThat);
                listViewNoiThat.getItems().set(selectIndex,noiThat);
                listViewNoiThat.refresh();
            }
        }catch (Exception e){
            System.out.println("co loi roi dai ca oi");
        }
    }


    @FXML
    void tableClickToSelectItem(MouseEvent event) {
        try {
            if(listViewNoiThat.getSelectionModel().getSelectedItem() !=null){
                NoiThat selectedItem = listViewNoiThat.getSelectionModel().getSelectedItem();
                txtNoiThat.setText(selectedItem.getName());
            }
        }catch (Exception e){
            System.out.println("loi roi");
        }
    }


    @FXML
    void NextScreen(ActionEvent event) {
        int selectID = listViewNoiThat.getSelectionModel().getSelectedItem().getId();
        Scene scene = null;
        Stage stage = null;
        Object source = event.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == nextScreenButton){
            scene = DatabaseModifyHangMucScene.getInstance().getScene();
            DatabaseModifyHangMucScene.getInstance().getController().initializeHangMuc(selectID);
        }else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }


    public void initializeNoiThat(int id) {
        noiThatList.clear();
        parentID=id;
        noiThatList = databaseModifyNoiThatService.findNoiThatByID(id);
        for (NoiThat nt : noiThatList) {
            listViewNoiThat.getItems().add(new NoiThat(nt.getId(),nt.getName(),nt.getHangMucList()));
        }
    }


    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == backButton){
            scene = DatabaseModifyPhongCachScene.getInstance().getScene();
            noiThatList.clear();
            listViewNoiThat.getItems().clear();
        }
        else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }

    public void NoiThatAdd(ActionEvent actionEvent) {
        try {
            String txt = txtNoiThat.getText().trim().toUpperCase();
            boolean hasDuplicate = false;
            if(!txt.isEmpty()){
                List<NoiThat> array = listViewNoiThat.getItems().stream().filter(e->e.getName().equals(txt)).collect(Collectors.toList());
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
                    NoiThat noiThat = new NoiThat();
                    noiThat.setId(0);
                    noiThat.setName(txt);
                    noiThat.setHangMucList(null);
//                    listViewPhongCach.getItems().add(phongCachNoiThat);
                    databaseModifyNoiThatService.addNewNoiThat(noiThat,parentID);
                    txtNoiThat.setText("");
                    noiThatList.clear();
                    listViewNoiThat.getItems().clear();
                    listViewNoiThat.refresh();
                    initializeNoiThat(parentID);
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
}
