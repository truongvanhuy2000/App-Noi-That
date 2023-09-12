package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Entity.HangMuc;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyHangMucScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyVatLieuScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyNoiThatScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyHangMucService;
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

public class DatabaseModifyHangMucController {
    List<HangMuc> hangMucList = new ArrayList<>();
    int parentID;

    DatabaseModifyHangMucService databaseModifyHangMucService = new DatabaseModifyHangMucService();
    @FXML
    private Button EditHangMucButton;

    @FXML
    private Button addHangMucButton;

    @FXML
    private Button clearTextbtn;

    @FXML
    private Button deleteHangMucButton;

    @FXML
    private ListView<HangMuc> listViewHangMuc;

    @FXML
    private Button nextScreenButton;

    @FXML
    private TextArea txtHangMuc;
    @FXML
    private Button backButton;

    @FXML
    void AddNewHangMuc(ActionEvent event) {
       try {
            String txt = txtHangMuc.getText().trim().toUpperCase();
            boolean hasDuplicate = false;
            if(!txt.isEmpty()){
                List<HangMuc> array = listViewHangMuc.getItems().stream().filter(e->e.getName().equals(txt)).collect(Collectors.toList());
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
                    HangMuc hangMuc = new HangMuc();
                    hangMuc.setId(0);
                    hangMuc.setName(txt);
                    hangMuc.setVatLieuList(null);
//                    listViewPhongCach.getItems().add(phongCachNoiThat);
                    databaseModifyHangMucService.addNewHangMuc(hangMuc,parentID);
                    txtHangMuc.setText("");
                    hangMucList.clear();
                    listViewHangMuc.getItems().clear();
                    listViewHangMuc.refresh();
                    initializeHangMuc(parentID);
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
    void ClearText(ActionEvent event) {
        txtHangMuc.setText("");
    }

    @FXML
    void DeleteHangMuc(ActionEvent event) {
        try {
            int selectIndex =0;
            Alert alert = new Alert(Alert.AlertType.ERROR);

            if(listViewHangMuc.getSelectionModel().getSelectedItem() ==null){
                alert.setTitle("NEXT ERROR");
                alert.setHeaderText("look, a error to next");
                alert.setContentText("Please choose one item to next !!!");
                alert.showAndWait();
            }else{
                selectIndex = listViewHangMuc.getSelectionModel().getSelectedIndex();
                int deleteID = listViewHangMuc.getSelectionModel().getSelectedItem().getId();
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
                        databaseModifyHangMucService.deleteHangMuc(deleteID);
                        listViewHangMuc.getItems().remove(selectIndex);
                        txtHangMuc.clear();
                        listViewHangMuc.refresh();
                    }
                }else{
                    dialog.setTitle("DELETE ERROR");
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    dialog.getDialogPane().setContentText("Please select one item to delete !!!");
                    dialog.show();
                }
            }

        }catch (Exception e){
            System.out.println("Something went wrong.");
        }
    }

    @FXML
    void EditHangMuc(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            String txt = txtHangMuc.getText().trim().toUpperCase();
            int selectIndex = listViewHangMuc.getSelectionModel().getSelectedIndex();
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
                HangMuc hangMuc = new HangMuc(listViewHangMuc.getItems().get(selectIndex).getId(),txt,listViewHangMuc.getItems().get(selectIndex).getVatLieuList());
                databaseModifyHangMucService.EditHangMuc(hangMuc);
                listViewHangMuc.getItems().set(selectIndex,hangMuc);
                listViewHangMuc.refresh();
            }
        }catch (Exception e){
            System.out.println("co loi roi dai ca oi");
        }
    }

    @FXML
    void NextScreen(ActionEvent event) {
        int selectID =0;
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if(listViewHangMuc.getSelectionModel().getSelectedItem() ==null){
            alert.setTitle("NEXT ERROR");
            alert.setHeaderText("look, a error to next");
            alert.setContentText("Please choose one item to next !!!");
            alert.showAndWait();
        }else{
             selectID = listViewHangMuc.getSelectionModel().getSelectedItem().getId();
            Scene scene = null;
            Stage stage = null;
            Object source = event.getSource();
            stage = (Stage) ((Node)source).getScene().getWindow();
            if (source == nextScreenButton){
                scene = DatabaseModifyVatLieuScene.getInstance().getScene();
                DatabaseModifyVatLieuScene.getInstance().getController().initializeVatLieu(selectID);
            }else {
                return;
            }
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void tableClickToSelectItem(MouseEvent event) {
        try {
            if(listViewHangMuc.getSelectionModel().getSelectedItem() !=null){
                HangMuc selectedItem = listViewHangMuc.getSelectionModel().getSelectedItem();
                txtHangMuc.setText(selectedItem.getName());
            }
        }catch (Exception e){
            System.out.println("loi roi");
        }
    }


    public void initializeHangMuc(int id) {
        hangMucList.clear();
        parentID=id;
        hangMucList = databaseModifyHangMucService.findHangMucByID(id);
        for (HangMuc hm : hangMucList) {
            listViewHangMuc.getItems().add(new HangMuc(hm.getId(),hm.getName(),hm.getVatLieuList()));
        }
    }

    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == backButton){
            scene = DatabaseModifyNoiThatScene.getInstance().getScene();
            hangMucList.clear();
            listViewHangMuc.getItems().clear();
        }
        else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }
}
