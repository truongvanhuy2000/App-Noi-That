package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Entity.*;
import com.huy.appnoithat.Scene.DatabaseModify.ChangeProductSpecificationScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyHangMucScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyHangMucService;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyVatlieuService;
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

public class DatabaseModifyVatLieuController{

    List<VatLieu> vatLieuList = new ArrayList<>();
    int parentID;

    DatabaseModifyVatlieuService databaseModifyVatlieuService = new DatabaseModifyVatlieuService();
    @FXML
    private Button EditVatlieuButton;

    @FXML
    private Button addVatlieuButton;

    @FXML
    private Button backButton;

    @FXML
    private Button clearTextbtn;

    @FXML
    private Button deleteVatLieuButton;

    @FXML
    private ListView<VatLieu> listViewVatlieu;

    @FXML
    private Button nextScreenButton;

    @FXML
    private TextArea txtVatlieu;

    @FXML
    void AddNewVatLieu(ActionEvent event) {
        try {
            String txt = txtVatlieu.getText().trim().toUpperCase();
            boolean hasDuplicate = false;
            if(!txt.isEmpty()){
                List<VatLieu> array = listViewVatlieu.getItems().stream().filter(e->e.getName().equals(txt)).collect(Collectors.toList());
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
                    VatLieu vatLieu = new VatLieu();
                    vatLieu.setId(0);
                    vatLieu.setName(txt);
                    vatLieu.setThongSo(null);
//                    listViewPhongCach.getItems().add(phongCachNoiThat);
                    databaseModifyVatlieuService.addNewVatLieu(vatLieu,parentID);
                    txtVatlieu.setText("");
                    vatLieuList.clear();
                    listViewVatlieu.getItems().clear();
                    listViewVatlieu.refresh();
                    initializeVatLieu(parentID);
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
        txtVatlieu.setText("");
    }

    @FXML
    void DeleteVatlieu(ActionEvent event) {
        try {
            int selectIndex =0;
            Alert alert = new Alert(Alert.AlertType.ERROR);

            if(listViewVatlieu.getSelectionModel().getSelectedItem() ==null){
                alert.setTitle("NEXT ERROR");
                alert.setHeaderText("look, a error to next");
                alert.setContentText("Please choose one item to next !!!");
                alert.showAndWait();
            }else{
                selectIndex = listViewVatlieu.getSelectionModel().getSelectedIndex();
                int deleteID = listViewVatlieu.getSelectionModel().getSelectedItem().getId();
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
                        databaseModifyVatlieuService.deleteVatLieu(deleteID);
                        listViewVatlieu.getItems().remove(selectIndex);
                        txtVatlieu.clear();
                        listViewVatlieu.refresh();
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
    void EditVatlieu(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            String txt = txtVatlieu.getText().trim().toUpperCase();
            int selectIndex = listViewVatlieu.getSelectionModel().getSelectedIndex();
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
                VatLieu vatLieu = new VatLieu(listViewVatlieu.getItems().get(selectIndex).getId(),txt,listViewVatlieu.getItems().get(selectIndex).getThongSo());;
                databaseModifyVatlieuService.EditVatLieu(vatLieu);
                databaseModifyVatlieuService.findVatLieuByID(selectIndex);
                listViewVatlieu.getItems().set(selectIndex,vatLieu);
                listViewVatlieu.refresh();
            }
        }catch (Exception e){
            System.out.println("co loi roi dai ca oi");
        }
    }

    @FXML
    void NextScreen(ActionEvent event) {
        int selectID =0;
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if(listViewVatlieu.getSelectionModel().getSelectedItem() ==null){
                alert.setTitle("NEXT ERROR");
                alert.setHeaderText("look, a error to next");
                alert.setContentText("Please choose one item to next !!!");
                alert.showAndWait();
            }else{
                selectID = listViewVatlieu.getSelectionModel().getSelectedItem().getId();
                Scene scene = null;
                Stage stage = null;
                Object source = event.getSource();
                stage = (Stage) ((Node)source).getScene().getWindow();
                if (source == nextScreenButton){
                    scene = ChangeProductSpecificationScene.getInstance().getScene();
                    ChangeProductSpecificationScene.getInstance().getController().initializeThongSo(selectID);
                }else {
                    return;
                }
                stage.setScene(scene);
                stage.show();
            }
    }

    @FXML
    void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == backButton){
            scene = DatabaseModifyHangMucScene.getInstance().getScene();
            vatLieuList.clear();
            listViewVatlieu.getItems().clear();
        }
        else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void tableClickToSelectItem(MouseEvent event) {
        try {
            if(listViewVatlieu.getSelectionModel().getSelectedItem() !=null){
                VatLieu selectedItem = listViewVatlieu.getSelectionModel().getSelectedItem();
                txtVatlieu.setText(selectedItem.getName());
            }
        }catch (Exception e){
            System.out.println("loi roi");
        }
    }

    public void initializeVatLieu(int id) {
        vatLieuList.clear();
        parentID=id;
        vatLieuList = databaseModifyVatlieuService.findVatLieuByID(id);
        for (VatLieu vl : vatLieuList) {
            listViewVatlieu.getItems().add(new VatLieu(vl.getId(),vl.getName(),new ThongSo()));
        }
    }


}
